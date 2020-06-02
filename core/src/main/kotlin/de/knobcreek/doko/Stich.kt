package de.knobcreek.doko

class Stich(val nummer: Int,
            val aufgespielt: KartenBewertung,
            val letzterSpieler: Spieler,
            val karten: List<GespielteKarte>) {
    constructor(nummer: Int, aufgespielt: KartenBewertung, spieler: Spieler) :
            this(nummer, aufgespielt, spieler, listOf(GespielteKarte(0, aufgespielt, spieler)))

    fun nächsteKarte(karte: KartenBewertung, spieler: Spieler) =
            Stich(nummer, aufgespielt, spieler, karten.plus(GespielteKarte(karten.size, karte, spieler)))

    fun darfSpielen(spieler: Spieler) =
            spieler.nummer == (letzterSpieler.nummer + 1) % 4

    fun darfSpielen(karte: Karte, hatKarten: List<Karte>, kartenBewertungen: Map<Karte, KartenBewertung>) : Boolean {
        return karte in hatKarten &&
                (hatKarten.none { kt -> kartenBewertungen.getValue(kt).bedient(aufgespielt) } ||
                        kartenBewertungen.getValue(karte).bedient(aufgespielt))
    }

    fun gespielt() =
            karten.size == 4

    fun hatGewonnen() = // TODO Schweinchen
            karten
                    .maxBy { gespielteKarte ->
                        with(gespielteKarte.bewertung) {
                            when {
                                aufgespielt.trumpf || trumpf -> trumpfHöhe
                                karte.farbe == aufgespielt.karte.farbe -> karte.wert.ordinal
                                else -> 0
                            } * 10 + (if (trumpf && zweiteStichtErste) gespielteKarte.nummer else -gespielteKarte.nummer)
                        }
                    }!!
                    .spieler

    fun punkte() =
            karten
                    .map { gk -> gk.bewertung.karte.wert.punkte }
                    .sum()
}

data class GespielteKarte(val nummer: Int, val bewertung: KartenBewertung, val spieler: Spieler)
