import java.util.LinkedList;
import java.util.List;

/*
 * TicTacToe Board State class.
 * Represents a state of the board in a Tic Tac Toe game.
 * It can be used as a state in a search algorithm.
 */
public class Board {

    // Size of the board.
    public static final int SIZE = 5;

    // Symbolic constants for different cell and game states.
    public static final char X = 'X', O = 'O', EMPTY = ' ', DRAW = '/';

    // Board as a matrix of chars.
    private final char[][] board;

    // Row of last move executed on the board.
    public final int lastMoveRow;

    // Col of last move executed on the board.
    public final int lastMoveCol;

    // Board this board was created from.
    public final Board parent;

    // Default constructor, creates an empty board.
    public Board() {
        this.board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                this.board[i][j] = EMPTY;
        this.lastMoveRow = -1;
        this.lastMoveCol = -1;
        this.parent = null;
    }

    // Constructor with input board and move to make.
    // Used by move() and getChildren().
    private Board(Board parent, char player, int moveRow, int moveCol) {
        this.board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                this.board[i][j] = parent.board[i][j];
        this.board[moveRow][moveCol] = player;
        this.parent = parent;
        this.lastMoveRow = moveRow;
        this.lastMoveCol = moveCol;
    }

    // Returns the winner in the position, or DRAW if it's a draw.
    // If the game isn't over, returns EMPTY.
    public char result() {
        // Verify rows and cols.
        for (int i = 0; i < SIZE; i++) {
            int x_rows = 0, o_rows = 0;
            int x_cols = 0, o_cols = 0;
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == X) x_rows++;
                else if (board[i][j] == O) o_rows++;
                if (board[j][i] == X) x_cols++;
                else if (board[j][i] == O) o_cols++;
            }
            if (x_rows == SIZE || x_cols == SIZE) return X;
            else if (o_rows == SIZE || o_cols == SIZE) return O;
        }
        // Verify max and min diagonals.
        int x_maj = 0, o_maj = 0;
        int x_min = 0, o_min = 0;
        for (int i = 0; i < SIZE; i++) {
            if (board[i][i] == X) x_maj++;
            else if (board[i][i] == O) o_maj++;
            if (board[i][board.length - i - 1] == X) x_min++;
            else if (board[i][board.length - i - 1] == O) o_min++;
        }
        if (x_maj == SIZE || x_min == SIZE) return X;
        else if (o_maj == SIZE || o_min == SIZE) return O;
        // Verify that it's not a draw (at least an empty square).
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (this.board[i][j] == EMPTY)
                    return EMPTY;
        return DRAW;
    }

    // Makes a move on the board and returns the resulting child.
    // If the move is illegal, returns null. Row/col are 0-based.
    public Board move(char player, int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE)
            return null;
        if (this.board[row][col] != EMPTY)
            return null;
        return new Board(this, player, row, col);
    }

    // Returns all possible children boards as a list.
    public List<Board> getChildren(char player) {
        // Simple try adding a symbol to each EMPTY square.
        List<Board> children = new LinkedList<>();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == EMPTY) {
                    children.add(new Board(this, player, i, j));
                }
        return children;
    }

    // Returns an heuristic evaluation for the board and player.
    // The heuristic is based on how many different wins each
    // mark is involved. Every square is involved in two wins
    // (row and col), plus some are on diagonals.
    public double heuristic(char player) {
        double h = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == player) {
                    h += 2;
                    // +1 for squares on major or minor diagonals.
                    if (i == j)
                        h += 1;
                    if (i == SIZE - 1 - j)
                        h += 1;
                }
            }
        }
        return h;
    }

    // Returns a representation of the board for printing.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Build horizontal divider string.
        String hDiv = "\n +";
        for (int i = 0; i < SIZE; i++)
            hDiv += "-+";
        hDiv += "\n";
        // Build column headers.
        sb.append(' ');
        for (int i = 1; i <= SIZE; i++)
            sb.append(' ').append(i);
        sb.append(hDiv);
        // Print columns.
        for (int i = 0; i < SIZE; i++) {
            sb.append(i + 1).append('|');
            for (int j = 0; j < SIZE; j++) {
                sb.append(board[i][j]).append('|');
            }
            sb.append(hDiv);
        }
        return sb.toString();
    }

    // Helper to switch from a player to the other.
    public static char otherPlayer(char player) {
        return (player == X ? O : X);
    }
}
