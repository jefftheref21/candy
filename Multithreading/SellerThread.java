import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.*;

public class SellerThread extends Seller implements Runnable {
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private HashMap<Action, Object> action;
    private StoreManager serverStoreManager;
    private boolean isRunning = true;
    private ArrayList<User> users;
    private CandyManager cm;

    public SellerThread(Socket socket, ObjectInputStream in, ObjectOutputStream out, StoreManager storeManager, ArrayList<User> users, CandyManager cm) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        this.serverStoreManager = storeManager;
        this.users = users;
        this.cm = cm;
    }

    public void run() {
        try {
            while (isRunning) {
                action = (HashMap<Action, Object>) in.readObject();
                for (Map.Entry<Action, Object> entry : action.entrySet()) {
                    switch (entry.getKey()) {
                        case STORE_STATS: {
                            String sellerStats = listSellerStatistics(this);
                            try {
                                out.writeObject(sellerStats);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case SORT_STORE_STATS: {
                            int sortChoice = 1;
                            sortSellerStatistics(sortChoice, this);
                            try {
                                out.writeObject(this.getStoreManager());
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case LIST_SALES: {
                            String salesInfo = listSales(this);
                            try {
                                out.writeObject(salesInfo);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case IMPORT_CSV: {
                            String importFileName = "your_import_file.csv";
                            try {
                                importCSV(importFileName, this);
                                try {
                                    out.writeObject(true);
                                    out.flush();
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                            } catch (IOException e) {
                                try {
                                    out.writeObject(false);
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                                e.printStackTrace();
                            }
                            break;
                        }
                        case EXPORT_CSV: {
                            String exportFileName = "your_export_file.csv";
                            try {
                                exportToCSV(exportFileName, this);
                                try {
                                    out.writeObject(true);
                                    out.flush();
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                            } catch (IOException e) {
                                try {
                                    out.writeObject(false);
                                    out.flush();
                                } catch (IOException ie) {
                                    ie.printStackTrace();
                                }
                            }
                            break;
                        }
                        case TOTAL_PURCHASE_QUANTITIES: {
                            ArrayList<Integer> quantities = new ArrayList<>();
                            //cm.getTotalPurchaseQuantity(this.getStoreManager().getStores(), (Buyer) entry.getValue());
                            try {
                                out.writeObject(quantities);
                                out.flush();
                            } catch (IOException ie) {
                                ie.printStackTrace();
                            }
                            break;
                        }
                        case CREATE_STORE:
                            try {
                                this.getStoreManager().addStore((Store) entry.getValue());
                                out.writeObject(Action.CREATE_STORE_SUCCESSFUL);
                                out.flush();
                            } catch (Exception e) {
                                out.writeObject(Action.CREATE_STORE_UNSUCCESSFUL);
                                out.flush();
                            }
                            break;
                        case EDIT_STORE:
                            try {
                                Store updated = (Store) entry.getValue();
                                boolean found = false;
                                for (int i = 0; i < this.getStoreManager().getStores().size(); i++) {
                                    if (this.getStoreManager().getStores().get(i).getName().equals(updated.getName())) {
                                        this.getStoreManager().getStores().set(i, updated);
                                        out.writeObject(Action.EDIT_STORE_SUCCESSFUL);
                                    }
                                }
                                if (!found) {
                                    out.writeObject(Action.EDIT_STORE_UNSUCCESSFUL);
                                }
                            } catch (Exception e) {
                                out.writeObject(Action.EDIT_STORE_UNSUCCESSFUL);
                            }
                            out.flush();
                            break;
                        case EDIT_CANDY:
                            Candy edCandy = (Candy) entry.getValue();
                            boolean edited = false;
                            for (int i = 0; i < this.getStoreManager().getStores().size(); i++) {
                                Store currStore = this.getStoreManager().getStores().get(i);
                                if (currStore.getName().equals(edCandy.getStore())) {
                                    currStore.editCandy(edCandy.getCandyID(), edCandy, cm);
                                    out.writeObject(Action.EDIT_CANDY_SUCCESSFUL);
                                    out.flush();
                                    edited = true;
                                }
                            }
                            if (!edited) {
                                out.writeObject(Action.EDIT_CANDY_UNSUCCESSFUL);
                                out.flush();
                            }
                        case SEND_SHOPPING_CART:
                            Store store = (Store) entry.getValue();
                            ArrayList<ShoppingCart> buyersShoppingCart = checkShoppingCart(store);
                            out.writeObject(buyersShoppingCart);
                            out.flush();
                    }
                }
            }
        } catch (Exception e) {
            closeResources();
        }
    }

    public ArrayList<ShoppingCart> checkShoppingCart(Store store) {
        ArrayList<Buyer> buyers = new ArrayList<>();
        ArrayList<ShoppingCart> buyersWithShoppingCart = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i) instanceof Buyer) {
                buyers.add((Buyer) users.get(i));
            }
        }

        for (int i = 0; i < buyers.size(); i++) {
            for (int j = 0; j < buyers.get(i).getShoppingCart().getPurchases().size(); j++) {
                if (buyers.get(i).getShoppingCart().getPurchases().get(j).
                        getCandyBought().getStore().equals(store.getName())) {
                    buyersWithShoppingCart.add(buyers.get(i).getShoppingCart());
                }
            }
        }
        return buyersWithShoppingCart;
    }

    public void importCSV(String filename, Seller seller) throws IOException {
        //this.getStoreManager().importCSV(filename, seller);
    }

    public void exportToCSV(String filename, Seller seller) throws IOException {
        File f = new File(filename);
        PrintWriter pw = new PrintWriter(new FileOutputStream(f));
        for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
            Store currStore = seller.getStoreManager().getStores().get(i);
            for (int j = 0; j < currStore.getCandies().size(); j++) {
                Candy currCandy = currStore.getCandies().get(j);
                pw.println(currCandy.toCSV());
            }
        }
        pw.close();
    }

    public String listSellerStatistics(Seller seller) {
        // return cm.listSellerStatistics(seller);
        return "";
    }

    public String listSales(Seller seller) {
        // return cm.listSales(seller);
        return "";
    }

    public void sortSellerStatistics(int choice, Seller seller) {
        // cm.sortSellerStatistics(choice, seller);
    }

    public void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
        isRunning = false;
    }
}
