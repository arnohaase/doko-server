package de.knobcreek.doko.ai_freedoko.heuristic

import de.knobcreek.doko.spielerspi.Karte
import de.knobcreek.doko.spielerspi.Stich

/**
 * @author arno
 */

interface HeuristikApi {
    fun conditionCanJab(stich: Stich): Boolean = TODO()
    fun conditionMustServe(stich: Stich): Boolean = TODO()
    fun conditionStartcard(stich: Stich): Boolean = if (stich.karten.isEmpty()) withRationale("Startkarte", true) else withRationale("Nicht Startkarte", false)

    fun addRationale(rat: String) { TODO() }
    fun <T> withRationale(rat: String, o: T): T {
        addRationale(rat)
        return o
    }
}

interface Heuristik: HeuristikApi {
    fun isValid(): Boolean
    fun conditionsMet(stich: Stich): Boolean
    fun cardToPlay(stich: Stich): Karte
}


