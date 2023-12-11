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

        initConnection();

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }


    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ObjectInputStream getIn() {
        return in;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendLogin(User user) {
        try {
            HashMap<Action, Object> map = new HashMap<>();
            map.put(Action.LOGIN, user);
            out.writeObject(map);
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