import java.io.*;
import java.net.Socket;

public class Client {
    private BufferedReader keyboard;
    private Board board;
    private String signature;

    public Client() {
        try {
            board = new Board();
            signature = "START";
            keyboard = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                board.printBoard();
                System.out.print("Choose pos (1-9): ");

                int position = Integer.parseInt(keyboard.readLine());

                Socket socket = new Socket("localhost",1234);

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);

                writer.println(board.boardToString() + "|" + signature + "|" + position);

                String boardData = reader.readLine();

                if (boardData.equals("INVALID")) {
                    System.out.println("Invalid move");
                    socket.close();
                    continue;
                }

                board = new Board();
                board.stringToBoard(boardData);
                board.printBoard();

                String result = reader.readLine();

                if (result.equals("PLAYER_WIN")) {
                    System.out.println("You won");
                    socket.close();
                    break;
                }

                if (result.equals("BOT_WIN")) {
                    System.out.println("Bot won");
                    socket.close();
                    break;
                }

                if (result.equals("DRAW")) {
                    System.out.println("Draw");
                    socket.close();
                    break;
                }
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}