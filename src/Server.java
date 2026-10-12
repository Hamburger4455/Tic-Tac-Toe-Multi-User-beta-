import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.Random;

public class Server {
    private final String SECRET = "vgu123";
    private String createSignature(String boardData) {
        return Integer.toString((boardData + SECRET).hashCode());
    }

    public Server() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started");

            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);

                String request = reader.readLine();
                String[] parts = request.split("\\|");

                String boardData = parts[0];
                String signature = parts[1];
                int position = Integer.parseInt(parts[2]);

                // verify signature
                if (!signature.equals("START")) {
                    String expected = createSignature(boardData);

                    if (!expected.equals(signature)) {
                        writer.println("CHEATER");
                        socket.close();
                        continue;
                    }
                }

                Board board = new Board();
                board.stringToBoard(boardData);

                boolean success = board.placeNumber(position, 'X');

                if (!success) {
                    writer.println("INVALID");
                    socket.close();
                    continue;
                }

                // player win
                if (board.checkWin('X')) {
                    String newBoard = board.boardToString();
                    String newSignature = createSignature(newBoard);

                    writer.println(newBoard + "|" + newSignature);
                    writer.println("PLAYER_WIN");
                    socket.close();
                    continue;
                }

                // draw
                if (board.isFull()) {
                    String newBoard = board.boardToString();
                    String newSignature = createSignature(newBoard);

                    writer.println(newBoard + "|" + newSignature);
                    writer.println("DRAW");
                    socket.close();
                    continue;
                }

                Random random = new Random();

                int botRow;
                int botCol;

                while (true) {
                    botRow = random.nextInt(3);
                    botCol = random.nextInt(3);

                    if (board.isEmpty(botRow, botCol)) {
                        break;
                    }
                }

                int botPosition = (botRow * 3) + botCol + 1;

                board.placeNumber(botPosition,'O');

                String newBoard = board.boardToString();
                String newSignature = createSignature(newBoard);

                writer.println(newBoard + "|" + newSignature);

                if (board.checkWin('O')) {
                    writer.println("BOT_WIN");
                }
                else if (board.isFull()) {
                    writer.println("DRAW");
                }
                else {
                    writer.println("CONTINUE");
                }
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}