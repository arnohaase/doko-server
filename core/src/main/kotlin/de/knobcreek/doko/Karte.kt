package de.knobcreek.doko

enum class Farbe(val text: String) {
    KARO("♦"), HERZ("♥"), PIK("♠"), KREUZ("♣")
}

/**
 * Wenn es Trümpfe außer Buben oder Damen gibt (z. B. im normalen Spiel), sind
 * die Damen höher als die Buben höher als die anderen.
 * Die natürliche Reihenfolge gilt, wenn alles eingereiht ist.
 */
enum class Wert(val punkte: Int, val text: String) {
    NEUN(0, "9"),
    BUBE(2, "B"),
    DAME(3, "D"),
    KÖNIG(4, "K"),
    ZEHN(10, "10"),
    AS(11, "As");
}

/**
 * Die Herz-10 wird nur markiert, wenn die Sonderregel "zweite sticht erste" aktiv ist.
 */
data class Karte(val farbe: Farbe,
                 val wert: Wert,
                 val trumpf: Boolean,
                 val trumpfHöhe: Int,
                 val herzZehn: Boolean) {
    constructor(farbe: Farbe, wert: Wert, spielregel: Spielregel, zweiteStichtErste: Boolean) :
            this(farbe, wert,
                    spielregel.istTrumpf(farbe, wert),
                    spielregel.trumpfHöhe(farbe, wert),
                    zweiteStichtErste && herzZehn(farbe, wert))

    fun bedient(karte: Karte) =
            if (karte.trumpf) trumpf else !trumpf && farbe == karte.farbe

    override fun toString() =
            farbe.text + wert.text
}

fun herzZehn(farbe: Farbe, wert: Wert) =
        farbe == Farbe.HERZ && wert == Wert.ZEHN

val reDame = Karte(Farbe.KREUZ, Wert.DAME, true, trumpfHöhe(Farbe.KARO, Farbe.KREUZ, Wert.DAME), false)

/**
 * Erzeugen eines einfachen Kartensatzes mit der angegebenen Spielregel
 */
fun erzeugeKarten(spielregel: Spielregel, mitNeunen: Boolean, zweiteStichtErste: Boolean) =
        Farbe.values()
                .flatMap { farbe ->
                    Wert.values()
                            .filter { wert -> mitNeunen || wert != Wert.NEUN }
                            .map { wert -> Karte(farbe, wert, spielregel, zweiteStichtErste) }
                }
