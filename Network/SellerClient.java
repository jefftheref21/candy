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

    public Action getAction() {
        return action;
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

    public void sendCreateStore(Store store) {
        sendAction(Action.CREATE_STORE, store);
    }

    public void sendEditStore(String storeName, Candy candy, int quantity) {
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

    public void sendToGetCandyID() {
        try {
            out.writeObject(Action.GET_CANDY_ID);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int receiveCandyID() {
        int candyID = -1;
        try {
            candyID = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candyID;
    }

    public void sendAddCandy(Candy candy) {
        sendAction(Action.ADD_CANDY, candy);
    }

    public void sendEditCandy(Candy candy) {
        sendAction(Action.EDIT_CANDY, candy);
    }

    public void sendDeleteCandy(Candy candy) {
        sendAction(Action.DELETE_CANDY, candy);
    }

    public void sendViewSales(Store store) {
        sendAction(Action.STORE_SALES, store);
    }

    public void receiveUpdatedSales() {
        try {
            CandyManager cm = (CandyManager) in.readObject();
            setCandyManager(cm);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendViewStoreStatistics(Store store){
        sendAction(Action.STORE_STATS, store);
    }

    public void receiveStoreStatistics() {
        try {
            CandyManager cm = (CandyManager) in.readObject();
            setCandyManager(cm);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendImportCSV(String fileName) {
        sendAction(Action.IMPORT_CSV, fileName);
    }

    public void sendExportCSV(String fileName) {
        sendAction(Action.EXPORT_CSV, fileName);
    }

    public void sendAction(Action action, Object object) {
        try {
            HashMap<Action, Object> map = new HashMap<>();
            map.put(action, object);
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
