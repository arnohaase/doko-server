package de.knobcreek.doko

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource

internal class StichTest {
    val spieler = (0..3)
            .map { n -> Spieler(n) }

    val pik = listOf(Wert.KÖNIG, Wert.ZEHN, Wert.AS)
            .map { wert -> Karte(Farbe.PIK, wert) }

    @Test
    fun darfSpielen() {
        val stich = Stich(0, reguläreBewertungen.getValue(pikAs), spieler[0])
        assertEquals(pikAs, stich.aufgespielt.karte)
        assertEquals(1, stich.karten.size)

        (1..3).forEach { n -> assertEquals(n == 1, stich.darfSpielen(spieler[n])) }
        assertTrue(stich.darfSpielen(pikAs, pik, reguläreBewertungen))
    }

    @Test
    fun darfNichtSpielen() {
        val stich = Stich(0, reguläreBewertungen.getValue(pikAs), spieler[3])

        (0..2).forEach { n -> assertEquals(n == 0, stich.darfSpielen(spieler[n]), "n = $n") }

        // hat er gar nicht
        assertFalse(stich.darfSpielen(karoNeun, pik, reguläreBewertungen))
        // darf er trotzdem nicht
        assertFalse(stich.darfSpielen(karoNeun, pik + karoNeun, reguläreBewertungen))
    }

    enum class Parameter(val aufspiel: KartenBewertung) {
        KARO_NEUN(karoNeun),
        PIK_AS(pikAs);

        constructor(karte: Karte) : this(reguläreBewertungen.getValue(karte))
    }

    @ParameterizedTest
    @EnumSource(Parameter::class)
    fun `wird aufgespielt und sticht`(parameter: Parameter) {
        val aufspiel = Stich(0, parameter.aufspiel, spieler[0])

        var stich = aufspiel
        for (n in 0..2)
            stich = stich.nächsteKarte(reguläreBewertungen.getValue(pik[n]), spieler[n + 1])

        assertEquals(4, stich.karten.size)
        assertSame(spieler[0], stich.hatGewonnen())
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3])
    fun `Pik wird aufgespielt, Karo 9 sticht`(karoSticht: Int) {

        val aufspiel = Stich(0, reguläreBewertungen.getValue(pik[0]), spieler[0])
        var stich = aufspiel

        for (n in 1 until karoSticht)
            stich = stich.nächsteKarte(reguläreBewertungen.getValue(pik[n]), spieler[n])

        stich = stich.nächsteKarte(reguläreBewertungen.getValue(karoNeun), spieler[karoSticht])

        for (n in karoSticht..2)
            stich = stich.nächsteKarte(reguläreBewertungen.getValue(pik[n]), spieler[n + 1])

        assertEquals(4, stich.karten.size)
        assertSame(spieler[karoSticht], stich.hatGewonnen())
    }

    @Test
    fun `fleischlos sticht die erste Herz 10`() {
        val karten = listOf(Wert.BUBE, Wert.ZEHN, Wert.DAME, Wert.ZEHN)
                .map { wert -> Karte(Farbe.HERZ, wert) }
                .map { karte -> KartenBewertung(karte, Spielregel.FLEISCHLOS, true) }

        val gespielteKarten = karten.indices
                .map { n -> GespielteKarte(n, karten[n], spieler[n]) }

        val stich = Stich(0, karten[0], spieler[3], gespielteKarten)

        assertSame(spieler[1], stich.hatGewonnen())
    }

    fun zweiteStichtImmer(karten: List<KartenBewertung>) {
        val gespielteKarten = karten.indices
                .map { n -> GespielteKarte(n, karten[n], spieler[n]) }
        val aufspiel = Stich(0, karten[0], spieler[2], gespielteKarten)

        val stich = aufspiel.nächsteKarte(reguläreBewertungen.getValue(herzZehn), spieler[3])

        assertEquals(4, stich.karten.size)
        assertSame(spieler[3], stich.hatGewonnen())
    }

    @TestFactory
    fun `zweite Herz 10 sticht immer`() : List<DynamicTest> {
        val alleKarten = erzeugeKarten(true)

        return alleKarten
                .flatMap { karte1 -> alleKarten
                        // nur eine Herz-10
                        .filter { karte2 -> karte1 != herzZehn || karte2 != herzZehn }
                        .flatMap { karte2 -> alleKarten
                                .filter { karte3 ->
                                    if (karte1 == herzZehn || karte2 == herzZehn)
                                        karte3 != herzZehn
                                    else
                                        karte3 != karte1 || karte3 != karte2
                                }
                                .map { karte3 -> DynamicTest.dynamicTest("$karte1-$karte2-$karte3") {
                                    zweiteStichtImmer(listOf(karte1, karte2, karte3).map { karte -> reguläreBewertungen.getValue(karte) })
                                }}
                        }
                }
    }
}

val karoNeun = Karte(Farbe.KARO, Wert.NEUN)
val herzZehn = Karte(Farbe.HERZ, Wert.ZEHN)
val pikAs = Karte(Farbe.PIK, Wert.AS)

val reguläreBewertungen = erzeugeKarten(true)
        .associateWith { karte -> KartenBewertung(karte, Spielregel.REGULÄR, true) }
