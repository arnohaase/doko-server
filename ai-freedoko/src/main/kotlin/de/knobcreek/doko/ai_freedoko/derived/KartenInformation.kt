package de.knobcreek.doko.ai_freedoko.derived

import de.knobcreek.doko.spielercommon.*
import de.knobcreek.doko.spielerspi.*
import java.util.*


/**
 * Abgeleitete Informationen - sowohl gesicherte als auch heuristisch wahrscheinliche - darüber, welche Karten
 *  noch im Spiel sind und wer sie hat
 *
 * @author arno
 */

class KartenInformation(val spiel: SpielSnapshot) {

//    fun anzahlAufgespielt(farbe: SpielFarbe): Int = spiel.fertigeStiche.count { s -> spiel.farbe(s) == farbe } // color_runs
//
//    val anzahlFarbstiche: Int by lazy { spiel.fertigeStiche.size - anzahlAufgespielt(SpielFarbe.Trumpf) }
//    val anzahlTrumpfstiche: Int by lazy { anzahlAufgespielt(SpielFarbe.Trumpf) } // trump_runs
//
//    val anzahlSpielerMitUnbekanntenKarten: Int by lazy { TODO() } // remaining_unknown_players

//    val verbleibendeKarten: List<Karte> by lazy {
//        val result = spiel.alleKarten().toMutableList()
//        result.removeAll(spiel.alleGespieltenKarten())
//        result
//    }
//    val verbleibendeKartenVonAnderen: List<Karte> by lazy {
//        val result = verbleibendeKarten.toMutableList()
//        result.removeAll(spiel.hand)
//        result
//    }
//
//    val verbleibendeTrümpfe: List<Karte> by lazy {
//        verbleibendeKarten.filter { k -> spiel.spielregel().farbe(k).trumpf }
//    }
//    val höchsterVerbleibenderTrumpf: Karte? by lazy { spiel.spielregel().höchsteKarte(verbleibendeTrümpfe) } // highest_remaining_trump
//
//    val verbleibendeTrümpfeVonAnderen: List<Karte> by lazy {
//        verbleibendeKartenVonAnderen.filter { k -> spiel.spielregel().farbe(k).trumpf }
//    } // highest_remaining_trump_of_others
//    val höchsterVerbleibenderTrumpfVonAnderen: Karte? by lazy {
//        spiel.spielregel().höchsteKarte(verbleibendeTrümpfeVonAnderen)
//    }
//
//    fun verbleibendeKarten(farbe: SpielFarbe): List<Karte> =
//            verbleibendeKarten.filter { k -> spiel.spielregel().farbe(k) == farbe }
//    fun verbleibendeKartenVonAnderen(farbe: SpielFarbe): List<Karte> =
//            verbleibendeKartenVonAnderen.filter { k -> spiel.spielregel().farbe(k) == farbe }
//
//    fun höchsteVerbleibendeKarteVonAnderen(farbe: SpielFarbe): Karte? =
//            spiel.spielregel().höchsteKarte(verbleibendeKartenVonAnderen(farbe)) // highest_remaining_card_of_others
//
//    fun höhereKarteExistiertBeiAnderen(karte: Karte): Boolean =
//            verbleibendeKartenVonAnderen.any{ k -> spiel.spielregel().isZweiteKarteHöher(karte, k) } // higher_card_exists
//
//    fun anzahlHöhereKartenBeiAnderen(karte: Karte): Int =
//            verbleibendeKartenVonAnderen
//                    .filter { k -> spiel.spielregel().isZweiteKarteHöher(karte, k) }
//                    .count() //higher_cards_no_of_others



    /*

 // the information of single players
  OfPlayer const& of_player(Player const& player) const;
  OfPlayer const& of_player(unsigned playerno) const;
  OfPlayer& of_player(Player const& player);
  OfPlayer& of_player(unsigned playerno);

  Hand const& possible_hand(Player const& player) const;
  Hand const& possible_hand(unsigned playerno) const;
  Hand const& estimated_hand(Player const& player) const;
  Hand const& estimated_hand(unsigned playerno) const;

  vector<Hands> estimated_hands_combinations() const;
  int weighting(Hands const& hands) const;

  Game const& game() const;


  public:

  // information

  unsigned forgotten_tricks_no() const;
  unsigned forgotten_cards_no() const;
  unsigned played_cards_no() const;
  unsigned played(Card card) const;
  unsigned played(Card::Color color, Card::Value value) const;
  unsigned played(Card::TColor tcolor) const;
  unsigned played(Card::Value value) const;
  unsigned cardno_to_play(Player const& player) const;
  unsigned remaining_cards_no() const;
  unsigned remaining(Card card) const;
  unsigned remaining_trumps() const;
  unsigned remaining(Card::TColor tcolor) const;
  unsigned remaining(Card::Value value) const;
  vector<Card> remaining_cards_of_others(Card::TColor tcolor) const;
  unsigned remaining_others_cards_no() const;
  unsigned remaining_others(Card card) const;
  unsigned remaining_others(Card::Color color, Card::Value value) const;
  unsigned remaining_trumps_others() const;
  unsigned remaining_others(Card::TColor tcolor) const;
  unsigned remaining_others(Card::Value value) const;

  unsigned known(Card card) const;
  unsigned remaining_unknown(Card card) const;

  CardCounter joined_can_have(OfPlayer const& p1, OfPlayer const& p2) const;

  // the information from the game

  void game_start();
  void trick_open(Trick const& trick);
  void trick_full(Trick const& trick);
  void card_played(HandCard card);
  void card_played(HandCard card, Trick const& trick);
  void announcement_made(Announcement announcement, Player const& player);
  void swines_announced(Player const& player);
  void hyperswines_announced(Player const& player);
  void genscher(Player const& genscher, Player const& partner);

 */




