package de.knobcreek.doko

data class Spieler(val nummer: Int)

class SpielStatus(val partei: Partei) {
    var gespielteKarten = 0
    val ansagen = ArrayList<Ansage>()
}

class TurnierStatus
