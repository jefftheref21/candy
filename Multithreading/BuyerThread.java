import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BuyerThread extends Buyer implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Action action;

    public BuyerThread(Socket socket) {
        try {
            this.socket = socket;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            switch(action) {
                case LOGIN:
                    break;
                case SIGNUP:
                    break;
            }
        }
    }
}