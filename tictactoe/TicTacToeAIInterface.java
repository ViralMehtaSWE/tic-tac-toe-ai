package tictactoe;

import java.util.NoSuchElementException;
public interface TicTacToeAIInterface {
    public Move getBestMove(TicTacToeBoardInterface board) throws NoSuchElementException;
}