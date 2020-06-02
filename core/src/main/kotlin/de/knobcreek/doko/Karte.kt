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

data class Karte(val farbe: Farbe,
                 val wert: Wert) {
    override fun toString() =
            farbe.text + wert.text
}

val herzZehn = Karte(Farbe.HERZ, Wert.ZEHN)
val fuchs = Karte(Farbe.KARO, Wert.AS)
val reDame = Karte(Farbe.KREUZ, Wert.DAME)

/**
 * Erzeugen eines einfachen Kartensatzes
 */
fun erzeugeKarten(mitNeunen: Boolean) =
        Farbe.values()
                .flatMap { farbe ->
                    Wert.values()
                            .filter { wert -> mitNeunen || wert != Wert.NEUN }
                            .map { wert -> Karte(farbe, wert) }
                }
