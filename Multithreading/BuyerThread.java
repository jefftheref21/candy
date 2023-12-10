import java.io.*;
import java.util.*;
import java.net.Socket;

public class BuyerThread extends Buyer implements Runnable {
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private HashMap<Action, Object> action;

    private CandyManager candyManager;
    private ArrayList<Store> stores;



    public BuyerThread(Socket socket, CandyManager candyManager) {
        try {
            this.socket = socket;
            this.candyManager = candyManager; // Initialize CandyManager

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            for (Candy candy : candyManager.candies) {
                stores.add(candy.getStore());
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            for (Map.Entry<Action, Object> entry : action.entrySet()) {
                switch (entry.getKey()) {
                    case VIEW_PRODUCT_PAGE:
                        // get stuff from client
                        String productPage = candyManager.viewProductPage((Integer) entry.getValue());
                        try {
                            out.writeObject(productPage);
                            out.flush();
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                        break;

                    case SORT_STORE_STATS:
                        // get stuff from client
                        candyManager.sortStoreStatistics(stores, (Integer) entry.getValue(), this);
                        try {
                            out.writeObject(candyManager);
                            out.flush();
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                        break;

                    case SORT_PRODUCTS:
                        // get stuff from client
                        candyManager.sortProducts((Integer) entry.getValue());
                        try {
                            out.writeObject(candyManager.candies);
                            out.flush();
                        } catch (IOException ie) {
                            ie.printStackTrace();
                        }
                        break;
                    case BUY_SHOPPING_CART:
                        candyManager.buyShoppingCart(this);
                }
            }
            try {
                action = (HashMap<Action, Object>) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
