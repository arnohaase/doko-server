package de.knobcreek.doko.spielercommon

import de.knobcreek.doko.spielerspi.Karte

/**
 * @author arno
 */
class KartenSet(private val karten: List<Karte>): Iterable<Karte> {
    override fun iterator(): Iterator<Karte> = karten.iterator()

    operator fun plus(karte: Karte?) = if (karte == null) this else KartenSet(karten + karte)
    operator fun plus(karten: Iterable<Karte>) = KartenSet(this.karten + karten)

    operator fun minus(karte: Karte?) = if (karte == null) this else KartenSet(karten - karte)
    operator fun minus(karten: Iterable<Karte>): KartenSet {
        //NB '-' mit einer Collection als Argument hat hier andere Semantik
        val result = this.karten.toMutableList()
        for (karte in karten) {
            result -= karte
        }
        return KartenSet(result)
    }

    fun filter(pred: (Karte) -> Boolean) = KartenSet(karten.filter(pred))

    fun contains(karte: Karte) = karten.contains(karte)
    fun anzahl(karte: Karte) = karten.count { k -> k == karte }
    fun anzahl(farbe: SpielFarbe) = mitFarbe[farbe]!!.size

    val size = karten.size

    val anzahlFehlKarten = count { !it.spielFarbe().trumpf }
    val anzahlTrümpfe = size - anzahlFehlKarten

    val mitFarbe: Map<SpielFarbe, KartenSet> by lazy {
        SpielContext().farben
                .map { farbe -> farbe to filter { it.spielFarbe() == farbe } }
                .toMap()
    }

    fun höchsteKarte(farbe: SpielFarbe): Karte? {
        val f = mitFarbe[farbe]!!
        return when(f.size) {
            0 -> null
            else -> {
                var candidate = f.iterator().next()

                for (karte in f) {
                    if (karte.isHöherAls(candidate)) {
                        candidate = karte
                    }
                }

                candidate
            }
        }
    }
    fun niedrigsteKarte(farbe: SpielFarbe): Karte? {
        val f = mitFarbe[farbe]!!
        return when(f.size) {
            0 -> null
            else -> {
                var candidate = f.iterator().next()

                for (karte in f) {
                    if (candidate.isHöherAls(karte)) {
                        candidate = karte
                    }
                }

                candidate
            }
        }
    }

    fun nächstHöhereKarte(karte: Karte): Karte? {
        val candidates = filter { it.isHöherAls(karte) }
        return when(candidates.size) {
            0 -> null
            else -> candidates.fold(candidates.iterator().next(), {
                acc, el -> if (acc.isHöherAls(el)) el else acc
            })
        }
    }

    fun anzahlNiedrigerOderGleich(karte: Karte) = count { ! it.isHöherAls(karte) }

    override fun equals(other: Any?): Boolean {
        return if (other is KartenSet) {
            this.karten == other.karten
        }
        else {
            false
        }
    }

    companion object Factory {
        val empty = KartenSet(listOf())
    }
}