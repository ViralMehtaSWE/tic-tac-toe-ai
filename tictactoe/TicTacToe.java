package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        TicTacToeBoardInterface.player player = choosePlayer(in);
        
        TicTacToeBoardInterface board = new TicTacToeBoard();
        board.setPlayer(TicTacToeBoardInterface.player.PLAYER1);

        if (player == TicTacToeBoardInterface.player.PLAYER2) {
            Move nextMove = getRandomMove();
            board.playMove(nextMove.getRow(), nextMove.getCol());
        }

        TicTacToeAIInterface ticTacToeAIInterface = new TicTacToeAI();

        BoardDisplayInterface ticTacToeBoardDisplayInterface = new BoardDisplay();

        while (!board.isGameOver()) {
            board.display(ticTacToeBoardDisplayInterface);
            System.out.println("Enter (row, col) for your next move:");
            int row = in.nextInt();
            int col = in.nextInt();
            board.playMove(row, col);
            board.display(ticTacToeBoardDisplayInterface);

            if (board.isGameOver()) {
                System.out.println();
                break;
            }

            Move bestMove = ticTacToeAIInterface.getBestMove(board);
            board.playMove(bestMove.getRow(), bestMove.getCol());
            System.out.println("AI Move: " + bestMove);
        }

        board.display(ticTacToeBoardDisplayInterface);
        System.out.println();
        if (board.isDraw()) {
            System.out.println("Game draw!");
        } else {
            System.out.println("Winner is: " + board.getWinner());
        }
    }

    private static Move getRandomMove() {
        int randomNum = (new Random()).nextInt(9);
        return new Move(randomNum/3, randomNum%3);
    }

    private static TicTacToeBoardInterface.player choosePlayer(Scanner in) {
        while(true) {
            System.out.println("Select Player: 1 or 2 ?");
            try {
                int playerId = in.nextInt();
                if (playerId == 1) {
                    return TicTacToeBoardInterface.player.PLAYER1;
                } else if (playerId == 2) {
                    return TicTacToeBoardInterface.player.PLAYER2;
                }
                System.out.println("Invalid input! Please try again!");
            } catch(Exception e) {
                System.out.println("Invalid input! Please try again!");
            }
        }
    }
}