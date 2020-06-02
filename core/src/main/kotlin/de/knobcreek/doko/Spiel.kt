package de.knobcreek.doko

import java.util.Random
import java.util.UUID

enum class Ansage(val erlaubtBis: Int,
                  val erlaubtFür: Array<Partei>,
                  val grenze: Int,
                  val punkte: Int) {
    RE(1, arrayOf(Partei.RE), 120, 2),
    KONTRA(1, arrayOf(Partei.KONTRA), 120, 2),
    KEINE9(2, 150),
    KEINE6(3, 180),
    KEINE3(4, 210),
    SCHWARZ(5, 239); // Re muß die Zahl überschreiten

    constructor(erlaubtBis: Int, grenze: Int) :
            this(erlaubtBis, Partei.values(), grenze, 1)
}

class Spiel(val spieler: List<Spieler>, val mitNeunen: Boolean, val zweiteStichtErste: Boolean) {
    val uuid: UUID = UUID.randomUUID()
    val random = Random()
    // einfacher Satz, wird nur zum Geben verwendet
    val alleKarten = erzeugeKarten(mitNeunen)
    val kartenBewertungen = HashMap<Karte, KartenBewertung>()
    val kartenJeSpieler = geben()
    val vorbehalte = HashMap<Spieler, Spielregel>()

    var spielStatusJeSpieler = emptyMap<Spieler, SpielStatus>()
    var soloSpieler : Spieler? = null
    var spielregel = Spielregel.REGULÄR
    var aktuellerStich : Stich? = null
    val stiche = ArrayList<Stich>()

    fun geben(): Map<Spieler, MutableList<Karte>> {
        val gemischt = alleKarten.plus(alleKarten).shuffled(random)
        val proSpieler = gemischt.size / spieler.size
        return spieler
                .associateWith { sp ->
                    val fromIndex = sp.nummer * proSpieler
                    gemischt.subList(fromIndex, fromIndex + proSpieler).toMutableList()
                }
    }

    fun vorbehalt(spielregel: Spielregel, spieler: Spieler) : Pair<Spieler, Spielregel>? {
        if (vorbehalte.containsKey(spieler))
            throw NichtErlaubtException("nur ein Vorbehalt")

        vorbehalte[spieler] = spielregel
        return if (vorbehalte.size == 4) {
            with (vorbehalte.entries
                    .maxBy { entry -> entry.value }!!) {
                spielStatusNachVorbehalten(key, value)
                if (value !in listOf(Spielregel.REGULÄR, Spielregel.HOCHZEIT))
                    soloSpieler = key
                Pair(key, value)
            }
        } else null
    }

    fun spielStatusNachVorbehalten(soloSpieler: Spieler, spielregel: Spielregel) {
        this.spielregel = spielregel
        spielStatusJeSpieler = if (spielregel == Spielregel.REGULÄR)
            kartenJeSpieler.entries
                    .associateBy({ entry -> entry.key }, { entry -> partei(entry.value) })
        else
            spieler
                    .associateWith { sp -> partei(sp, soloSpieler) }
        kartenBewerten(kartenBewertungen, spielregel)
    }

    fun partei(karten: List<Karte>) =
            SpielStatus(if (reDame in karten) Partei.RE else Partei.KONTRA)

    fun partei(sp: Spieler, soloSpieler: Spieler) =
            SpielStatus(if (sp == soloSpieler) Partei.RE else Partei.KONTRA)

    fun kartenBewerten(kartenBewertungen: HashMap<Karte, KartenBewertung>, spielregel: Spielregel) =
            alleKarten
                    .associateWithTo(kartenBewertungen) { karte -> KartenBewertung(karte, spielregel, zweiteStichtErste) }

    fun spielen(karte: Karte, spieler: Spieler) {
        val karten = kartenJeSpieler[spieler] ?: throw NichtErlaubtException("ungültiger Spieler")
        val bewertung = kartenBewertungen.getValue(karte)

        val stich = aktuellerStich
        if (stich == null)
            aktuellerStich = Stich(stiche.size, bewertung, spieler)
        else {
            if (!stich.darfSpielen(spieler))
                throw NichtErlaubtException("nicht an der Reihe")
            if (!stich.darfSpielen(karte, karten, kartenBewertungen))
                throw NichtErlaubtException("ungültige Karte")
            val aktuellerStich = stich.nächsteKarte(bewertung, spieler)
            if (aktuellerStich.gespielt()) {
                stiche.add(aktuellerStich)
                this.aktuellerStich = null
            } else
                this.aktuellerStich = aktuellerStich
        }
        karten.remove(karte)
        spielStatusJeSpieler[spieler]!!.gespielteKarten++
    }

    fun ansagen(ansage: Ansage, spieler: Spieler) {
        val status = spielStatusJeSpieler[spieler]
        if (status == null || status.partei !in ansage.erlaubtFür)
            throw NichtErlaubtException("$ansage nicht erlaubt")
        if (status.gespielteKarten > ansage.erlaubtBis)
            throw NichtErlaubtException("$ansage nur erlaubt bis zur ${ansage.erlaubtBis}. Karte")
        status.ansagen.add(ansage)
    }

    fun ergebnis() : Ergebnis {
        val ansagePunkte = ansagePunkte() // TODO Sonderpunkte

        Partei.values().forEach { partei ->
            val mitglieder = mitglieder(partei)
            val spielPunkte = spielPunkte(mitglieder)
            val grenze = mitglieder
                    .flatMap { spieler -> spielStatusJeSpieler[spieler]?.ansagen ?: emptyList<Ansage>() }
                    .map { ansage -> ansage.grenze }
                    .max() ?: 120
            if (partei.gewonnen(spielPunkte, grenze)) {
                val punkte = partei.regulärePunkte(spielPunkte) + ansagePunkte
                return Ergebnis(partei, mitglieder, punkte)
            }
        }
        // In der seltenen Situation, daß keine Partei ihre Ansagen erreicht,
        // gibt es 0 Punkte für niemanden. ;-)
        return Ergebnis(Partei.KONTRA, emptyList(), 0)
    }

    fun ansagePunkte() =
            spielStatusJeSpieler.values
                    .flatMap { status -> status.ansagen }
                    .map { ansage -> ansage.punkte }
                    .sum()

    fun mitglieder(partei: Partei) =
            spielStatusJeSpieler.entries
                    .filter { entry -> entry.value.partei == partei }
                    .map { entry -> entry.key }

    fun spielPunkte(mitglieder: List<Spieler>) =
            stiche
                    .filter { stich -> stich.hatGewonnen() in mitglieder }
                    .map { stich -> stich.punkte() }
                    .sum()
}

class NichtErlaubtException(val grund: String) : Throwable(grund)

data class Ergebnis(val gewonnen: Partei, val spieler: List<Spieler>, val punkte: Int)
