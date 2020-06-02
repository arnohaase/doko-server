package de.knobcreek.doko.spielercommon

import de.knobcreek.doko.spielerspi.Farbe
import de.knobcreek.doko.spielerspi.Karte
import de.knobcreek.doko.spielerspi.Wert
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author arno
 */
class KartenSetTest {
    val KaroAs = Karte(Farbe.Karo, Wert.As)
    val HerzZehn = Karte(Farbe.Herz, Wert.Zehn)
    val KreuzDame = Karte(Farbe.Kreuz, Wert.Dame)

    @Test
    fun testPlusMinus() {
        var ks = KartenSet.empty
        assertEquals(0, ks.size)

        ks += KaroAs
        ks += HerzZehn
        assertEquals(KartenSet(listOf(KaroAs, HerzZehn)), ks)

        ks += listOf(KaroAs, HerzZehn)
        assertEquals(KartenSet(listOf(KaroAs, HerzZehn, KaroAs, HerzZehn)), ks)

        ks += KreuzDame
        ks += KreuzDame
        assertEquals(KartenSet(listOf(KaroAs, HerzZehn, KaroAs, HerzZehn, KreuzDame, KreuzDame)), ks)

        ks -= listOf(HerzZehn, HerzZehn)
        assertEquals(KartenSet(listOf(KaroAs, KaroAs, KreuzDame, KreuzDame)), ks)

        ks -= listOf(KaroAs)
        assertEquals(KartenSet(listOf(KaroAs, KreuzDame, KreuzDame)), ks)

        ks -= KreuzDame
        assertEquals(KartenSet(listOf(KaroAs, KreuzDame)), ks)
    }

    @Test
    fun testContains() {
        assertFalse(KartenSet(listOf()).contains(KreuzDame))
        assertFalse(KartenSet(listOf(KaroAs)).contains(KreuzDame))
        assertTrue(KartenSet(listOf(HerzZehn, KaroAs, KreuzDame)).contains(KaroAs))

        assertEquals(0, KartenSet(listOf()).anzahl(KreuzDame))
        assertEquals(0, KartenSet(listOf(KaroAs)).anzahl(KreuzDame))
        assertEquals(1, KartenSet(listOf(KaroAs)).anzahl(KaroAs))

        assertEquals(2, KartenSet(listOf(KaroAs, KreuzDame, KaroAs, HerzZehn)).anzahl(KaroAs))
        assertEquals(1, KartenSet(listOf(KaroAs, KreuzDame, KaroAs, HerzZehn)).anzahl(KreuzDame))
    }
}
