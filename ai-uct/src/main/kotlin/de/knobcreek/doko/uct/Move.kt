package de.knobcreek.doko.uct

/**
 * @author arno
 */
class Move(val gameType: GameType) {
    enum class QuestionType {
        ImmediateSolo, HasReservation, IsSolo
    }
    enum class MoveType {
        Question, GameType, Announcement, Card
    }

    private var moveType: MoveType? = null;
    private var questionType: QuestionType? = null;
    private var answerRe = false;
    private var announcementType: AnnouncementType = AnnouncementType.None;
    private var card: Card? = null;
    
    fun isQuestionMove() = moveType == MoveType.Question
    fun questionType() = questionType
    fun answer() = answerRe
    fun isGameTypeMove() = moveType == MoveType.GameType
    fun isAnnouncementMove() = moveType == MoveType.Announcement
    fun isReTeam() = answerRe
    fun isCardMove() = moveType == MoveType.Card
    fun card() = card
}
