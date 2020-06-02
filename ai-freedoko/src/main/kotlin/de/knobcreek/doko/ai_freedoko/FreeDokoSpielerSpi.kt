package de.knobcreek.doko.ai_freedoko

import de.knobcreek.doko.spielerspi.*

/**
 * @author arno
 */
class FreeDokoSpielerSpi: SpielerSpi {
    override fun id() = "free-doko-ai"

    override fun vorbehalt(variante: RegelVariante, werBinIch: Spieler, ersterAufspieler: Spieler, hand: List<Karte>): Vorbehalt? {
        TODO("Not yet implemented")
    }

    override fun aktion(snapshot: SpielSnapshot): SpielerAktion {
        TODO("Not yet implemented")
    }
}

object AiConfig {
    val trump_card_limit = Karte(Farbe.Herz, Wert.Dame)     //TODO Ai::trump_card_limit --> abhängig vom Solo etc

}