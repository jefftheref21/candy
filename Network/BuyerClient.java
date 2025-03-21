import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuyerClient extends Buyer {
    private Socket socket;
    private Marketplace marketplace;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Action action;

    private CandyManager candyManager;
    private StoreManager storeManager;

    public BuyerClient(Socket socket, ObjectInputStream in, ObjectOutputStream out, Marketplace marketplace) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.marketplace = marketplace;
        candyManager = new CandyManager();
        storeManager = new StoreManager();
    }

    public Action getAction() {
        return action;
    }

    public CandyManager getCandyManager() {
        return candyManager;
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }

    public void setCandyManager(CandyManager candyManager) {
        this.candyManager = candyManager;
    }

    public void sendCandyManager() {
        sendAction(Action.UPDATE_CANDY_MANAGER, this.candyManager);
        System.out.println("Sent candy manager");
    }

    public void receiveCandyManager() {
        try {
            System.out.println(in);
            candyManager.readObject(in);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Sends the candy in which the buyer would like to buy
    //The type refers if the user would like to put in shopping cart or buy instantly
    public void sendCandyProduct(Candy candy, String type, int quantity) {
        Purchase purchase = new Purchase(candy, quantity);
        if (type.equals("BUY_INSTANTLY")) {
            sendAction(Action.BUY_INSTANTLY, purchase);
        } else if (type.equals("ADD_TO_CART")) {
            sendAction(Action.ADD_TO_CART, purchase);
        }
    }

    //Sends to server which user would like to buy everything in their shopping cart
    public void sendBuyShoppingCart() {
        sendAction(Action.BUY_SHOPPING_CART, 0);
    }

    public void sendShoppingCart() {
        sendAction(Action.SHOPPING_CART, 0);
    }

    public void receiveShoppingCart() {
        try {
            this.getShoppingCart().readObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Sends to server the candy in which the user would like to remove from their shopping cart
    public void sendRemoveShoppingCart(Candy candy, int quantity) {
        Purchase purchase = new Purchase(candy, quantity);
        sendAction(Action.REMOVE_FROM_CART, purchase);
    }

    public void sendHistory() {
        sendAction(Action.PURCHASE_HISTORY, 0);
    }

    public void receivePurchaseHistory() {
        try {
            this.getPurchaseHistory().readObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendExportPurchaseHistory(String file) {
        sendAction(Action.EXPORT_HISTORY, file);
    }

    public void receiveSortCandies(int index) {
        this.candyManager.sortProducts(index);
    }

    public void sendStoreManager() {
        sendAction(Action.UPDATE_STORE_MANAGER, 0);
    }

    public void receiveStoreManager() {
        try {
            storeManager.readObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSortProductStats(int choice) {
        sendAction(Action.SORT_STORE_PRODUCTS_STATS, choice);
    }

    public void sendSortBuyerStats(int choice) {
        sendAction(Action.SORT_BUYER_PRODUCTS_STATS, choice);
    }

    public void receiveAction() {
        try {
            action = (Action) in.readUnshared();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAction(Action action, Object object) {
        try {
            HashMap<Action, Object> map = new HashMap<>();
            map.put(action, object);
            System.out.println("hashmap " + map);
            out.writeUnshared(map);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
