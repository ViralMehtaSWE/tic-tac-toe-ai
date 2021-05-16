package tictactoe;

public class TicTacToeBoard implements TicTacToeBoardInterface {
    private int[] playerPositions;
    private TicTacToeBoardInterface.player currentTurn;
    private boolean isGameOver;
    private TicTacToeBoardInterface.player winner;
    private int moves;

    public TicTacToeBoard() {
        playerPositions = new int[2];
        currentTurn = TicTacToeBoardInterface.player.UNDECIDED;
        isGameOver = false;
        winner = TicTacToeBoardInterface.player.UNDECIDED;
        moves = 0;
    }

    private TicTacToeBoard(int[] playerPositions,
                            TicTacToeBoardInterface.player currentTurn,
                            boolean isGameOver, TicTacToeBoardInterface.player winner,
                            int moves) {
        this.playerPositions = playerPositions.clone();
        this.currentTurn = currentTurn;
        this.isGameOver = isGameOver;
        this.winner = winner;
        this.moves = moves;
    }

    @Override
    public boolean isMoveValid(int row, int col) {
        if (isGameOver) {
            return false;
        }

        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            return false;
        }

        if (currentTurn == TicTacToeBoardInterface.player.UNDECIDED) {
            return false;
        }

        int index = 3*row + col;

        if ((playerPositions[0]&(1<<index)) > 0 || (playerPositions[1]&(1<<index)) > 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean playMove(int row, int col) throws TicTacToeBoardInterface.InputOutOfBoundsException, TicTacToeBoardInterface.PositionAlreadyTakenException, TicTacToeBoardInterface.GameOverException, TicTacToeBoardInterface.PlayerUninitializedException {
        if (isGameOver) {
            throw new TicTacToeBoardInterface.GameOverException();
        }

        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new InputOutOfBoundsException();
        }

        if (currentTurn == TicTacToeBoardInterface.player.UNDECIDED) {
            throw new PlayerUninitializedException();
        }

        int index = 3*row + col;

        if ((playerPositions[0]&(1<<index)) > 0 || (playerPositions[1]&(1<<index)) > 0) {
            throw new TicTacToeBoardInterface.PositionAlreadyTakenException();
        }

        moves++;

        playerPositions[currentTurn.ordinal()] = (playerPositions[currentTurn.ordinal()]|(1<<index));

        if (isWinningMove(index, currentTurn)) {
            isGameOver = true;
            winner = currentTurn;
            currentTurn = getOtherPlayer(currentTurn);
            return true;
        }

        if (!movesLeft()) {
            isGameOver = true;
            winner = TicTacToeBoardInterface.player.DRAW;
            currentTurn = getOtherPlayer(currentTurn);
            return true;
        }

        currentTurn = getOtherPlayer(currentTurn);

        return false;
    }

    @Override
    public boardItem getItem(int row, int col) {
        int index = 3*row + col;
        if ((playerPositions[0]&(1<<index)) > 0) {
            return boardItem.X;
        }

        if ((playerPositions[1]&(1<<index)) > 0) {
            return boardItem.O;
        }

        return boardItem.EMPTY;
    }

    @Override
    public TicTacToeBoardInterface.player getCurrentTurn() {
        return currentTurn;
    }

    @Override
    public boolean isGameOver() {
        return isGameOver;
    }

    @Override
    public boolean isDraw() {
        return (winner == TicTacToeBoardInterface.player.DRAW);
    }

    @Override
    public TicTacToeBoardInterface.player getWinner() {
        return winner;
    }

    @Override
    public TicTacToeBoardInterface getBoard() {
        return new TicTacToeBoard(playerPositions, currentTurn, isGameOver, winner, moves);
    }

    @Override
    public void setPlayer(player player) throws TicTacToeBoardInterface.AttemptToSetPlayerAfterGameStartedException {
        if (moves == 0) {
            this.currentTurn = player;
            return;
        }
        throw new TicTacToeBoardInterface.AttemptToSetPlayerAfterGameStartedException();
    }

    @Override
    public void display(BoardDisplayInterface boardDisplayInterface) {
        boardDisplayInterface.display(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
  
        if (!(o instanceof TicTacToeBoardInterface)) {
            return false;
        }

        TicTacToeBoardInterface board = (TicTacToeBoardInterface) o;
          
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.getItem(i, j) != board.getItem(i, j)) {
                    return false;
                }
            }
        }

        if (this.getCurrentTurn() != board.getCurrentTurn()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return ((playerPositions[0] << 10) + (playerPositions[1] << 1) + this.getCurrentTurn().ordinal());
    }

    private boolean movesLeft() {
        return (moves < 9);
    }

    private boolean isWinningMove(int index, TicTacToeBoardInterface.player player) {
        return (isRowComplete(index, player) || isColComplete(index, player) || isDiagComplete(player));
    }

    private boolean isRowComplete(int index, TicTacToeBoardInterface.player player) {
        int row = index/3;
        return (((7<<(row*3))&playerPositions[player.ordinal()]) == (7<<(row*3)));
    }

    private boolean isColComplete(int index, TicTacToeBoardInterface.player player) {
        int col = index%3;
        return (((73<<col)&playerPositions[player.ordinal()]) == (73<<col));
    }

    private boolean isDiagComplete(TicTacToeBoardInterface.player player) {
        boolean isLeftDiagComplete = ((273&playerPositions[player.ordinal()]) == 273);
        boolean isRightDiagComplete = ((84&playerPositions[player.ordinal()]) == 84);
        return (isLeftDiagComplete || isRightDiagComplete);
    }

    private TicTacToeBoardInterface.player getOtherPlayer(TicTacToeBoardInterface.player player) {
        if (player == TicTacToeBoardInterface.player.PLAYER1) {
            return TicTacToeBoardInterface.player.PLAYER2;
        }
        if (player == TicTacToeBoardInterface.player.PLAYER2) {
            return TicTacToeBoardInterface.player.PLAYER1;
        }
        assert(false);
        return TicTacToeBoardInterface.player.UNDECIDED;
    }
}