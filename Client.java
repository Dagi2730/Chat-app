import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Client(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        new ReadThread().start();
        new WriteThread().start();
    }

    private class ReadThread extends Thread {
        public void run() {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class WriteThread extends Thread {
        public void run() {
            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                String message;
                while ((message = keyboardInput.readLine()) != null) {
                    output.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Client("127.0.0.1", 12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}