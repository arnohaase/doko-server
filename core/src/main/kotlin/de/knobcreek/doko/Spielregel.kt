package de.knobcreek.doko

/**
 * Interessanterweise sind die Spielregeln unabhängig davon, ob ein Solo
 * gespielt wird oder nicht.  Die "Standard-Spielregel" ist KARO.
 */
enum class Spielregel(val istTrumpf: (Karte) -> Boolean,
                      val trumpfHöhe: (Karte) -> Int) {
    REGULÄR(Farbe.KARO),
    HOCHZEIT(Farbe.KARO),
    ARMUT(Farbe.KARO),
    FLEISCHLOS({ _ -> false }, { _ -> 0 }),
    KARO(Farbe.KARO),
    HERZ(Farbe.HERZ),
    PIK(Farbe.PIK),
    KREUZ(Farbe.KREUZ),
    BUBEN(Wert.BUBE),
    DAMEN(Wert.DAME);

    /**
     * Die normalen Spielregeln oder ein Farbsolo
     */
    constructor(trumpfFarbe: Farbe) :
            this({ karte -> istTrumpf(trumpfFarbe, karte) },
                    { karte -> trumpfHöhe(trumpfFarbe, karte) })

    /**
     * Damen- oder Bubensolo
     */
    constructor(trumpf: Wert) :
            this({ karte -> karte.wert == trumpf },
                    { karte -> karte.farbe.ordinal + 10 })
}

fun istTrumpf(trumpfFarbe: Farbe, karte: Karte) =
        with (karte) {
            farbe == trumpfFarbe || wert == Wert.BUBE || wert == Wert.DAME || karte == herzZehn
        }

/**
 * Die Trumpfhöhe ist immer höher als der Wert einer Fehlfarbe.
 */
fun trumpfHöhe(trumpfFarbe: Farbe, karte: Karte) =
        with (karte) {
            when {
                karte == herzZehn -> 99
                wert == Wert.DAME -> 30 + farbe.ordinal
                wert == Wert.BUBE -> 20 + farbe.ordinal
                farbe == trumpfFarbe -> 10 + wert.ordinal
                else -> 0
            }
        }

/**
 * Die Herz-10 wird nur markiert, wenn die Sonderregel "zweite sticht erste" aktiv ist.
 */
data class KartenBewertung(val karte: Karte,
                           val trumpf: Boolean,
                           val trumpfHöhe: Int,
                           val zweiteStichtErste: Boolean) {
    constructor(karte: Karte, spielregel: Spielregel, zweiteStichtErste: Boolean) :
            this(karte,
                    spielregel.istTrumpf(karte),
                    spielregel.trumpfHöhe(karte),
                    zweiteStichtErste && karte == herzZehn)

    fun bedient(aufgespielt: KartenBewertung) =
            if (aufgespielt.trumpf) trumpf else !trumpf && karte.farbe == aufgespielt.karte.farbe
}
