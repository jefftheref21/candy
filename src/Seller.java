import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Sellers in Candy Marketplace
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */

public class Seller extends User {
    // Might merge StoreManager and Seller, but it isn't necessary
    private StoreManager storeManager = new StoreManager();
    public Seller() {
        super();
    }

    public Seller(String username, String password) {
        super(username, password);
    }
    public Seller(String username, String password, StoreManager storeManager) {
        super(username, password);
        this.storeManager = storeManager;
    }
    public StoreManager getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(StoreManager storeManager) {
        this.storeManager = storeManager;
    }
    public String listStatistics() {
        StringBuilder statisticsInfo = new StringBuilder();
        double totalStoreRevenue = 0.0;

        if (storeManager.getStores().isEmpty()) {
            statisticsInfo.append("You do not own any stores.\n");
        } else {
            for (int i = 0; i < storeManager.getStores().size(); i++) {
                Store store = storeManager.getStores().get(i);
                ArrayList<Sale> sales = store.getSales();
                statisticsInfo.append(storeManager.getStores().get(i).getName()+"\n");
                if (sales.isEmpty()) {
                    statisticsInfo.append("This store currently does not have any sales.\n");
                } else {
                    statisticsInfo.append("Customers:");
                    for (int j = 0; j < sales.size(); j++) {
                        Sale sale = sales.get(j);
                        statisticsInfo.append(sale.getBuyerAccount()+",");
                        totalStoreRevenue += sale.getTotalRevenue();
                    }
                    statisticsInfo.append(store.getName());
                    statisticsInfo.append(" made total revenue of $");
                    statisticsInfo.append(totalStoreRevenue);
                }
            }
        }
        return statisticsInfo.toString();
    }
    public String listSales() {
        StringBuilder salesInfo = new StringBuilder();
        ArrayList<Buyer> accountedBuyers = new ArrayList<>();
        if (storeManager.getStores().isEmpty()) {
            salesInfo.append("You do not own any stores.\n");
        } else {
            for (int i = 0; i < storeManager.getStores().size(); i++) {
                Store store = storeManager.getStores().get(i);
                ArrayList<Sale> sales = store.getSales();
                salesInfo.append(storeManager.getStores().get(i).getName()+"\n");
                if (sales.isEmpty()) {
                    salesInfo.append("This store currently does not have any sales.\n");
                } else {
                    salesInfo.append("Customers\n");
                    for (int j = 0; j < sales.size(); j++) {
                        int purchaseQuantity = 0;
                        Sale sale = sales.get(j);
                        if(accountedBuyers.contains(sale.getBuyerAccount())) {
                            salesInfo.append("");
                        } else {
                            for(int k = 0; k < sale.getBuyerAccount().getPurchaseHistory().getPurchases().size(); k++) {
                                if (sales.contains(sale.getBuyerAccount().getPurchaseHistory().getPurchases().get(k))) {
                                    purchaseQuantity +=  sale.getBuyerAccount().getPurchaseHistory().getPurchases().get(k).getQuantityBought();
                                }
                            }
                            salesInfo.append(sale.getBuyerAccount() + " bought " + purchaseQuantity + "products.\n");
                            accountedBuyers.add(sale.getBuyerAccount());
                        }
                    }
                    salesInfo.append("Candies\n");
                    for (int l =0; l < CandyManager.candies.size(); l++){
                        int candyQuantity = 0;
                        for (int m = 0; m < sales.size(); m++) {
                            Sale sale = sales.get(m);
                            if(sale.getCandyBought().getCandyID() == CandyManager.candies.get(l).getCandyID()) {
                                candyQuantity += sale.getQuantityBought();
                            }
                        }
                        salesInfo.append(CandyManager.candies.get(l).getName()+" has a total of " +
                                candyQuantity + " sales.\n");
                    }
                }
            }
        }
        return salesInfo.toString();
    }
    /* Let test this before we start using it
    public void sortSellerStatistics(int choice) {
        if (choice == 1) { // Sorts Candies by least to greatest Sales
            Collections.sort(CandyManager.candies, new CandyQuantityComparator(true));
        } else if (choice == 2) { //Sorts Candies by greatest to least Sales
            Collections.sort(CandyManager.candies, new CandyQuantityComparator(false));
        } else if (choice == 3) { //Sorts Customers by least to greatest # of purchases
            for (int i = 0; i < storeManager.getStores().size(); i++) {
                Store store = storeManager.getStores().get(i);
                PurchaseQuantityComparator comparator = new PurchaseQuantityComparator(store, true);
                Collections.sort(store.getSales(), comparator);
            }
        } else if (choice == 4) { //Sorts Customers by greatest to least # of purchases
            for (int i = 0; i < storeManager.getStores().size(); i++) {
                Store store = storeManager.getStores().get(i);
                PurchaseQuantityComparator comparator = new PurchaseQuantityComparator(store, false);
                Collections.sort(store.getSales(), comparator);
            }
        } else {
            System.out.println("Invalid choice. Please choose option #1-4.");
        }
    }

     */

    public void importCSV(String filename) throws IOException {
        File f = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            String[] data = line.split(",", 5);
            ArrayList<Store> stores = storeManager.getStores();
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getName().equals(data[1])) {
                    Candy currCandy = new Candy(data[0], stores.get(i), data[1], CandyManager.prodCounter,
                            Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                    stores.get(i).addCandy(currCandy, Integer.parseInt(data[3]), CandyManager.prodCounter);
                    CandyManager.prodCounter++;
                }
            }
            line = br.readLine();
        }
        br.close();
    }
    public void exportToCSV(String filename) throws IOException {
        File f = new File(filename);
        PrintWriter pw = new PrintWriter(new FileOutputStream(f));
        for (int i = 0; i < storeManager.getStores().size(); i++) {
            Store currStore = storeManager.getStores().get(i);
            for (int j = 0; j < currStore.getIndexes().size(); j++) {
                int index = currStore.getIndexes().get(j);
                pw.println(CandyManager.candies.get(index).toCSV());
            }
        }
        pw.close();
    }
    //Fields: Sale<> and StoreManager(Store<>(
    @Override
    public String toString() {
        return super.toString() + "&" + storeManager.toString();
    }
}