package de.knobcreek.doko.ai_freedoko.derived

import de.knobcreek.doko.spielercommon.*
import de.knobcreek.doko.spielerspi.*
import java.util.*


/**
 * ermittelt auf Basis des aktuellen Snapshots gesicherte Informationen zur Verteilung der verbleibenden Karten
 *
 *  @author arno
 */
class KartenConstraints(private val spiel: SpielSnapshot) {

    val anzahlHandkarten: Map<Spieler, Int> by lazy {
        val basisAnzahl = if (spiel.variante.mitNeunen) 12 else 10

        SpielContext().gespielteKarten
                .map { it.key to basisAnzahl - it.value.size }
                .toMap()
    }

    val hatNichtBedient: Map<Spieler, Set<SpielFarbe>> by lazy {
        Spieler.values()
                .map {
                    s ->
                    val farben = spiel.fertigeStiche
                            .filter { it.hatNichtBedient(s) }
                            .map { it.farbe()!! }
                            .toMutableSet()
                    if(spiel.aktuellerStich.hatNichtBedient(s)) {
                        farben.add(spiel.aktuellerStich.farbe()!!)
                    }
                    s to farben
                }.toMap()
    }

    /**
     * ... und sie noch nicht gespielt, d.h. er hat sie sicher noch auf der Hand. 'true' bedeutet hat sicher KreuzDame,
     *  'false' bedeutet 'hat sicher keine KreuzDame'
     */
    private val hatKreuzDameWegenReNormalesSpiel: Map<Spieler, Boolean> by lazy {
        val result = HashMap<Spieler,Boolean>()

        // Ansagen
        for (aktion in spiel.journal) {
            val spieler = aktion.first
            if (aktion.second is Re) {
                result.put(spieler, true)
            }
            if (aktion.second is Contra) {
                result.put(spieler, false)
            }
        }

        // ich habe noch eine KreuzDame auf der Hand
        if (spiel.hand.contains(KreuzDame)) {
            result.put(spiel.werBinIch, true)
        }


        // bereits gespielte KreuzDame
        for (spieler in Spieler.values()) {
            if (SpielContext().gespielteKarten[spieler]!!.contains(KreuzDame)) {
                result.put(spieler, true)
            }
        }

        // 2x Re bedeutet, dass die anderen beiden Spieler Kontra sind
        val notRe = result.filterNot { it.value == true }
        if (notRe.size == 2) {
            for (s in notRe) {
                result.put(s.key, false)
            }
        }
        // ... und umgekehrt für 2x Kontra
        val notKontra = result.filterNot { it.value == false }
        if (notKontra.size == 2) {
            for (s in notKontra) {
                result.put(s.key, false)
            }
        }

        // bereits gespielte KreuzDamen entfernen
        for (spieler in Spieler.values()) {
            if (SpielContext().gespielteKarten[spieler]!!.contains(KreuzDame)) {
                result.put(spieler, false)
            }
        }

        //TODO stille Hochzeit

        result
    }

    private val anzahlKreuzDamenWegenHochzeit: Int by lazy { //TODO Spieler mit zurückgeben?
        val spieler = spiel.vorbehalt!!.first
        val anzahlGespielt = SpielContext().gespielteKarten[spieler]!!.count{ it == KreuzDame }
        2 - anzahlGespielt
    }

    private val hatNurEinAnderer: Map<SpielFarbe, Spieler> by lazy {
        SpielFarbe.values()
                .map { f -> f to SpielContext().alleAußerMir.filterNot { spieler -> hatNichtBedient[spieler]!!.contains(f) }}
                .filter { it.second.size == 1 }
                .map { it.first to it.second.iterator().next() }
                .toMap()
    }

    val hatSicher: Map<Spieler, KartenSet> by lazy {
        


        TODO()
    }
    val hatSicherNicht: Map<Spieler, KartenSet> by lazy {
        TODO()
    }
    val kannHaben: Map<Spieler, KartenSet> by lazy {
        TODO()
    }
}
