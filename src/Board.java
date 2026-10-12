public class Board {

    private char[][] board;

    public Board() {
        board = new char[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = '-';
            }
        }
    }

    public void printBoard() {
        System.out.println();

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                System.out.print(board[r][c]);

                if (c < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();

            if (r < 2) {
                System.out.println("---------");
            }
        }
        System.out.println();
    }

    public boolean placeMark(int row, int col, char mark) {
        if (board[row][col] == '-') {
            board[row][col] = mark;
            return true;
        }
        return false;
    }

    public boolean placeNumber(int number, char mark) {

        int row = (number - 1) / 3;
        int col = (number - 1) % 3;

        return placeMark(row, col, mark);
    }

    public boolean isEmpty(int row, int col) {
        return board[row][col] == '-';
    }

    public String boardToString() {
        String result = "";

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                result += board[r][c];
            }
        }
        return result;
    }

    public boolean checkWin(char mark) {
        //row
        for (int r = 0; r < 3; r++) {
            if (board[r][0] == mark &&
                    board[r][1] == mark &&
                    board[r][2] == mark) {
                return true;
            }
        }
        //col
        for (int c = 0; c < 3; c++) {
            if (board[0][c] == mark &&
                    board[1][c] == mark &&
                    board[2][c] == mark) {
                return true;
            }
        }
        //diagonal
        if (board[0][0] == mark &&
                board[1][1] == mark &&
                board[2][2] == mark) {
            return true;
        }

        if (board[0][2] == mark &&
                board[1][1] == mark &&
                board[2][0] == mark) {
            return true;
        }
        return false;
    }

    public boolean isFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public void stringToBoard(String data) {
        int index = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = data.charAt(index);
                index++;
            }
        }
    }
}