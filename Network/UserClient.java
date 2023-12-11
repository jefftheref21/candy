import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class UserClient extends User {
    private Socket socket;
    private final Application application;

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Action action;

    //private CandyManager candyManager;

    public UserClient(Application application) throws IOException {
        this.application = application;

        Candy candy1 = new Candy("Snickers", new Store("Walmart"), "Chocolate bar", 1, 50, 1.00);
        Candy candy2 = new Candy("Twix", new Store("Walmart"), "Chocolate bar",2, 25, 2.00);
        Candy candy3 = new Candy("M&Ms", new Store("Walmart"), "Chocolate bar", 3, 100, 3.00);
        Candy candy4 = new Candy("Kit Kat", new Store("Walmart"), "Chocolate bar", 4, 75, 4.00);
        Candy candy5 = new Candy("Sour Patch Kids", new Store("Walmart"), "Sour candy", 5, 50, 1.00);
        Candy candy6 = new Candy("Sour Skittles", new Store("Walmart"), "Sour candy", 6, 25, 2.00);
        ArrayList<Candy> candies = new ArrayList<>();
        candies.add(candy1);
        candies.add(candy2);
        candies.add(candy3);
        candies.add(candy4);
        candies.add(candy5);
        candies.add(candy6);

        initConnection();

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }


    public Socket getSocket() {
        return socket;
    }

    public Action getAction() {
        return action;
    }

    public void initConnection() {
        String hostname = getHostname();
        // String hostname = "10.186.142.72";
        int portNo = 1234;
        try {
            socket = new Socket(hostname, portNo);

            JOptionPane.showMessageDialog(null, "Server connected!",
                    "Connection Confirmation", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server connection failed!",
                    "Connection Failed", JOptionPane.ERROR_MESSAGE);
            initConnection();
        }
    }

    /**
     * Get the hostname of the server.
     * @return the hostname of the server
     */
    public String getHostname() {
        String hostname = JOptionPane.showInputDialog(null, "Enter Server IP Address or Name:",
                "Server IP Address or Name", JOptionPane.PLAIN_MESSAGE);
        if (hostname == null) {
            hostname = getHostname();
        }
        return hostname;
    }

    public void sendSignUp(User user) {
        try {
            HashMap<Action, Object> map = new HashMap<>();
            if (user instanceof Buyer) {
                map.put(Action.BUYER, user);
            } else {
                map.put(Action.SELLER, user);
            }
            out.writeObject(map);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLogin(User user) {
        try {
            HashMap<Action, Object> map = new HashMap<>();
            map.put(Action.LOGIN, user);
            out.writeObject(map);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEditUser(User user, String newUsername, String newPassword) {
        try {
            User newUser = new User(newUsername, newPassword);
            ArrayList<String> users = new ArrayList<>();
            users.add(user.getUsername());
            users.add(user.getPassword());
            users.add(newUser.getUsername());
            users.add(newUser.getPassword());
            HashMap<Action, Object> map = new HashMap<>();
            map.put(Action.EDIT_USER, users);
            out.writeObject(map);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveAction() {
        try {
            action = (Action) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}