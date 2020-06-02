package de.knobcreek.doko.ai_freedoko.heuristic

import de.knobcreek.doko.spielercommon.SpielContext
import de.knobcreek.doko.spielerspi.Hochzeit
import de.knobcreek.doko.spielerspi.Karte
import de.knobcreek.doko.spielerspi.Solo
import de.knobcreek.doko.spielerspi.Stich

/**
 * @author arno
 */
object BestWinningCard: Heuristik {
    override fun isValid(): Boolean =
            if (SpielContext().spielSnapshot.vorbehalt == null) {
                true
            }
            else {
                when(SpielContext().spielSnapshot.vorbehalt!!.second) {
                    is Hochzeit -> true
                    is Solo -> false
                }
            }

    override fun conditionsMet(stich: Stich) =  !conditionStartcard(stich) && !conditionHighWinnerCardOfOwnTeam(stich)

    override fun cardToPlay(stich: Stich): Karte {
//        return Heuristics::best_winning_card(trick, ai)

        TODO()
    }

    private fun conditionHighWinnerCardOfOwnTeam(stich: Stich): Boolean {


        /*
  auto const& winnerplayer = trick.winnerplayer();
  if (!this->guessed_same_team(winnerplayer)) {
    this->rationale_.add(_("Heuristic::condition::winnerplayer is of opposite team"));
    return false;
  }
  this->rationale_.add(_("Heuristic::condition::winnerplayer is of the same team"));
  auto const& winnercard = trick.winnercard();
  if (winnercard.istrump()) {
    auto const trumplimit_normal = this->value(Aiconfig::Type::trumplimit_normal);
    if (trumplimit_normal.less(winnercard)) {
      this->rationale_.add(_("Heuristic::condition::trumplimit low (%s) is less then the winnercard", _(trumplimit_normal)));
      return true;
    } else {
      this->rationale_.add(_("Heuristic::condition::trumplimit low (%s) is not less then the winnercard", _(trumplimit_normal)));
      return false;
    }
  } else { // color trick
    if (winnercard.value() != Card::ace) {
      this->rationale_.add(_("Heuristic::condition::winnercard not ace"));
      return false;
    } else if (this->cards_information().played(winnercard.tcolor()) <= trick.actcardno()) {
      this->rationale_.add(_("Heuristic::condition::winnercard is ace and the color has not been played before"));
      return true;
    } else {
      this->rationale_.add(_("Heuristic::condition::winnercard is ace and the color has already been played before"));
      return false;
    }
  }

         */
        TODO("Not yet implemented")
    }


}