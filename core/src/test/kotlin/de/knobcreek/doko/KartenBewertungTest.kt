package de.knobcreek.doko

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class KartenBewertungTest {

    enum class Parameter(val bewertung: KartenBewertung, val bedient: KartenBewertung, val erwartet: Boolean) {
        HERZ_BEDIENT_HERZ(herzKönig, herzAs, true),
        BUBE_BEDIENT_NICHT(herzBube, herzAs, false),
        HERZ10_BEDIENT_NICHT(herzZehn, herzAs, false),
        FUCHS_BEDIENT_TRUMPF(fuchs, herzBube, true),
        HERZ10_BEDIENT_TRUMPF(herzZehn, herzBube, true),
        HERZ_BEDIENT_NICHT(herzAs, herzBube, false);

        constructor(karte: Karte, bedient: Karte, erwartet: Boolean) :
                this(KartenBewertung(karte, Spielregel.REGULÄR, true),
                        KartenBewertung(bedient, Spielregel.REGULÄR, true),
                        erwartet)
    }

    @ParameterizedTest
    @EnumSource(Parameter::class)
    fun bedient(parameter: Parameter) {
        assertEquals(parameter.erwartet, parameter.bewertung.bedient(parameter.bedient))
    }
}

val herzBube = Karte(Farbe.HERZ, Wert.BUBE)
val herzKönig = Karte(Farbe.HERZ, Wert.KÖNIG)
val herzAs = Karte(Farbe.HERZ, Wert.AS)
val fuchs = Karte(Farbe.KARO, Wert.AS)
