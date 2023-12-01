import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UserThread extends User implements Runnable {
    // TCP Components
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Thread thread;
    private Thread buyerThread;
    private Thread sellerThread;

    private Action action;
    public UserThread(Socket socket) {
        try {
            this.socket = socket;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            thread = new Thread(this);
            thread.start();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void run() {
        try {
            String str = in.readLine();
            action = Action.valueOf(str);
            switch (action) {
                case SIGNUP:
                    break;
                case LOGIN:
                    break;
                case BUYER:
                    buyerThread = new Thread(new BuyerThread(socket));
                    buyerThread.start();
                    break;
                case SELLER:
                    sellerThread = new Thread(new SellerThread(socket));
                    sellerThread.start();
                    break;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
