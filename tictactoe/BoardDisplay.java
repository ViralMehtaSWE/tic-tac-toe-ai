package tictactoe;

public class BoardDisplay implements BoardDisplayInterface {
    @Override
    public void display(TicTacToeBoardInterface ticTacToeBoardInterface) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TicTacToeBoardInterface.boardItem item = ticTacToeBoardInterface.getItem(i, j);
                char letter;
                if (item == TicTacToeBoardInterface.boardItem.X) {
                    letter = 'X';
                } else if (item == TicTacToeBoardInterface.boardItem.O) {
                    letter = 'O';
                } else {
                    letter = ' ';
                }
                if (j < 2) {
                    System.out.print("" + letter + "|");
                } else {
                    System.out.print(letter);
                }
            }
            if (i < 2) {
                System.out.println("\n-----");
            } else {
                System.out.println();
            }
        }
    }
}