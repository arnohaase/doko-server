package de.knobcreek.doko.uct

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ln
import kotlin.math.sqrt
import kotlin.random.Random

/**
 * @author arno
 */
data class Node(val id: Int, val playerToMove: Int) {
    val successors = ArrayList<Node?>()
    val moves = ArrayList<Move>()
    var parent: Node? = null
    var numVisits = 0
    val accumulatedReward = arrayOf(.0, .0, .0, .0)
}

class Uct(
        val options: Options,
        val state: BeliefGameState,
        val playersCards: Cards,
        val moveNumber: Int,
        val random: Random
) {
//    private val EPSILON = 0.0000001;
//
//    private var root: Node? = null;
//    val beliefGameStates = ArrayList<BeliefGameState>()
//    val averageRewards = Array(20, {i -> .0}) //TODO what is this array's max size?
//    val moveIndicesCount = Array(20, {i -> 0})
//    val uctPlayer = state.playerToMove
//    var nodesCounter = 0
//
//    init {
//        if(options.uctVersion == 1) {
//            root = Node(0, uctPlayer)
//            val cardAssignment = CardAssignment(options, state, playersCards)
//            for (i in 0..options.numberOfRollouts) {
//                val startState = BeliefGameState(state)
//                startState.setUctOutput(false)
//                cardAssignment.assignCardsToPlayers(startState)
//                rollout(startState, i)
//            }
//
//            for (j in 0..root!!.successors.size) {
//                averageRewards[j] += root!!.successors.get(j)!!.accumulatedReward[uctPlayer] / root!!.successors[j]!!.numVisits
//            }
//
//            val bestMove = calculateBestMoveIndex(root!!, options.numberOfRollouts)
//            moveIndicesCount[bestMove] += 1
//        }
//        else {
//            val cardAssignment = CardAssignment(options, state, playersCards)
//            for (i in 0..options.numberOfSimulations) {
//                root = Node(0, uctPlayer)
//                nodesCounter = 0
//                val oneState = BeliefGameState(state)
//                oneState.setUctOutput(false)
//                cardAssignment.assignCardsToPlayers(oneState)
//
//                beliefGameStates.add(oneState)
//                for (j in 0..options.numberOfRollouts) {
//                    val startState = BeliefGameState(oneState)
//                    rollout(startState, j)
//                }
//
//                for (j in 0..root!!.successors.size) {
//                    val sj = root!!.successors.get(j) ?: break
//
//                    // if number of rollouts is set to be smaller than the number of possible moves for the player then stop the loop as soon as encountering a non expanded node
//                    averageRewards[j] += sj.accumulatedReward[uctPlayer] / sj.numVisits
//
//                    val bestMove = calculateBestMoveIndex(root!!, options.numberOfRollouts)
//                    moveIndicesCount[bestMove] += 1
//                }
//            }
//        }
//    }
//
//    private fun calculateBestMoveIndex(node: Node,
//                                       numberOfRollout: Int,
//                                       state: BeliefGameState? = null,
//                                       moveIndex: AtomicInteger = AtomicInteger()): Int {
//        val withExplorationTerm = state != null
//
//        var maxIndex = -1
//        var bestRewardSoFar = .0
//        var onlyNegativeRewards = true
//
//        for (i in 0..node.successors.size) {
//            // if number of rollouts is set to be smaller than the number of possible moves for the player then stop the loop as soon as encountering a non expanded node (because nodes are expanded in the order of increasing indices)
//            val si = node.successors.get(i) ?: break
//
//            var currentMoveIndex = -1 // for uct version 1, need to store the index of vector legal moves and write it to move_index in the end. max_index is still needed for accessing the right successor
//            if (options.uctVersion == 1) {
//                if (state != null) { // if this method is called by get_best_move, then node == root and at root, all card assignments  yield the same successors because the uct player himself is being asked to play, thus only for the other cases, need to check if the current successor is actually consistent to the current card assignment. if not, do not consider it for computations.
//                    val legalMoves = state.legalMoves()
//                    var moveContained = false
//                    for (j in 0..legalMoves.size) {
//                        if (node.moves.get(j) == legalMoves.get(j)) {
//                            // NOTE: explanations about this extra check see "NOTE" below, at rollout()
//                            val stateCopy = BeliefGameState(state)
//                            stateCopy.setMove(stateCopy.playerToMove, legalMoves[j])
//                            if (stateCopy.playerToMove == si.playerToMove) {
//                                // NOTE: || node->successors[i]->player_to_move == -2 needs to be added as an additional check if also using version 0 to use this style of only adding successors to a node which are actually going to be visited and not all of them as it is done right now. No explanation found why version 1 does not require this extra check...
//                                moveContained = true
//                                currentMoveIndex = j
//                                break
//                            }
//                        }
//                    }
//                    if (!moveContained) {
//                        continue
//                    }
//                }
//            }
//
//            val numVisits = si.numVisits
//            var currentReward = si.accumulatedReward[node.playerToMove] / numVisits
//            if (options.announcementOption == 2
//                    && !withExplorationTerm
//                    && root!!.moves[0].isAnnouncementMove()
//                    && currentReward > 0
//            ) {
//                onlyNegativeRewards = false
//            }
//
//            var rewardCopy = currentReward
//            val explorationTerm = options.explorationConstant * sqrt(ln(1.0 * if (options.useWrongUctFormula) numberOfRollout else node.numVisits) / numVisits)
//            if (withExplorationTerm) {
//                currentReward += explorationTerm
//                rewardCopy += explorationTerm
//            }
//
//            if (maxIndex == -1 || (currentReward - bestRewardSoFar) > EPSILON) {
//                bestRewardSoFar = currentReward
//                if (options.uctVersion == 1 && withExplorationTerm) {
//                    moveIndex.set(currentMoveIndex)
//                }
//                maxIndex = i
//            }
//        }
//
//        if (options.announcementOption == 2
//                && !withExplorationTerm
//                && root!!.moves[0].isAnnouncementMove()
//                && onlyNegativeRewards
//        ) {
//            println("all successors yield a negative reward, forbid announcing")
//            System.exit(0) //TODO
//        }
//
//        return maxIndex
//    }
//
//    private fun propagateValues(currentNode_: Node, currentState: BeliefGameState) {
//        // current_node is the last visited node (which is in most of the cases not coinciding with current_state!)
//        var currentNode: Node? = currentNode_
//
//        val scorePoints = arrayOf(0, 0, 0, 0)
//        val playersPoints = arrayOf(0, 0, 0, 0)
//        val teamPoints = arrayOf(0, 0)
//        val uctRewards = arrayOf(.0, .0, .0, .0)
//
//        currentState.getScorePoints(scorePoints, playersPoints, teamPoints)
//        for (i in 0..4) {
//            uctRewards[i] = options.scorePointsConstant * scorePoints[i]
//            uctRewards[i] +=
//                    if (options.useTeamPoints) {
//                        teamPoints[currentState->playersTeam(i)]
//                    }
//                    else {
//                        playersPoints[i]
//                    } / options.playingPointsConstant
//        }
//        while (currentNode != null) { // go back to root (whose parent is 0)
//            currentNode.numVisits += 1
//            for (i in 0..4) {
//                currentNode.accumulatedReward[i] += uctRewards[i]
//            }
//            currentNode = currentNode.parent
//        }
//    }
//
//    private fun rollout(currentState: BeliefGameState, numberOfRollout: Int) {
//        var currentNode = root!!
//        var addedNewNode = false // this will be set to true as soon as the first node needs to be inserted. from then on, a MC simulation will be carried out either with adding further nodes to the tree or not, depending on the chosen options
//
//        while(true) {
//            // test if a terminal node or a terminal state (which is not the same if nodes are not added to tree as soon as one was added but the simulation is carried on) was reached
//            if (currentNode.playerToMove == -2 || currentState.gameFinished()) {
//                propagateValues(currentNode, currentState)
//                return
//            }
//
//            // check if there are unvisited successors or if a node was already added and thus just choose an arbitrary move
//            val legalMoves = currentState.legalMoves()
//            var chosenMove = -1
//            if (!addedNewNode) {
//                // a leaf node was not reached yet, thus check if there are (consistent in the case of uct version 1) successors of the current_node which have not been visited yet and choose one
//
//                val notContainedMovesIndices = ArrayList<Int>() // stores the indices of all moves from legal_moves which are not already a successor of the current node
//                if (options.uctVersion == 1) {
//                    // iterate over legal moves and check if they already exists as a successor in the tree, i.e. find all moves which are not in the tree yet if therer exist some. contrary to the case of uct version 0, do not add any other moves as uninitialized successors because if the algorithm reaches the same node in another rollout again, it will have a different card assignment
//                    for (i in legalMoves.indices) {
//                        var moveContained = false
//                        for (j in currentNode.moves.indices) {
//                            if (legalMoves.get(i) == currentNode.moves.get(j)) {
//                                // NOTE: in some rare cases, it could happen that when a player announced black at a node and this node was reached again later with a different card assignment, then a different player was the player to play next, because depending on the teams, the team mate of the announcing player was not allowed to do anymore announcings. To fix this, the following check on equal player to move in both the current node and the current state has been introduced.
//                                val stateCopy = BeliefGameState(currentState)
//                                stateCopy.setMove(stateCopy.playerToMove, legalMoves.get(i))
//                                if (stateCopy.playerToMove == currentNode.successors.get(j).playerToMove) {
//                                    moveContained = true
//                                    break
//                                }
//                            }
//                        }
//                        if (!moveContained) {
//                            notContainedMovesIndices.add(i)
//                        }
//                    }
//                }
//                else {
//                    if (currentNode.successors.isEmpty()) {
//                        // node has not been expanded: insert ALL successors, because if at any time, the algorithm will encounter this same node again and find an unvisited leaf, it will still have the same card assignment (because after each simulation, a new tree is constructed)
//
//                        for (i in legalMoves.indices) {
//                            currentNode.moves.add(legalMoves[i])
//                            currentNode.successors.add(null)
//                        }
//
//                        // iterate over all successors to see if there are still unvisited ones
//                        for (i in currentNode.successors.indices) {
//                            if (currentNode.successors.get(i) == null) {
//                                notContainedMovesIndices.add(i)
//                            }
//                        }
//                    }
//
//                }
//
//                if (notContainedMovesIndices.isNotEmpty()) {
//                    if (legalMoves.get(0).isCardMove()) {
//                        when (options.actionSelectionVersion) {
//                            0, 2 -> chosenMove = notContainedMovesIndices.get(0)
//                            1, 3 -> chosenMove = notContainedMovesIndices.get(rng.next(notContainedMovesIndices.size))
//                            else -> {
//                                val legalCards = ArrayList<Move>()
//                                for (i in notContainedMovesIndices.indices) {
//                                    legalCards.add(legalMoves.get(notContainedMovesIndices.get(i)))
//                                }
//                                var legalCardsIndex = currentState.bestMoveIndex(legalCards)
//                                if (chosenMove == -1) {
//                                    // no safe card was found
//                                    chosenMove = notContainedMovesIndices.get(rng.next(notContainedMovesIndices.size))
//                                }
//                            }
//                        }
//                    }
//                    else {
//                        // take the first not contained move and add the resulting new node
//                        chosenMove = notContainedMovesIndices.get(0)
//                    }
//                }
//            }
//            else {
//                // already added a node to the tree and thus only need to choose a move. depending on the chosen options, another node will be added or not (simulation only)
//                if (legalMoves.get(0).isCardMove()) {
//                    if (options.actionSelectionVersion >= 2) {
//                        chosenMove = currentState.bestMoveIndex(legalMoves)
//                        if (chosenMove == -1) {
//                            // no safe card was found
//                            chosenMove = rng.next(legalMoves.size)
//                        }
//                    }
//                    else {
//                        chosenMove = rng.next(legalMoves.size)
//                    }
//                }
//                else {
//                    // TODO: improve the simulation. for now: no announcing, no solo play
//                    chosenMove = 0
//                }
//
//                if (options.simulationOption == 0) {
//                    if (options.uctVersion == 0) {
//                        if (currentNode.successors.isEmpty()) {
//                            // node has not been expanded: insert ALL successors, because if at any time, the algorithm will encounter this same node again and find an unvisited leaf, it will still have the same card assignment (because after each simulation, a new tree is constructed)
//                            for (i in legalMoves.indices) {
//                                currentNode.moves.add(legalMoves.get(i))
//                                currentNode.successors.add(null)
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (chosenMove != -1) {
//                // already chose a node because there were either some unvisited successors left and no node was added so far or because a leaf node was reached and from then on it suffices to choose an arbitrary move (applying the uct formula would not work because no uct rewards are known for the successors of the current node)
//
//                currentState.setMove(currentState.playerToMove, legalMoves.get(chosenMove))
//                if (!addedNewNode || options.simulationOption == 0) {
//                    // no node was added yet or the chosen option requires to add all nodes encountered during a rollout
//                    nodesCounter += 1
//                    val nextNode = Node(nodesCounter, currentState.playerToMove)
//                    nextNode.parent = currentNode
//
//                    if (options.uctVersion == 1) {
//                        currentNode.moves.add(legalMoves.get(chosenMove))
//                        currentNode.successors.add(nextNode)
//                    }
//                    else {
//                        currentNode.successors.set(chosenMove, nextNode)
//                    }
//
//                    currentNode = nextNode
//                    if (currentState.gameFinished()) {
//                        currentNode.setPlayerToMove(-2)
//                    }
//                    addedNewNode = true
//                }
//            }
//            else {
//                // all successors of the current node have been visited at least once and thus need to follow the one with the highest value according the uct formula
//
//                val moveIndex = AtomicInteger(0)
//                val maxIndex = calculateBestMoveIndex(currentNode, numberOfRollout, currentState, moveIndex)
//                // update current_state and current_node according to the chosen move
//                if (options.uctVersion == 1) {
//                    currentState.setMove(currentState.playerToMove, legalMoves(moveIndex.get()))
//                }
//                else {
//                    currentState.setMove(currentState.playerToMove, legalMoves(maxIndex))
//                }
//
//                currentNode = currentNode.successors.get(maxIndex)
//            }
//        }
//    }
//
////    fun dotRec
//
//    // fun dot()
//
//    fun getBestMove() {
//        TODO()
//    }
}
