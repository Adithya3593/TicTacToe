import java.util.Scanner;

/*
 * Main class. Runs the interactive game.
 */
public class TicTacToe {

    private static Scanner input;

    // Main method, runs the program.
    public static void main(String[] args) {
        input = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe.");
        int choice = 0;
        do {
            System.out.print("Choose level of difficulty (1. easy 2. medium 3. hard) >> ");
            int diff = input.nextInt();
            System.out.print("Choose symbol to play (1. X 2. O) >> ");
            int sym = input.nextInt();
            System.out.println();
            runGame(diff, sym);
            System.out.println();
            System.out.print("Do you want to keep playing? (1. yes 2. no) >> ");
            choice = input.nextInt();
        } while (choice != 2);

        input.close();
    }

    // Runs a single game with given difficulty level and player symbol.
    private static void runGame(int diff, int sym) {
        // Setup initial state.
        Board board = new Board();
        char next = Board.X;
        char humanPlayer = (sym == 1 ? Board.X : Board.O);
        char aiPlayer = Board.otherPlayer(humanPlayer);
        AIPlayer ai;
        switch (diff) {
            case 1:  ai = new RandomPlayer(aiPlayer); break;
            case 2:  ai = new ImmediateWinPlayer(aiPlayer); break;
            default: ai =  new MinimaxPlayer(aiPlayer);
        }

        // Show empty board.
        System.out.println(board);

        // Run until the game is over.
        while (board.result() == Board.EMPTY) {
            if (next == humanPlayer) {
                while (true) { // trap until valid row/col is entered.
                    System.out.print("What is your move? (row col) >> ");
                    int row = input.nextInt(), col = input.nextInt();
                    Board b = board.move(humanPlayer, row - 1, col - 1);
                    if (b == null) {
                        System.out.println("Sorry, invalid move. Try again.");
                    } else {
                        board = b;
                        break;
                    }
                }

            } else {
                board = ai.makeMove(board);
                System.out.println("My move is row " + (board.lastMoveRow + 1)
                    + " column " + (board.lastMoveCol + 1) + ".");
            }
            System.out.println(board);
            next = Board.otherPlayer(next);
        }

        // Show results.
        System.out.println();
        char result = board.result();
        if (result == Board.DRAW)
            System.out.println("It's draw!");
        else if (result == humanPlayer)
            System.out.println("You win!");
        else
            System.out.println("You lose.");
    }
}
