package tictactoe;

public interface TicTacToeBoardInterface {
    public boolean isMoveValid(int row, int col);
    public boolean playMove(int row, int col) throws InputOutOfBoundsException, PositionAlreadyTakenException, GameOverException, PlayerUninitializedException;
    public boardItem getItem(int row, int col);
    public player getCurrentTurn();
    public boolean isGameOver();
    public boolean isDraw();
    public player getWinner();
    public TicTacToeBoardInterface getBoard();
    public void setPlayer(player player) throws AttemptToSetPlayerAfterGameStartedException;
    public void display(BoardDisplayInterface boardDisplayInterface);

    public enum player {
        PLAYER1,
        PLAYER2,
        UNDECIDED,
        DRAW
    }

    public enum boardItem {
        X,
        O,
        EMPTY
    }

    public class InputOutOfBoundsException extends Exception {}
    public class GameOverException extends Exception {}
    public class PositionAlreadyTakenException extends Exception {}
    public class AttemptToSetPlayerAfterGameStartedException extends Exception {}
    public class PlayerUninitializedException extends Exception {}
}
