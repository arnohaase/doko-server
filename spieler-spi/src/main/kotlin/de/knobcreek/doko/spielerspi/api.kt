package de.knobcreek.doko.spielerspi

/**
 * @author arno
 */
interface SpielerSpi {
    fun id(): String

    //TODO Pflichtsoli, Turnierstand, Rundennummer etc.
    /**
     * Jeder Spieler wird vor Beginn des eigentlichen Spiels gefragt, ob bzw.
     * welchen Vorbehalt er anmeldet. Die anderen Spieler sehen später nur den
     * einen Vorbehalt, der die höchste Priorität hat
     */
    fun vorbehalt(variante: RegelVariante, werBinIch: Spieler, ersterAufspieler: Spieler, hand: List<Karte>): Vorbehalt?

    /**
     * Liefert die Aktion eines Spielers auf Basis des aktuellen Spielstandes.
     * Jeder Spieler wird über jede Aktion jedes anderen Spielers
     * benachrichtigt, und jeder Spieler muss jeweils eine SpielerAktion
     * zurückgeben ('Ack', wenn er nicht am Zug ist und keine Ansage machen
     * möchte).
     */
    fun aktion(snapshot: SpielSnapshot): SpielerAktion
}


data class RegelVariante(
        val mitNeunen: Boolean,
        val soli: List<SoloTyp>,
        val zweiteStichtErste: Boolean
// Schweinchen
)

enum class Farbe {
    Kreuz, Pik, Herz, Karo
}
enum class Wert(val punkte: Int) {
    Neun(0), Bube(2), Dame(3), König(4), Zehn(10), As(11)
}
data class Karte(val farbe: Farbe, val wert: Wert) {
    val punkte = wert.punkte
}

enum class Spieler {
    Eins, Zwei, Drei, Vier;

    fun nächster() = Spieler.values()[(ordinal + 1)%4];
}

sealed class Stich {
    abstract val aufspiel: Spieler
    abstract val karten: List<Karte>
}
data class OffenerStich(
        override val aufspiel: Spieler,
        override val karten: List<Karte>
): Stich()
data class FertigerStich(
        override val aufspiel: Spieler,
        override val karten: List<Karte>,
        val gewinner: Spieler
): Stich()

enum class SoloTyp {
    Fleischlos,
    Karo, Herz, Pik, Kreuz,
    Buben, Damen
}

enum class HochzeitTyp {
    ErsterFremder,
    ErsterFremderTrumpf
}

sealed class HochzeitKlärung
object HochzeitAllein: HochzeitKlärung()
data class HochzeitMit(val spieler: Spieler): HochzeitKlärung()

sealed class Vorbehalt
data class Solo(val typ: SoloTyp): Vorbehalt()

// Aus Sicht des Spielers passt es durchaus, die Klärung der Hochzeit (sofern
// erfolgt) in den Vorbehalt aufzunehmen
data class Hochzeit(val typ: HochzeitTyp, val klärung: HochzeitKlärung?): Vorbehalt()

enum class AnsageHöhe {
    Sieg, Keine90, Keine60, Keine30, Schwarz
}

sealed class SpielerAktion
data class KarteGespielt(val karte: Karte): SpielerAktion()
data class Re(val höhe: AnsageHöhe): SpielerAktion()
data class Kontra(val höhe: AnsageHöhe): SpielerAktion()
object Ack: SpielerAktion() // als explizite Bestätigung, um "höflich" zu spielen - Spieler geben sich gegenseitig Zeit für Ansagen


data class SpielSnapshot(
        val variante: RegelVariante,

        // Information für den Spieler, wer "er selbst" ist
        val werBinIch: Spieler,
        val hand: List<Karte>,

        // nur ein Vorbehalt kann "greifen", die anderen bleiben unsichtbar
        val vorbehalt: Pair<Spieler, Vorbehalt>?,

        val fertigeStiche: List<FertigerStich>,
        val aktuellerStich: Stich,

        val journal: List<Pair<Spieler, SpielerAktion>>
)
