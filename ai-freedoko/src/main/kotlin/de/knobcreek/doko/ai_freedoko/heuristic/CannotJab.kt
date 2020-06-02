package de.knobcreek.doko.ai_freedoko.heuristic

import de.knobcreek.doko.ai_freedoko.AiConfig
import de.knobcreek.doko.spielercommon.*
import de.knobcreek.doko.spielerspi.Farbe
import de.knobcreek.doko.spielerspi.Karte
import de.knobcreek.doko.spielerspi.Stich
import de.knobcreek.doko.spielerspi.Wert

/**
 * @author arno
 */
object CannotJab: Heuristik {
    override fun isValid(): Boolean = true

    override fun conditionsMet(stich: Stich) = !conditionStartcard(stich) && !conditionCanJab(stich)

    override fun cardToPlay(stich: Stich): Karte {
        if (conditionMustServe(stich)) {
            val result = if (stich.farbe()!!.trumpf) {
                this.billigeTrumpfKarte()!!
            }
            else {
                KartenSet(stich.karten).niedrigsteKarte(stich.farbe()!!)!!
            }
            addRationale("niedrigste bedienende Karte " + result)
            return result
        }

        if (SpielContext().meineHand.anzahlFehlKarten == 0) {
            return withRationale("billiger Trump (nur noch Trümpfe auf der Hand)", this.billigeTrumpfKarte()!!)
        }

        val fehl = this.billigeFehlKarte()
        val trumpf = this.billigeTrumpfKarte()
        val karte = this.chooseFehlOrTrump(fehl, trumpf)

        return withRationale(if (karte == fehl) "niedrigste Fehlkarte " + karte else "niedrigster Trumpf " + karte, karte)
    }

    private fun billigeFehlKarte(): Karte? =
            firstNotNull(
                    {searchSingleColorCard(Wert.Neun)},
                    {searchSingleColorCard(Wert.Bube)},
                    {searchSingleColorCard(Wert.Dame)},
                    {searchSingleColorCard(Wert.König)},
                    {searchColorCard(Wert.Neun)},
                    {searchColorCard(Wert.Bube)},
                    {searchColorCard(Wert.Dame)},
                    {searchColorCard(Wert.König)},
                    {searchSingleColorCard(Wert.Zehn)},
                    {searchColorCard(Wert.Zehn)},
                    {searchDoubleColorCard(Wert.As)},
                    {searchColorCard(Wert.As)}
            )

    private fun billigeTrumpfKarte(): Karte? {
        val meineHand = SpielContext().meineHand

        val niedrigsterTrumpf: Karte = meineHand.niedrigsteKarte(SpielFarbe.Trumpf) ?: return null

        if (niedrigsterTrumpf.punkte < 10) return niedrigsterTrumpf

        var zweitNiedrigsterTrumpf: Karte = meineHand.nächstHöhereKarte(niedrigsterTrumpf) ?: return niedrigsterTrumpf

        if (meineHand.anzahlTrümpfe <= 3 && !AiConfig.trump_card_limit.isHöherAls(meineHand.höchsteKarte(SpielFarbe.Trumpf)!!)) {
            if (zweitNiedrigsterTrumpf.punkte >= 10)      return niedrigsterTrumpf
            if (meineHand.anzahl(niedrigsterTrumpf) == 2) return niedrigsterTrumpf
        }

        if (zweitNiedrigsterTrumpf.punkte >= 10) {
            zweitNiedrigsterTrumpf = meineHand.nächstHöhereKarte(zweitNiedrigsterTrumpf) ?: return niedrigsterTrumpf
        }
        if (zweitNiedrigsterTrumpf.punkte >= 10)                        return niedrigsterTrumpf
        if (niedrigsterTrumpf == KaroAs && meineHand.anzahlTrümpfe > 2) return zweitNiedrigsterTrumpf

//TODO        if (!is_picture_solo_or_meatless(this->game().type())) {
        if (meineHand.anzahlTrümpfe <= 2*meineHand.anzahlNiedrigerOderGleich(zweitNiedrigsterTrumpf)
                && zweitNiedrigsterTrumpf.wert != Wert.Bube) {
            return niedrigsterTrumpf
        }
//        }

        if (AiConfig.trump_card_limit.isHöherAls(zweitNiedrigsterTrumpf))
            return zweitNiedrigsterTrumpf
        else
            return niedrigsterTrumpf
    }

    private fun searchColorCard(wert: Wert): Karte? {
        val meineHand = SpielContext().meineHand
        val farben = SpielContext().fehlFarben
                .filter { f -> meineHand.mitFarbe[f]!!.any { it.wert == wert } }
                .sortedBy { f -> meineHand.mitFarbe[f]!!.size }

        if (farben.isEmpty())
            return null
        else
            return meineHand.mitFarbe[farben.iterator().next()]!!.find { it.wert == wert }
    }

    private fun searchSingleColorCard(wert: Wert) =
            SpielContext()
                    .meineHand
                    .filter { it.wert == wert && !it.isTrumpf() }
                    .firstOrNull { k -> SpielContext().meineHand.mitFarbe[k.spielFarbe()]!!.size == 1 }

    private fun searchDoubleColorCard(wert: Wert): Karte? {
        val meineHand = SpielContext().meineHand
        for (farbe in Farbe.values()) {
            val karte = Karte(farbe, wert)
            if (!karte.isTrumpf() && meineHand.anzahl(karte) == 2 && meineHand.anzahl(karte.spielFarbe()) == 2) {
                return karte
            }
        }
        return null
    }

    private fun chooseFehlOrTrump(fehlKarte: Karte?, trumpfKarte: Karte?): Karte {
        if (fehlKarte == null) return trumpfKarte!!
        if (trumpfKarte == null) return fehlKarte

        //TODO   if (is_picture_solo_or_meatless(game.type())) return card_fehl;

        val meineHand = SpielContext().meineHand

        if (trumpfKarte.punkte >= 10) return withRationale("niedrige Trumpfkarte hat mindestens 10 Punkte", fehlKarte)
        if (fehlKarte.punkte < 10) {
            addRationale("niedrige Fehlkarte hat weniger als 10 Punkte")
            val höchsterTrumpf = meineHand.höchsteKarte(SpielFarbe.Trumpf)!!

            if (höchsterTrumpf.isSpeziell()) return fehlKarte
            if (höchsterTrumpf.wert == Wert.Neun) return withRationale("höchster Trumpf ist eine Neun", trumpfKarte)
            if (höchsterTrumpf.wert == Wert.König) {
                if (fehlKarte.wert == Wert.Neun) return fehlKarte
                return withRationale("Höchster Trumpf ist ein König, aber die niedrige Fehlkarte auch", trumpfKarte)
            }
            return fehlKarte
        }
        addRationale("Niedrige Fehlkarte mit mindestens 10 Punkten")
        if (trumpfKarte.isSpeziell()) return withRationale("Niedrige Trumpfkarte ist speziell", fehlKarte)
        if (trumpfKarte.wert == Wert.Dame) {
            addRationale("Niedriger Trumpf ist Dame")
            if (meineHand.anzahlTrümpfe >= meineHand.size * 3/4) return withRationale("viele Trümpfe", trumpfKarte)
            return withRationale("Nicht so viele Trümpfe", fehlKarte)
        }
        return withRationale("niedriger Trumpf ist kleiner als Dame", trumpfKarte)
    }
}
