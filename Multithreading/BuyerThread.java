import java.io.*;
import java.net.SocketException;
import java.util.*;
import java.net.Socket;

public class BuyerThread extends Buyer implements Runnable {
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private HashMap<Action, Object> action;

    private CandyManager candyManager;
    private ArrayList<Store> stores;

    private boolean isRunning = true;

    public BuyerThread(Socket socket, ObjectInputStream in, ObjectOutputStream out, CandyManager candyManager) {
        this.socket = socket;
        this.out = out;
        this.in = in;

        this.candyManager = candyManager; // Initialize CandyManager

        for (Candy candy : candyManager.candies) {
            stores.add(candy.getStore());
        }
    }

    public void run() {
        try {
            while (isRunning) {
                action = (HashMap<Action, Object>) in.readObject();
                for (Map.Entry<Action, Object> entry : action.entrySet()) {
                    switch (entry.getKey()) {
                        case VIEW_PRODUCT_PAGE: {
                            // get stuff from client
                            String productPage = candyManager.viewProductPage((Integer) entry.getValue());
                            try {
                                out.writeObject(productPage);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case SORT_STORE_STATS: {
                            // get stuff from client
                            candyManager.sortStoreStatistics(stores, (Integer) entry.getValue(), this);
                            try {
                                out.writeObject(candyManager);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case SORT_PRODUCTS: {
                            // get stuff from client
                            candyManager.sortProducts((Integer) entry.getValue());
                            try {
                                out.writeObject(candyManager.candies);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case BUY_SHOPPING_CART: {
                            boolean successful = candyManager.buyShoppingCart(this);
                            try {
                                if (successful) {
                                    out.writeObject(Action.BUY_SUCCESSFUL);
                                } else {
                                    out.writeObject(Action.BUY_QUANTITY_EXCEEDS);
                                }
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case ADD_TO_CART: {
                            Purchase purchaseAddToCart = (Purchase) entry.getValue();
                            try {
                                addToCart(purchaseAddToCart.getCandyBought(), purchaseAddToCart.getQuantityBought());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case REMOVE_FROM_CART: {
                            Purchase purchaseRemoveFromCart = (Purchase) entry.getValue();
                            try {
                                removeFromCart(purchaseRemoveFromCart.getCandyBought(),
                                        purchaseRemoveFromCart.getQuantityBought());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case SHOPPING_CART: {
                            try {
                                out.writeObject(this.getShoppingCart());
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case PURCHASE_HISTORY: {
                            try {
                                out.writeObject(this.getPurchaseHistory());
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case EXPORT_HISTORY: {
                            File file = (File) entry.getValue();
                            try {
                                exportHistory(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
        } catch (SocketException se) {
            closeResources();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void exportHistory(File file) throws IOException {
        try {
            PurchaseHistory ph = this.getPurchaseHistory();
            PrintWriter pw = new PrintWriter(file);

            for (int i = 0; i < ph.getPurchases().size(); i++) {
                pw.println(ph.getPurchases().get(i));
            }

            out.writeObject(Action.EXPORT_HISTORY_SUCCESSFUL);
            out.flush();
        } catch (IOException e) {
            out.writeObject(Action.EXPORT_HISTORY_UNSUCCESSFUL);
            out.flush();
            e.printStackTrace();
        }
    }
    private void search(String searchWord) {

    }
    private void addToCart(Candy candy, int quantity) throws IOException {
        if (quantity > candy.getQuantity()) {
            out.writeObject(Action.BUY_QUANTITY_EXCEEDS);
            out.flush();
            return;
        }

        Purchase purchase = new Purchase(candy, quantity);
        this.addToShoppingCart(purchase);
        out.writeObject(Action.ADD_TO_CART);
        out.flush();
    }
    private void removeFromCart(Candy candy, int quantity) throws IOException {
        Purchase purchase = new Purchase(candy, quantity);
        for (int i = 0; i < this.getShoppingCart().getPurchases().size(); i++) {
            if (purchase.getCandyBought().getCandyID() ==
                    this.getShoppingCart().getPurchases().get(i).getCandyBought().getCandyID()) {
                this.getShoppingCart().getPurchases().remove(i);
                break;
            }
        }
        out.writeObject(Action.REMOVE_FROM_CART);
        out.flush();
    }

    public void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = false;
    }
}
