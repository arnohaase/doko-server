package de.knobcreek.doko.uct

/**
 * @author arno
 */
data class Options(val uctVersion: Int,
                   val numberOfRollouts: Int,
                   val numberOfSimulations: Int,
                   val announcementOption: Int,
                   val explorationConstant: Double,
                   val useWrongUctFormula: Boolean,
                   val actionSelectionVersion: Int,
                   val simulationOption: Int,
                   val scorePointsConstant: Double,
                   val useTeamPoints: Boolean,
                   val playingPointsConstant: Double
) {

}