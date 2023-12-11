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

    private StoreManager sm;

    private ArrayList<User> users;

    public boolean isRunning = true;

    public UserThread(Socket socket, CandyManager cm, StoreManager sm, ArrayList<User> users) {
        try {
            this.socket = socket;
            this.cm = cm;
            this.sm = sm;
            this.users = users;

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            thread = new Thread(this);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException ie) {
            handleException(ie);
        }
    }

    public void run() {
        try {
            while (isRunning) {
                action = (HashMap<Action, Object>) in.readUnshared();
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
        } catch (Exception e) {
            e.printStackTrace();
            handleException(e);
        }
    }


    private void handleSignUp(User user) throws IOException {
        //  reads username and password from the client and validate/signup the user
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                out.writeUnshared(Action.INVALID_CREDENTIALS);
                out.flush();
                return;
            }
        }
        users.add(user);
        registerUser();
        if (user instanceof Buyer) {
            out.writeUnshared(Action.VALID_CREDENTIALS_BUYER);
            out.flush();
            buyerThread = new Thread(new BuyerThread(socket, in, out, cm, sm));
            buyerThread.start();
            try {
                buyerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            out.writeUnshared(Action.VALID_CREDENTIALS_SELLER);
            out.flush();
            sellerThread = new Thread(new SellerThread(socket, in, out, sm));
            sellerThread.start();
            try {
                sellerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isRunning = false;
    }

    private void handleLogin(String username, String password) throws IOException {
        //  reads username and password from the client and validate/login the user
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)){
                if (u instanceof Buyer) {
                    out.writeUnshared(Action.VALID_CREDENTIALS_BUYER);
                    out.flush();
                    buyerThread = new Thread(new BuyerThread(socket, in, out, cm, sm));
                    buyerThread.start();
                    try {
                        buyerThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    out.writeUnshared(Action.VALID_CREDENTIALS_SELLER);
                    out.flush();
                    sellerThread = new Thread(new SellerThread(socket, in, out, sm));
                    sellerThread.start();
                    try {
                        sellerThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isRunning = false;
                return;
            }
        }
        out.writeUnshared(Action.INVALID_CREDENTIALS);
        out.flush();
    }

    private void handleException(Exception e) {
        System.err.println("Error: " + e.getMessage());
    }

    private void registerUser() throws IOException {
        File f = new File ("Users.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeUnshared(users);
        oos.flush();
        oos.close();
    }
}
