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
                        case EDIT_USER:
                            ArrayList<String> users = (ArrayList<String>) entry.getValue();
                            handleEditUser(users.get(0), users.get(1), users.get(2), users.get(3));
                    }
                }
            }
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
        registerUser();
        if (user instanceof Buyer) {
            out.writeObject(Action.VALID_CREDENTIALS_BUYER);
            out.flush();
            buyerThread = new Thread(new BuyerThread(socket, cm));
            buyerThread.start();
        } else {
            out.writeObject(Action.VALID_CREDENTIALS_SELLER);
            out.flush();
            sellerThread = new Thread(new SellerThread(socket, cm));
            sellerThread.start();
        }
        isRunning = false;
    }

    private void handleLogin(String username, String password) throws IOException {
        //  reads username and password from the client and validate/login the user
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)){
                if (u instanceof Buyer) {
                    out.writeObject(Action.VALID_CREDENTIALS_BUYER);
                    out.flush();
                    buyerThread = new Thread(new BuyerThread(socket, cm));
                    buyerThread.start();
                } else {
                    out.writeObject(Action.VALID_CREDENTIALS_SELLER);
                    out.flush();
                    sellerThread = new Thread(new SellerThread(socket, cm));
                    sellerThread.start();
                }
                isRunning = false;
                return;
            }
        }
        out.writeObject(Action.INVALID_CREDENTIALS);
        out.flush();
    }

    public void handleEditUser(String oldUsername, String oldPassword, String newUsername, String newPassword)
            throws IOException {
        User oldUser = new User(oldUsername, oldPassword);
        User newUser = new User(newUsername, newPassword);
        for (User u : users) {
            if (u.getUsername().equals(newUser.getUsername())) {
                out.writeObject(Action.INVALID_CREDENTIALS);
                out.flush();
                return;
            }
        }

        for (User u : users) {
            if (u.getUsername().equals(oldUser.getUsername()) && u.getPassword().equals(oldUser.getPassword())){
                if (u instanceof Buyer) {
                    out.writeObject(Action.VALID_CREDENTIALS_BUYER);
                } else {
                    out.writeObject(Action.VALID_CREDENTIALS_SELLER);
                }
                System.out.println("Old username: " + oldUsername);
                System.out.println("Old password: " + oldPassword);
                System.out.println("Username: " + newUsername);
                System.out.println("Password: " + newPassword);
                oldUser.setUsername(newUsername);
                oldUser.setPassword(newPassword);

                out.flush();
            }
        }

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
    private void registerUser() throws IOException {
        File f = new File ("Users.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(users);
        oos.flush();
        oos.close();
    }
}
