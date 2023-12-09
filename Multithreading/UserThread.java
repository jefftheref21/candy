import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            handleException(ie);
        }
    }

    public void run() {
        try {
            String str = in.readLine();
            action = Action.valueOf(str);

            switch (action) {
                case SIGNUP:
                    handleSignup();
                    break;
                case LOGIN:
                    handleLogin();
                    break;
                case BUYER:
                    buyerThread = new Thread(new BuyerThread(socket));
                    buyerThread.start();
                    break;
                case SELLER:
                    sellerThread = new Thread(new SellerThread(socket));
                    sellerThread.start();
                    break;
                default:
                    out.println("Invalid action.");
                    break;
            }
        } catch (IOException ie) {
            handleException(ie);
        } finally {
            closeResources();
        }
    }

    private void handleSignup() throws IOException {
        //  reads username and password from the client and validate/signup the user
    }

    private void handleLogin() throws IOException {
        //  reads username and password from the client and validate/login the user
    }

    private void handleException(IOException e) {
        System.err.println("Error: " + e.getMessage());
    }

    private void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            handleException(e);
        }
    }
}
