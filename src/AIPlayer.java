/*
 * Base class for a computer controlled player.
 */
public abstract class AIPlayer {

    // The player the AI plays as.
    protected char player;

    // Constructor.
    public AIPlayer(char player) {
        this.player = player;
    }

    // Returns the move the player wants to execute on the given board.
    public abstract Board makeMove(Board board);

}
