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

    public SellerClient(Socket socket, ObjectInputStream in, ObjectOutputStream out, ControlCenter controlCenter) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.controlCenter = controlCenter;
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

    public void sendEditStore(String storeName, Candy candy) {
        for (Store store : this.getStoreManager().getStores()) {
            if (store.getName().equals(storeName)) {
                store.addCandy(candy, candyManager);
                sendAction(Action.EDIT_STORE, store);
            }
        }
    }
    public void sendShoppingCartRequest() {
        sendAction(Action.VIEW_SHOPPING_CARTS, new Object());
    }

    public void sendCustomerShoppingCarts(Store store) {

        sendAction(Action.SEND_SHOPPING_CART, store);
    }

    public ArrayList<ShoppingCart> receiveCustomerShoppingCarts() {
        try {
            ArrayList<ShoppingCart> customerShoppingCarts = (ArrayList<ShoppingCart>) in.readObject();
            return customerShoppingCarts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToGetCandyID() {
        sendAction(Action.GET_CANDY_ID, new Object());
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

    public ArrayList<Sale> receiveUpdatedSales() {
        try {
            ArrayList<Object> input = (ArrayList<Object>) in.readObject();
            Store store = (Store) input.get(1);
            ArrayList<Sale> sales = (ArrayList<Sale>) input.get(0);
            store.setSales(sales);
            return sales;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
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
