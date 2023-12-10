import java.io.*;
import java.lang.reflect.Array;
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

    private ArrayList<User> users;

    public boolean isRunning = true;

    public UserThread(Socket socket, CandyManager cm, ArrayList<User> users) {
        try {
            this.socket = socket;
            this.cm = cm;
            this.users = users;

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            thread = new Thread(this);
            thread.start();
        } catch (IOException ie) {
            handleException(ie);
        }
    }

    public void run() {
        try {
            while (isRunning) {
                action = (HashMap<Action, Object>) in.readObject();
                for (Map.Entry<Action, Object> entry : action.entrySet()) {
                    switch (entry.getKey()) {
                        case LOGIN:
                            handleLogin(((User) entry.getValue()).getUsername(), ((User) entry.getValue()).getPassword());
                            break;
                        case BUYER:
                            handleSignUp((Buyer) entry.getValue());
                            break;
                        case SELLER:
                            handleSignUp((Seller) entry.getValue());
                            break;
                    }
                }
            }

            // closeResources();
        } catch (Exception e) {
            handleException(e);
        }
        closeResources();
    }


    private void handleSignUp(User user) throws IOException {
        //  reads username and password from the client and validate/signup the user
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                out.writeObject(Action.INVALID_CREDENTIALS);
                out.flush();
                return;
            }
        }
        users.add(user);
        if (user instanceof Buyer) {
            out.writeObject(Action.VALID_CREDENTIALS_BUYER);
            buyerThread = new Thread(new BuyerThread(socket, cm));
            buyerThread.start();
        } else {
            out.writeObject(Action.VALID_CREDENTIALS_SELLER);
            sellerThread = new Thread(new SellerThread(socket, cm));
            sellerThread.start();
        }
        out.flush();
        isRunning = false;
    }

    private void handleLogin(String username, String password) throws IOException {
        //  reads username and password from the client and validate/login the user
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)){
                if (u instanceof Buyer) {
                    out.writeObject(Action.VALID_CREDENTIALS_BUYER);
                    buyerThread = new Thread(new BuyerThread(socket, cm));
                    buyerThread.start();
                } else {
                    out.writeObject(Action.VALID_CREDENTIALS_SELLER);
                    sellerThread = new Thread(new SellerThread(socket, cm));
                    sellerThread.start();
                }
                out.flush();
                isRunning = false;
                return;
            }
        }
        out.writeObject(Action.INVALID_CREDENTIALS);
        out.flush();
    }

    private void handleException(Exception e) {
        System.err.println("Error: " + e.getMessage());
    }

    private void closeResources() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            handleException(e);
        }
    }
}
