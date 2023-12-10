import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SellerClient extends Seller {
    private Socket socket;
    private ControlCenter controlCenter;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Action action;

    private CandyManager candyManager;

    public SellerClient(Socket socket, ControlCenter controlCenter) throws IOException {
        this.socket = socket;
        this.controlCenter = controlCenter;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCandyManager(CandyManager candyManager) {
        this.candyManager = candyManager;
    }

    public void sendSellerDecision(String type) {
        try {
            out.writeUTF(type);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createStore(String storeName, Candy candy, int quantity) {
        try {
            HashMap hashmap = new HashMap<String, Seller>();
            hashmap.put(Action.CREATE_STORE, this);
            out.writeObject(hashmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editStore(String storeName, Candy candy, int quantity) {
        try {
            out.writeObject(candy);
            out.flush();
            out.writeUTF(storeName);
            out.flush();
            out.write(quantity);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewStoreStatistics(){
        try {
            HashMap hashmap = new HashMap<String, Seller>();
            hashmap.put(Action.STORE_SALES, this);
            out.writeObject(hashmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
