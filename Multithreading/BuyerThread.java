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
    private StoreManager storeManager;
    private ArrayList<User> users;

    private boolean isRunning = true;

    public BuyerThread(Socket socket, ObjectInputStream in, ObjectOutputStream out, CandyManager candyManager,
                       StoreManager storeManager, ArrayList<User> users) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.candyManager = candyManager; // Initialize CandyManager
        this.storeManager = storeManager; // Initialize StoreManager
        this.users = users; // Initialize Users
    }

    public void run() {
        try {
            while (isRunning) {
                action = (HashMap<Action, Object>) in.readUnshared();
                for (Map.Entry<Action, Object> entry : action.entrySet()) {
                    switch (entry.getKey()) {
                        case UPDATE_CANDY_MANAGER: {
                            try {
                                candyManager.writeObject(out);
                                out.flush();

                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case BUY_INSTANTLY: {
                            Purchase purchase = (Purchase) entry.getValue();
                            if (purchase.getQuantityBought() < 0) {
                                try {
                                    out.writeUnshared(Action.BUY_QUANTITY_INVALID);
                                    out.flush();
                                    break;
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                            }

                            int index = candyManager.getIndex(purchase.getCandyBought().getCandyID());
                            int totalQuantity = candyManager.candies.get(index).getQuantity();

                            if (totalQuantity >= purchase.getQuantityBought()) {
                                candyManager.buyInstantly(purchase.getCandyBought().getCandyID(),
                                        purchase.getQuantityBought(), this);

                                storeManager.buyInstantly(purchase.getCandyBought(),
                                        purchase.getQuantityBought(), this);
                                try {
                                    out.writeUnshared(Action.BUY_SUCCESSFUL);
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                            } else {
                                try {
                                    out.writeUnshared(Action.BUY_QUANTITY_EXCEEDS);
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                            }
                            break;
                        }
                        case BUY_SHOPPING_CART: {
                            System.out.println("foiejwejf: " + this.getShoppingCart().getPurchases().size());
                            boolean successful = candyManager.buyShoppingCart(this);
                            try {
                                if (successful) {
                                    out.writeUnshared(Action.BUY_SUCCESSFUL);
                                } else {
                                    out.writeUnshared(Action.BUY_QUANTITY_EXCEEDS);
                                }
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case ADD_TO_CART: {
                            Purchase purchaseAddToCart = (Purchase) entry.getValue();
                            addToCart(purchaseAddToCart);
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
                                this.getShoppingCart().writeObject(out);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case PURCHASE_HISTORY: {
                            try {
                                this.getPurchaseHistory().writeObject(out);
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case EXPORT_HISTORY: {
                            String file = (String) entry.getValue();
                            try {
                                File f = new File(file);
                                exportHistory(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case UPDATE_STORE_MANAGER:
                            try {
                                storeManager.writeObject(out);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        case SORT_BUYER_PRODUCTS_STATS: {
                            int choice = (int) entry.getValue();
                            try {
                                out.writeUnshared(storeManager);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case SORT_STORE_PRODUCTS_STATS: {
                            try {
                                out.writeUnshared(storeManager);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        default: {
                            System.out.println("Invalid action");
                            break;
                        }
                    }
                }
            }
            closeResources();
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
            this.getPurchaseHistory().exportHistory(file);

            out.writeUnshared(Action.EXPORT_HISTORY_SUCCESSFUL);
            out.flush();
        } catch (IOException e) {
            out.writeUnshared(Action.EXPORT_HISTORY_UNSUCCESSFUL);
            out.flush();
            e.printStackTrace();
        }
    }

    private void addToCart(Purchase purchase) {
        if (purchase.getQuantityBought() < 0) {
            try {
                out.writeUnshared(Action.ADD_TO_CART_INVALID);
                out.flush();
                return;
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
        int index = candyManager.getIndex(purchase.getCandyBought().getCandyID());
        int totalQuantity = candyManager.candies.get(index).getQuantity();

        if (totalQuantity >= purchase.getQuantityBought()) {
            this.addToShoppingCart(purchase);

            try {
                out.writeUnshared(Action.ADD_TO_CART_SUCCESSFUL);
                out.flush();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        } else {
            try {
                out.writeUnshared(Action.ADD_TO_CART_EXCEEDS);
                out.flush();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
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
        out.writeUnshared(Action.REMOVE_FROM_CART_SUCCESSFUL);
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
