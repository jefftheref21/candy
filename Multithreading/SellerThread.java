import java.net.*;
import java.io.*;


public class SellerThread extends Seller implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Action action;
    public SellerThread(Socket socket) {
        try {
            this.socket = socket;

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void run() {
        switch(action) {
            case STORE_STATS:
                break;
            case SORT_STORE_STATS:
                break;
        }
    }
}
