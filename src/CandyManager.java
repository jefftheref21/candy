import java.util.*;
import java.io.*;

/**
 * Manager that handles all of the candies
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class CandyManager implements Serializable {
    public ArrayList<Candy> candies;
    public int prodCounter = 0;
    public static transient Object obj;
    public CandyManager() {
        this.candies = new ArrayList<>();
        this.prodCounter = 0;
        obj = new Object();
    }
    public CandyManager(ArrayList<Candy> candies, int prodCounter) {
        this.candies = candies;
        this.prodCounter = prodCounter;
        obj = new Object();
    }
    public ArrayList<Candy> getCandies() {
        return candies;
    }

    public void setCandies(ArrayList<Candy> candies) {
        this.candies = candies;
    }
    public void setProdCounter(int prodCounter) {
        this.prodCounter = prodCounter;
    }
    // Buyer methods
    public String viewProductPage(int productID) {
        String productPage = "";
        String productName = "";
        String productDescription = "";
        int productQuantity = 0;
        for (int i = 0; i < this.candies.size(); i++) {
            if (candies.get(i).getCandyID() == productID) {
                productName = candies.get(i).getName();
                productDescription = candies.get(i).getDescription();
                productQuantity = candies.get(i).getQuantity();
            }
        }
        productPage = "About " + productName + ":\n";
        productPage += "Product Quantity: " + productQuantity + "\n";
        productPage += "Product Description: " + productDescription + "\n";
        return productPage;
    }
    public ArrayList<Store> sortStoreStatistics(ArrayList<Store> stores, int choice, Buyer buyer) {
        for (int i = 1; i < stores.size(); i++) {
            int temp = i;
            int j = i - 1;
            switch (choice) {
                case 1 -> { // Least to greatest by number of products sold
                    while (j >= 0 && stores.get(temp).getNumberOfProductsSold() <
                            stores.get(j).getNumberOfProductsSold()) {
                        Collections.swap(stores, temp, j);
                        // System.out.println(stores.get(temp).getNumberOfProductsSold() + " " + stores.get(j).getNumberOfProductsSold());

                        temp = j;
                        j--;
                    }
                }
                case 2 -> { // Greatest to least by number of products sold
                    while (j >= 0 && stores.get(temp).getNumberOfProductsSold() >
                            stores.get(j).getNumberOfProductsSold()) {
                        Collections.swap(stores, temp, j);

                        temp = j;
                        j--;
                    }
                }
                case 3 -> { // Least to greatest by number of products bought by customer
                    ArrayList<Integer> numOfProductsBought = new ArrayList<>();
                    for (Store store: stores) {
                        ArrayList<Sale> buyerSales = store.getSalesByBuyer(buyer);
                        for (Sale sale : buyerSales) {
                            numOfProductsBought.add(sale.getQuantityBought());
                        }
                    }
                    while (j >= 0 && numOfProductsBought.get(temp) < numOfProductsBought.get(j)) { // I, as in Jeffrey, will fix later
                        Collections.swap(stores, temp, j);
                        Collections.swap(numOfProductsBought, temp, j);

                        temp = j;
                        j--;
                    }
                }
                case 4 -> { // Greatest to least by number of products bought by customer
                    ArrayList<Integer> numOfProductsBought = new ArrayList<>();
                    for (Store store: stores) {
                        ArrayList<Sale> buyerSales = store.getSalesByBuyer(buyer);
                        for (Sale sale : buyerSales) {
                            numOfProductsBought.add(sale.getQuantityBought());
                        }
                    }
                    while (j >= 0 && numOfProductsBought.get(temp) > numOfProductsBought.get(j)) { // I, as in Jeffrey, will fix later;
                        Collections.swap(stores, temp, j);

                        temp = j;
                        j--;
                    }
                }
            }
        }
        return stores;
    }
    public ArrayList<Integer> getTotalPurchaseQuantity(ArrayList<Store> stores, Buyer buyer){
        ArrayList<Integer> purchaseQuantities = new ArrayList<>();
        for (int j = 0; j < stores.size(); j++) {
            Store store = stores.get(j);
            int purchaseQuantity = 0;
            for (int k = 0; k < buyer.getPurchaseHistory().getPurchases().size(); k++) {
                purchaseQuantity += store.getSalesByBuyer(buyer).get(k).getQuantityBought();
            }
            purchaseQuantities.add(purchaseQuantity);
        }
        return purchaseQuantities;
    }
    public void sortProducts(int choice) {
        // TODO: Sort for price or quantity
        for (int i = 1; i < this.candies.size(); i++) {
            int temp = i;
            int j = i - 1;

            switch (choice) {
                case 0 -> { // Least to greatest by price
                    while (j >= 0 && this.candies.get(temp).getPrice() <
                            this.candies.get(j).getPrice()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
                case 1 -> { // Greatest to least by price
                    while (j >= 0 && this.candies.get(temp).getPrice() >
                            this.candies.get(j).getPrice()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
                case 2 -> { // Least to greatest by quantity
                    while (j >= 0 && this.candies.get(temp).getQuantity() < this.candies.get(j).getQuantity()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
                case 3 -> { // Greatest to least by quantity
                    while (j >= 0 && this.candies.get(temp).getQuantity() > this.candies.get(j).getQuantity()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
            }
        }
    }
    public ArrayList<Store> viewStoreStatistics(ArrayList<Store> stores, Buyer buyer, int choice) {
        // 0 = sort by quantity sold. 1 = sort by num of products sold to buyer
            for (int i = 0; i < stores.size(); i++) {
                Store currStore = stores.get(i);
                for (int j = i + 1; j < stores.size(); j++) {
                    switch (choice) {
                        case 0:
                            if (stores.get(j).getNumberOfProductsSold() > currStore.getNumberOfProductsSold()) {
                                stores.set(i, stores.get(j));
                            }
                            break;
                        case 1:
                            int currStoreSales = 0;
                            int loopingStoreSales = 0;
                            for (Sale sale : currStore.getSalesByBuyer(buyer)) {
                                currStoreSales += sale.getQuantityBought();
                            }
                            for (Sale sale : stores.get(j).getSalesByBuyer(buyer)) {
                                loopingStoreSales += sale.getQuantityBought();
                            }
                            if (loopingStoreSales > currStoreSales) {
                                stores.set(i, stores.get(j));
                            }
                            break;
                        }
                    }
                }
        return stores;
    }
    public void buyInstantly(int id, int quantity, Buyer buyer) {
        synchronized (obj) {
            int index = getIndex(id);
            int totalQuantity = candies.get(index).getQuantity();
            System.out.println(index + " " + totalQuantity);
            candies.get(index).setQuantity(totalQuantity - quantity);
            System.out.println(candies.get(index).getQuantity());

            buyer.getPurchaseHistory().addPurchase(new Purchase(candies.get(index), quantity));
        }
    }
    public boolean buyShoppingCart(Buyer buyer) {
        synchronized (obj) {
            for (int i = 0; i < buyer.getShoppingCart().getPurchases().size(); i++) {
                Purchase currPurchase = buyer.getShoppingCart().getPurchases().get(i);
                int index = getIndex(currPurchase.getCandyBought().getCandyID());
                int totalQuantity = candies.get(index).getQuantity();

                if (totalQuantity < currPurchase.getQuantityBought()) {
                    return false;
                }
            }
            for (int j = 0; j < buyer.getShoppingCart().getPurchases().size(); j++) {
                Purchase currPurchase = buyer.getShoppingCart().getPurchases().get(j);
                buyInstantly(currPurchase.getCandyBought().getCandyID(),
                        currPurchase.getQuantityBought(), buyer);
                buyer.getPurchaseHistory().addPurchase(currPurchase);
            }
            return true;
        }
    }
    public ArrayList<Candy> search(String keyWord) {
        ArrayList<Candy> result = new ArrayList<>();
        for (int i = 0; i < candies.size(); i++) {
            String name = candies.get(i).getName();
            String store = candies.get(i).getStore();
            String description = candies.get(i).getDescription();

            if (name.contains(keyWord) || store.contains(keyWord) || description.contains(keyWord)) {
                result.add(candies.get(i));
            }
        }
        System.out.println("please work oiwjfwwejoifjwe" + result.size());

        return result;
    }
    // Seller methods
    public String listSellerStatistics(Seller seller) {
        StringBuilder statisticsInfo = new StringBuilder();
        double totalStoreRevenue = 0.0;

        if (seller.getStoreManager().getStores().isEmpty()) {
            statisticsInfo.append("You do not own any stores.\n");
        } else {
            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                Store store = seller.getStoreManager().getStores().get(i);
                ArrayList<Sale> sales = store.getSales();
                statisticsInfo.append(seller.getStoreManager().getStores().get(i).getName()+"\n");
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
    public String listSales(Seller seller) {
        StringBuilder salesInfo = new StringBuilder();
        ArrayList<Buyer> accountedBuyers = new ArrayList<>();
        if (seller.getStoreManager().getStores().isEmpty()) {
            salesInfo.append("You do not own any stores.\n");
        } else {
            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                Store store = seller.getStoreManager().getStores().get(i);
                ArrayList<Sale> sales = store.getSales();
                salesInfo.append(seller.getStoreManager().getStores().get(i).getName()+"\n");
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
                    for (int l =0; l < this.candies.size(); l++){
                        int candyQuantity = 0;
                        for (int m = 0; m < sales.size(); m++) {
                            Sale sale = sales.get(m);
                            if(sale.getCandyBought().getCandyID() == this.candies.get(l).getCandyID()) {
                                candyQuantity += sale.getQuantityBought();
                            }
                        }
                        salesInfo.append(this.candies.get(l).getName()+" has a total of " +
                                candyQuantity + " sales.\n");
                    }
                }
            }
        }
        return salesInfo.toString();
    }
    public void sortSellerStatistics(int choice, Seller seller) {
        if (choice == 1) { // Sorts Candies by least to greatest Sales
            Collections.sort(this.candies, new CandyQuantityComparator(true));
        } else if (choice == 2) { //Sorts Candies by greatest to least Sales
            Collections.sort(this.candies, new CandyQuantityComparator(false));
        } else if (choice == 3) { //Sorts Customers by least to greatest # of purchases
            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                Store store = seller.getStoreManager().getStores().get(i);
                PurchaseQuantityComparator comparator = new PurchaseQuantityComparator(store, true);
                Collections.sort(store.getSales(), comparator);
            }
        } else if (choice == 4) { //Sorts Customers by greatest to least # of purchases
            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                Store store = seller.getStoreManager().getStores().get(i);
                PurchaseQuantityComparator comparator = new PurchaseQuantityComparator(store, false);
                Collections.sort(store.getSales(), comparator);
            }
        } else {
            System.out.println("Invalid choice. Please choose option #1-4.");
        }
    }
    public void importCSV(String filename, Seller seller) throws IOException {
        File f = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            String[] data = line.split(",", 5);
            ArrayList<Store> stores = seller.getStoreManager().getStores();
            for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getName().equals(data[1])) {
                    Candy currCandy = new Candy(data[0], stores.get(i).getName(), data[1], this.prodCounter,
                            Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                    stores.get(i).addCandy(currCandy, this);
                    this.prodCounter++;
                }
            }
            line = br.readLine();
        }
        br.close();
    }
    // User methods

    public int getIndex(int id) {
        for (int i = 0; i < candies.size(); i++) {
            if (candies.get(i).getCandyID() == id) {
                return i;
            }
        }
        return -1;
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(candies.size());

        for (Candy candy : candies) {
            out.writeUnshared(candy);
        }
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int candiesSize = in.readInt();

        candies = new ArrayList<>(candiesSize);

        for (int i = 0; i < candiesSize; i++) {
            Candy candy = (Candy) in.readUnshared();
            candies.add(candy);
        }
    }
}