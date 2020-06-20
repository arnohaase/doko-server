package de.knobcreek.doko.uct

/**
 * @author arno
 */
abstract class GameState {
    

    /*


class GameState {
protected:
    const Options &options;
    Cards cards[4];
    const GameType *game_type;
    int players_team[4]; // saves for each player whether he is part of the re team (1) or the kontra team (0) (uninitialized: -1)
    std::vector<Trick> tricks; // all tricks that have been played
    bool session_instance; // to know if players' teams need to be assigned (or asserted because it is already known or because the session instance of gamestate knows the true card distribution) when somebody announces something or plays a queen of clubs
    int player_played_queen_of_clubs; // initialized: -1, after the first queen of clubs was played, stores the player who played it. needed to find out if the same plays the second queen of clubs too, in which case all other players must be kontra players (regular game only)

    // fields related to announcements
    int players_latest_moment_for_announcement[4]; // number of cards a player must hold to allow for announcements
    announcement_t announcements[2]; // team anouncements (only the highest one)
    bool first_announcement_in_time[2]; // true iff the first announcement was a 'real' announcement and not a reply to an announcement from the other team
    int card_number_for_latest_possible_reply[2]; // stores how many cards each team has to have making a reply to an announcement
    int players_known_team[4]; // only difference to players_team is that for a regular game, this gets initialized with -1 and updated later to 0 or 1 as soon as all players participating in the game have the information about that player's team
    bool teams_are_known; // from the moment on where both teams and their players are identified, this variable is set to true

    // fields related to the special cases marriage and solo
    int solo_or_marriage_player; // = -1 if no solo or marriage play
    bool compulsory_solo; // compulsory_solo = true implies solo_or_marriage_player != -1
    int number_of_clarification_trick; // for adapting announcements rules in the case of marriage

    // during the game: game type determination, announcements, card moves
    int corrected_number_of_cards(int number) const; // adapts the number of cards needed for announcements when game type is marriage
    /* announcement_possible does NOT check for players which team they belong to in order to avoid not asking a player for an announcement when the other players cannot know that this player is not allowed for any announcement, thus telling them informations they normally would not have. (player's) cards_count was introduced as a parameter for the UctPlayer-usage of this class. */
    bool announcement_possible(int player, int cards_count) const;
    bool announcement_legal(int player, announcement_t announcement, int cards_count) const;
    int get_number_of_cards_for_announcement(announcement_t announcement) const;
    announcement_t get_announcement_for_number_of_cards(int number_of_cards) const; // returns the minimum announcement required in order for the given number of cards to be a correct limit for announcements
    void check_teams_are_known(); // this method tests if (with a given announcement) now the teams are uniquely determined. if yes, it also updates the latest moments for announcing for each player if necessary
    void set_announcement(int player, announcement_t announcement, bool is_re_player, int cards_count);
    void set_latest_moment_for_announcements(); // only called in case of marriage after the clearification trick
    void get_legal_announcements_for_player(int player, std::vector<Move> &legal_announcements) const;
    int update(int player, Card card); // set the card played by splayer and returns the player who is next
    void assign_solo_player_to_re_team(int player);

    // after game end: points calculation
    /* computes all points and special points made during the game (iterates over all tricks). also computes whether one team was played black or not. the argument count_re_players serves to determine whether special points must be computed or not. */
    void get_points_and_special_points(int points[4], int &special_points_for_re, bool black[2], int count_re_players) const;
    bool has_team_lost(bool re_team, int points, const bool black[2]) const;
    int get_team_score_points(bool re_team, int points, const bool black[2]) const; // computes score points that will always be distributed, independent of who (or if one team) won. therefore called for both teams.
    int get_winning_team_score_points(bool re_team, int points, const bool black[2]) const; // computes score points that will be granted for the winning team only
public:
    GameState(const Options &options, bool session_instance);
    bool game_finished() const; // changed to public for Uct
    void get_score_points(int *players_score_points, int *players_points = 0, int *team_points = 0) const; // computes the final score points made by each player. players_points needed by Uct to not only get the score points, but also the points players made during the game. furthermore, if player_points = 0 (the case when Session uses GameState), the score points etc are printed to standard out
    bool is_compulsory_solo() const {
        return compulsory_solo;
    }
    int get_compulsory_solo_player() const { // needed by Session to store which player already played his compulsory solo
        assert(is_compulsory_solo() && solo_or_marriage_player != -1);
        return solo_or_marriage_player;
    }
    int get_players_team(int player) const { // needed by Uct to compute team points
        assert(players_team[player] != -1);
        assert(players_known_team[player] != -1);
        return players_team[player];
    }
};


     */
}