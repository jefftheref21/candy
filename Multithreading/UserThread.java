import java.io.*;
import java.net.Socket;
import java.util.*;

public class UserThread extends User implements Runnable {
    // TCP Components

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Thread thread;
    private Thread buyerThread;
    private Thread sellerThread;

    private HashMap<Action, Object> action;
    private CandyManager cm;

    public UserThread(Socket socket, CandyManager cm) {
        try {
            this.socket = socket;
            this.cm = cm;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            thread = new Thread(this);
            thread.start();
        } catch (IOException ie) {
            handleException(ie);
        }
    }

    public void run() {
        try {
            action = (HashMap<Action, Object>) in.readObject();
            for (Map.Entry<Action, Object> entry : action.entrySet()) {
                switch (entry.getKey()) {
                    case LOGIN:
                        handleLogin();

                        break;
                    case BUYER:
                        handleSignup((Buyer) entry.getValue());
                        buyerThread = new Thread(new BuyerThread(socket, cm));
                        buyerThread.start();
                        break;
                    case SELLER:
                        handleSignup((Seller) entry.getValue());
                        sellerThread = new Thread(new SellerThread(socket, cm));
                        sellerThread.start();
                        break;
                }
            }
        } catch (Exception e) {
            handleException(e);
        } finally {
            closeResources();
        }
    }

    private void handleSignup(User user) throws IOException {
        //  reads username and password from the client and validate/signup the user
    }

    private void handleLogin() throws IOException {
        //  reads username and password from the client and validate/login the user
    }

    private void handleException(Exception e) {
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
