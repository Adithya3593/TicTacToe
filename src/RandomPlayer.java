/*
 * Computer player that makes random moves.
 */
public class RandomPlayer extends AIPlayer {

    // Constructor.
    public RandomPlayer(char player) {
        super(player);
    }

    @Override
    public Board makeMove(Board board) {
        // Simply generate random moves until one is valid.
        Board b = null;
        while (b == null) {
            int i = (int)(Math.random() * Board.SIZE);
            int j = (int)(Math.random() * Board.SIZE);
            b = board.move(player, i, j);
        }
        return b;
    }
}
