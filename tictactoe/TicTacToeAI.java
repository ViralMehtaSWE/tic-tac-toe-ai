package tictactoe;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class TicTacToeAI implements TicTacToeAIInterface {

    private Map<TicTacToeBoardInterface, Integer> cache;

    public TicTacToeAI() {
        this.cache = new HashMap<>();
    }

    @Override
    public Move getBestMove(TicTacToeBoardInterface board) throws NoSuchElementException {
        List<Integer> nextStateValues = getNextStateValues(board);
        TicTacToeBoardInterface.player player = board.getCurrentTurn();
        int index = -1;
        if (player == TicTacToeBoardInterface.player.PLAYER1) {
            index = argMax(nextStateValues);
        } else {
            index = argMin(nextStateValues);
        }
        return (new Move(index/3, index%3));
    }

    private List<Integer> getNextStateValues(TicTacToeBoardInterface board) {
        List<Integer> nextStateValues = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeBoardInterface nextState = board.getBoard();
                try {
                    nextState.playMove(i, j);
                } catch (Exception e) {
                    nextStateValues.add(null);
                    continue;
                }
                nextStateValues.add(getStateValue(nextState));
            }
        }
        return nextStateValues;
    }

    private int getStateValue(TicTacToeBoardInterface board) {
        if (board.isGameOver()) {
            if (board.isDraw()) {
                return 0;
            }

            return ((board.getWinner() == TicTacToeBoardInterface.player.PLAYER1) ? 1: -1);
        }

        if (cache.containsKey(board)) {
            return cache.get(board);
        }

        List<Integer> nextStateValues = getNextStateValues(board);
        nextStateValues.removeIf(value -> (value == null));
        TicTacToeBoardInterface.player player = board.getCurrentTurn();
        int value;
        if (player == TicTacToeBoardInterface.player.PLAYER1) {
            value = Collections.max(nextStateValues);
        } else {
            value = Collections.min(nextStateValues);
        }
        cache.put(board, value);
        return value;
    }

    private int argMax(List<Integer> values) {
        if (values == null) {
            throw new NoSuchElementException();
        }
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == null) {
                continue;
            }
            if (max <= values.get(i)) {
                max = values.get(i);
                index = i;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException();
        }
        return index;
    }

    private int argMin(List<Integer> values) {
        if (values == null) {
            throw new NoSuchElementException();
        }
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == null) {
                continue;
            }
            if (min >= values.get(i)) {
                min = values.get(i);
                index = i;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException();
        }
        return index;
    }
}