    //-------------------------------------- OfPlayer --------------------------------

/*


  // whether all cards are known
  bool all_known() const;

  // how many cards 'card' the player has played
  unsigned played(Card card) const;
  // how many cards of the tcolor 'tcolor' the player has played
  unsigned played(Card::TColor tcolor) const;
  // how many cards the player must still have on the hand
  unsigned must_have(Card card) const;
  // how many cards the player must still have on the hand
  unsigned must_have(Card::TColor tcolor) const;
  // how many cards the player can still have on the hand
  unsigned can_have(Card card) const;
  // how many cards the player can still have on the hand
  unsigned can_have(Card::TColor tcolor) const;
  // whether the player cannot have the card
  bool cannot_have(Card card) const;
  // whether the player has not the color
  bool does_not_have(Card::TColor tcolor) const;
  // how the unkown number of 'card' (can have - must have)
  unsigned unknown(Card card) const;
  // how the unkown number of 'tcolor' (can have - must have)
  unsigned unknown(Card::TColor tcolor) const;


  // the weighting for the card
  int weighting(Card card, bool modify = true) const;


  unsigned played_cards_no() const;
  unsigned remaining_cards_no() const;
  // the cards the player has played
  Hand played_cards() const;
  // the cards the player must have
  Hand must_have_cards() const;
  // the cards the player cannot have
  Hand cannot_have_cards() const;
  // hand with all possible cards of the player
  Hand possible_hand() const;
  // the estimated hand for the player
  Hand estimated_hand() const;

  // checks, whether the hand is valid
  bool is_valid(Hand const& hand) const;
  int weighting(Hand const& hand) const;

  // the game starts
  void game_start();
  // the player has played 'card'
  void card_played(HandCard card, Trick const& trick);

  // change the weightings according to the played card
  void weight_played_card(HandCard card, Trick const& trick);

  // adds the information that the player must have the card 'no' times
  void add_must_have(Card card, unsigned no = 1);
  // adds the information that the player must have the cards
  void add_must_have(vector<Card> const& cards);
  // adds the information that the player must have the tcolor cards 'no' times
  void add_must_have(Card::TColor tcolor, unsigned no);

  // adds the information that the player can have the card at max 'no' times
  void add_can_have(Card card, unsigned no);
  // adds the information that the player can have the cards at max 'no' times
  void add_can_have(vector<Card> const& cards, unsigned no);
  // adds the information that the player can only have the cards
  void add_can_only_have(vector<Card> const& cards);
  // adds the information that the player can have the tcolor cards at max 'no' times
  void add_can_have(Card::TColor tcolor, unsigned no);
  // adds the information that the player must have the tcolor cards exactly 'no' times
  void add_must_exactly_have(Card::TColor tcolor, unsigned no);

  // adds the information that the player cannot have the card
  void add_cannot_have(Card card);
  // adds the information that the player cannot have the cards
  void add_cannot_have(vector<Card> const& cards);
  // whether the player has not the color
  void add_cannot_have(Card::TColor tcolor);

  // updates the information of the card
  void update_information(Card card);
  // updates the information of the tcolor
  void update_information(Card::TColor tcolor);
  // updates the information of the tcolors
  void update_tcolor_information();
  // updates 'can have' according to 'remaining cards'
  void update_remaining_cards();

  private:
  // check whether the cards the player can have are the ones
  // he has to have
  void check_can_is_must();
  // check whether the cards the player can have are the ones
  // he has to have
  void check_can_is_must(Card::TColor tcolor);
  // check whether the cards the player has to have are the only ones
  // he can have
  void check_must_is_can();
  // checks the data for error
  bool self_check() const;

  private:
  // the cards information
  CardsInformation* cards_information_;
  // the playerno
  unsigned playerno_ = UINT_MAX;

  // number of played cards of the player
  CardCounter played_;
  // number of cards, the player must have
  CardCounter must_have_;
  // number of cards, the player can have
  CardCounter can_have_;

  // number of played tcolors of the player
  TColorCounter tcolor_played_;
  // number of cards, the tcolors must have
  TColorCounter tcolor_must_have_;
  // number of cards, the tcolors can have
  TColorCounter tcolor_can_have_;

  // weighting for the cards for estimations
  // -100
  mutable map<Card, int> cards_weighting_;



 */


}

