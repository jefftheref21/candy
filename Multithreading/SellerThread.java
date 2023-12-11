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

    public SellerThread(Socket socket, ObjectInputStream in, ObjectOutputStream out, StoreManager storeManager) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        serverStoreManager = storeManager;
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
                            //candyManager.getTotalPurchaseQuantity(this.getStoreManager().getStores(), (Buyer) entry.getValue());
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
                        case VIEW_SHOPPING_CARTS:

                    }
                }
            }
        } catch (Exception e) {
            closeResources();
        }
    }


    public void importCSV(String filename, Seller seller) throws IOException {
        // storeManager.importCSV(filename, seller);
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
        // return candyManager.listSellerStatistics(seller);
        return "";
    }

    public String listSales(Seller seller) {
        // return candyManager.listSales(seller);
        return "";
    }

    public void sortSellerStatistics(int choice, Seller seller) {
        // candyManager.sortSellerStatistics(choice, seller);
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
