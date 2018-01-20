import java.util.List;

/*
 * Computer player able to choose one move wins.
 */
public class ImmediateWinPlayer extends AIPlayer {

    public ImmediateWinPlayer(char player) {
        super(player);
    }

    @Override
    public Board makeMove(Board board) {
        // Get all possible children and check if there is a win.
        List<Board> possibleMoves = board.getChildren(player);
        for (Board b : possibleMoves)
            if (b.result() == player)
                return b;
        // If not, just return a random one.
        return possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    }
}
