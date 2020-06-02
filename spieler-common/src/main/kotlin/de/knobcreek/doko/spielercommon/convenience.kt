package de.knobcreek.doko.spielercommon

import de.knobcreek.doko.spielerspi.*

/**
 * @author arno
 */

class SpielContext(val spielSnapshot: SpielSnapshot) {
    val regel: Spielregel by lazy {
        if (spielSnapshot.vorbehalt == null) {
            Spielregel.create(spielSnapshot.variante, null)
        }
        else {
            Spielregel.create(spielSnapshot.variante, spielSnapshot.vorbehalt!!.second)
        }
    }

    val werBinIch = spielSnapshot.werBinIch
    val meineHand by lazy { KartenSet(spielSnapshot.hand) }

    val alleAußerMir = Spieler.values().filterNot { it == werBinIch }


    val alleKarten: KartenSet by lazy {
        TODO()
    }

    val farben = regel.farben()
    val fehlFarben = farben.filterNot { it.trumpf }

    val alleKartenJeFarbe: Map<SpielFarbe, KartenSet> by lazy {
        val result = HashMap<SpielFarbe, KartenSet>()
        for (farbe in regel.farben()) {
            result.put(farbe, alleKarten.filter { it.spielFarbe() == farbe })
        }
        result
    }

    val alleGespieltenKarten: KartenSet by lazy {
        spielSnapshot.fertigeStiche
                .fold(KartenSet.empty, { acc, stich -> acc + stich.karten }) +
                spielSnapshot.aktuellerStich.karten
    }

    val gespielteKarten: Map<Spieler, KartenSet> by lazy {
        TODO()
    }

    val alleUngespieltenKarten: KartenSet by lazy {
        alleKarten - alleUngespieltenKarten
    }

    companion object Access {
        operator fun invoke(): SpielContext = threadLocal.get()

        fun executeInContext(spielSnapshot: SpielSnapshot, code: () -> Unit) {
            threadLocal.set(SpielContext(spielSnapshot))
            try {
                code()
            }
            finally {
                threadLocal.remove()
            }
        }

        private val threadLocal = ThreadLocal<SpielContext>()
    }
}

val HerzZehn = Karte(Farbe.Herz, Wert.Zehn)
val KaroAs = Karte(Farbe.Karo, Wert.As)
val KreuzDame = Karte(Farbe.Kreuz, Wert.Dame)


fun Spielregel.hatTrumpf() = this.farben().any { f -> f.trumpf }

fun bedienendeKarten(farbe: SpielFarbe, karten: Iterable<Karte>): KartenSet =
        KartenSet(karten.filter { k -> SpielContext().regel.farbe(k) == farbe })

fun Karte.isHöherAls(zweite: Karte): Boolean =
        if(spielFarbe() == zweite.spielFarbe()) {
            SpielContext().regel.isZweiteKarteHöherRaw(zweite, this)
        }
        else {
            spielFarbe().trumpf
        }

fun Karte.spielFarbe() = SpielContext().regel.farbe(this)
fun Karte.isTrumpf() = spielFarbe() == SpielFarbe.Trumpf

fun Karte.isSpeziell() = this == HerzZehn //TODO oder Schweinchen oder mögliches Schweinchen oder Hyperschweinchen oder mögliches Hyperschweinchen

fun FertigerStich.karteVon(spieler: Spieler): Karte = karten[(spieler.ordinal - aufspiel.ordinal + 4) % 4]

fun Stich.farbe(): SpielFarbe? =
        if (karten.isNotEmpty()) {
            karten[0].spielFarbe()
        }
        else {
            null
        }

fun Stich.hatNichtBedient(spieler: Spieler): Boolean =
    //NB: Für offene Stiche ist das *nicht* das Gleiche wie !Stich.hatBedient()!

    if (this is FertigerStich) {
        karteVon(spieler).spielFarbe() != karten[0].spielFarbe()
    }
    else {
        val idx = (spieler.ordinal - aufspiel.ordinal + 4) % 4
        if (idx < karten.size) {
            karten[idx].spielFarbe() != karten[0].spielFarbe()
        }
        else {
            false
        }
    }


fun punkte(karte: Karte): Int = when(karte.wert) {
    Wert.Neun -> 0
    Wert.Zehn -> 10
    Wert.Bube -> 2
    Wert.Dame -> 3
    Wert.König -> 4
    Wert.As -> 11
}

fun punkte(karten: Iterable<Karte>): Int =
        karten.fold(0, { acc, k -> acc + punkte(k) } )




