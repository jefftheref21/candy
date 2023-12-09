import java.net.*;
import java.io.*;
import java.util.*;

public class SellerThread extends Seller implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private HashMap<Action, Object> action;
    private CandyManager candyManager;

    public SellerThread(Socket socket, CandyManager cm) {
        try {
            this.socket = socket;
            this.candyManager = cm;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void run() {
        for (Map.Entry<Action, Object> entry : action.entrySet()) {
            switch (entry.getKey()) {
                case STORE_STATS:
                    String sellerStats = listSellerStatistics(this);
                    try {
                        out.writeObject(sellerStats);
                        out.flush();
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    break;

                case SORT_STORE_STATS:
                    int sortChoice = 1;
                    sortSellerStatistics(sortChoice, this);
                    try {
                        out.writeObject(candyManager);
                        out.flush();
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    break;

                case LIST_SALES:
                    String salesInfo = listSales(this);
                    try {
                        out.writeObject(salesInfo);
                        out.flush();
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    break;

                case IMPORT_CSV:
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

                case EXPORT_CSV:
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
                case TOTAL_PURCHASE_QUANTITIES:
                    ArrayList<Integer> quantities = candyManager.getTotalPurchaseQuantity(this.getStoreManager().getStores(), (Buyer) entry.getValue());
                    try {
                        out.writeObject(quantities);
                        out.flush();
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    break;
            }
        }
        try {
            action = (HashMap<Action, Object>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyInstantly(int id, int quantity, Buyer buyer) {
        candyManager.buyInstantly(id, quantity, buyer);
    }

    public void buyShoppingCart(Buyer buyer) {
        candyManager.buyShoppingCart(buyer);
    }

    public String search(String keyWord) {
        return candyManager.search(keyWord);
    }


    public void importCSV(String filename, Seller seller) throws IOException {
        candyManager.importCSV(filename, seller);
    }

    public void exportToCSV(String filename, Seller seller) throws IOException {
        candyManager.exportToCSV(filename, seller);
    }

    public String listSellerStatistics(Seller seller) {
        return candyManager.listSellerStatistics(seller);
    }

    public String listSales(Seller seller) {
        return candyManager.listSales(seller);
    }

    public void sortSellerStatistics(int choice, Seller seller) {
        candyManager.sortSellerStatistics(choice, seller);
    }

    public int getIndex(int id) {
        return candyManager.getIndex(id);
    }
}
