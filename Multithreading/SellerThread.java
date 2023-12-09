import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class SellerThread extends Seller implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Action action;

    private CandyManager candyManager;

    public SellerThread(Socket socket) {
        try {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Initialize CandyManager
            this.candyManager = new CandyManager();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void run() {
        switch (action) {
            case STORE_STATS:
                String sellerStats = listSellerStatistics(this);
                out.println(sellerStats);
                break;

            case SORT_STORE_STATS:
                int sortChoice = 1;
                sortSellerStatistics(sortChoice, this);
                out.println("Seller statistics sorted.");
                break;

            case LIST_SALES:
                String salesInfo = listSales(this);
                out.println(salesInfo);
                break;

            case IMPORT_CSV:
                String importFileName = "your_import_file.csv";
                try {
                    importCSV(importFileName, this);
                    out.println("CSV import successful.");
                } catch (IOException e) {
                    out.println("Error importing CSV.");
                    e.printStackTrace();
                }
                break;

            case EXPORT_CSV:
                String exportFileName = "your_export_file.csv";
                try {
                    exportToCSV(exportFileName, this);
                    out.println("CSV export successful.");
                } catch (IOException e) {
                    out.println("Error exporting CSV.");
                    e.printStackTrace();
                }
                break;
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
