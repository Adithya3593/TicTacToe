import java.util.List;

/*
 * Computer player using the MiniMax algorithm.
 * This class uses minimax with discounting for depth, so that
 * less deep victories have better scores. It applies alpha-beta
 * pruning and depth-cutoff.
 */
public class MinimaxPlayer extends AIPlayer {

    // The numeric value assigned to a win.
    public static final double WIN_VALUE = 100;

    // Max depth before cutoff.
    public static final int MAX_DEPTH = 3;

    public MinimaxPlayer(char player) {
        super(player);
    }

    // Run the MiniMax algorithm and return the best move for the AI player.
    @Override
    public Board makeMove(Board board) {
        // Get possible children and find the best one.
        List<Board> moves = board.getChildren(player);
        Board bestChild = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        for (Board child : moves) {
            double score = minimax(child, 1, false, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            if (score > bestScore) {
                bestScore = score;
                bestChild = child;
            }
        }
        return bestChild;
    }

    // Runs the minimax algorithm, returning the result of the best possible move.
    private double minimax(Board board, int depth, boolean maximizing,
                           double alpha, double beta) {
        // Terminal node checks.
        char r = board.result();
        if (r == Board.DRAW)
            return 0;
        if (r == player)
            return WIN_VALUE - depth;
        if (r == Board.otherPlayer(player))
            return - (WIN_VALUE - depth);

        // Depth cutoff.
        if (depth == MAX_DEPTH) {
            // Calculate heuristic value.
            double h = board.heuristic(player) - board.heuristic(Board.otherPlayer(player));
            return h;
        }

        // Maximizing player.
        if (maximizing) {
            double v = Double.NEGATIVE_INFINITY;
            for (Board child : board.getChildren(player)) {
                v = Math.max(v, minimax(child, depth + 1, false, alpha, beta));
                alpha = Math.max(alpha, v);
                if (beta <= alpha)
                    break;
            }
            return v;
        }  else { // Minimizing player.
            double v  = Double.POSITIVE_INFINITY;
            for (Board child : board.getChildren(Board.otherPlayer(player))) {
                v = Math.min(v, minimax(child, depth + 1, true, alpha, beta));
                beta = Math.min(beta, v);
                if (beta <= alpha)
                    break;
            }
            return v;
        }

    }
}
