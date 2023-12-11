import java.util.*;
import java.io.*;

/**
 * Manager that handles all of the candies
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class CandyManager {
    // class doesn't need a constructor, because we're just interested in the static candies ArrayList.
    public ArrayList<Candy> candies = new ArrayList<>();
    public int prodCounter = 0;
    public static Object obj = new Object();
    public CandyManager() {
        this.candies = new ArrayList<>();
        this.prodCounter = 0;
    }
    public CandyManager(ArrayList<Candy> candies, int prodCounter) {
        this.candies = candies;
        this.prodCounter = prodCounter;
    }
    public ArrayList<Candy> getCandies() {
        return candies;
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
                case 1 -> { // Least to greatest by price
                    while (j >= 0 && this.candies.get(temp).getPrice() <
                            this.candies.get(j).getPrice()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
                case 2 -> { // Greatest to least by price
                    while (j >= 0 && this.candies.get(temp).getPrice() >
                            this.candies.get(j).getPrice()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
                case 3 -> { // Least to greatest by quantity
                    while (j >= 0 && this.candies.get(temp).getQuantity() < this.candies.get(j).getQuantity()) {
                        Collections.swap(this.candies, temp, j);
                        temp = j;
                        j--;
                    }
                }
                case 4 -> { // Greatest to least by quantity
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
    public synchronized boolean buyInstantly(int id, int quantity, Buyer buyer) {
            int index = getIndex(id);
            int totalQuantity = candies.get(index).getTotalQuantity();
            if (totalQuantity >= quantity) {
                candies.get(index).setQuantity(totalQuantity - quantity);
                candies.get(index).getStore().editCandy(id, candies.get(index), this);
                System.out.println("Thank you for purchasing! Your total was $" + quantity *
                        candies.get(index).getPrice() );
                Sale sale = new Sale(candies.get(index), quantity, buyer);
                candies.get(index).getStore().addSale(sale);
                buyer.getPurchaseHistory().addPurchase(sale);
                return true;
            } else {
                return false;
            }
    }
    public synchronized boolean buyShoppingCart(Buyer buyer) {
            for (int i = 0; i < buyer.getShoppingCart().getPurchases().size(); i++) {
                Purchase currPurchase = buyer.getShoppingCart().getPurchases().get(i);
                boolean success = buyInstantly(currPurchase.getCandyBought().getCandyID(), currPurchase.getQuantityBought(), buyer);
                if (!success) {
                    return false;
                }
            }
        return true;
    }
    public ArrayList<Candy> search(String keyWord) {
        ArrayList<Candy> result = new ArrayList<>();
        for (int i = 0; i < candies.size(); i++) {
            String name = candies.get(i).getName();
            String store = candies.get(i).getStore().getName();
            String description = candies.get(i).getDescription();

            if (name.contains(keyWord) || store.contains(keyWord) || description.contains(keyWord)) {
                result.add(candies.get(i));
            }
        }
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
                    Candy currCandy = new Candy(data[0], stores.get(i), data[1], this.prodCounter,
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
    public void writeToFile(User user) throws IOException, ClassNotFoundException {
        File f = new File("Users.txt");
        ArrayList<User> users = new ArrayList<>();
        if (f.exists() && f.length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            users = (ArrayList<User>) ois.readObject();
            ois.close();
        }
        boolean present = false;
        for (int i = 0; i < users.size(); i++) {
            User currUser = users.get(i);
            if (currUser instanceof Seller) {
                Seller currSeller = (Seller) currUser;
                for (int k = 0; k < currSeller.getStoreManager().getStores().size(); k++) {
                    Store currStore = currSeller.getStoreManager().getStores().get(k);
                    ArrayList<Candy> currCandies = new ArrayList<>();
                    for (int j = 0; j < this.candies.size(); j++) {
                        if (this.candies.get(j).getStore().getName().equals(currStore.getName())) {
                            currCandies.add(this.candies.get(j));
                        }
                    }
                    currSeller.getStoreManager().getStores().get(k).setCandies(currCandies);
                }
            }
            if (currUser.getUsername().equals(user.getUsername()) && currUser.getPassword().equals(user.getPassword())) {
                users.set(i, user);
                present = true;
            }
        }
        if (!present) {
            users.add(user);
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, false));
        oos.writeObject(users);
        oos.flush();
        oos.close();
    }

    public int getIndex(int id) {
        for (int i = 0; i < candies.size(); i++) {
            if (candies.get(i).getCandyID() == id) {
                return i;
            }
        }
        return -1;
    }
}