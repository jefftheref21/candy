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

    public CandyManager() {
        this.candies = new ArrayList<>();
        this.prodCounter = 0;
    }
    public CandyManager(ArrayList<Candy> candies, int prodCounter) {
        this.candies = candies;
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
    public void sortStoreStatistics(ArrayList<Store> stores, int choice, Buyer buyer) {
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
    }
    public int getTotalPurchaseQuantity(Store store, Buyer buyer){
        int purchaseQuantity = 0;
        for (int k = 0; k < buyer.getPurchaseHistory().getPurchases().size(); k++) {
            purchaseQuantity += store.getSalesByBuyer(buyer).get(k).getQuantityBought();
        }
        return purchaseQuantity;
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
    public String viewStoreStatistics(ArrayList<Store> stores, ArrayList<User> users) {
        StringBuilder output = new StringBuilder();
        System.out.println("Show up!");
        for (Store store : stores) {

            String storeName = "Store Name: " + store.getName() + "\n";
            output.append(storeName);
            int numOfProductsBought = 0;
            for (User user : users) {
                if (user instanceof Buyer buyer) {
                    for (Sale sale : store.getSalesByBuyer(buyer)) {
                        numOfProductsBought += sale.getQuantityBought();
                    }
                }
                if (user instanceof Seller seller) {
                    if (seller.getStoreManager().getStores().contains(store)) {
                        String sellerName = "Seller Name: " + seller.getUsername() + "\n";
                        output.append(sellerName);
                    }
                }
            }
            String storeInfo =  "Number of products sold: " + store.getNumberOfProductsSold() + "\n" +
                    "Number of products sold to this buyer: " + numOfProductsBought + "\n";
            output.append(storeInfo);
        }

        return output.toString();
    }
    public void buyInstantly(int id, int quantity, Buyer buyer) throws IOException {
        int index = getIndex(id);
        int totalQuantity = candies.get(index).getTotalQuantity();
        if (totalQuantity >= quantity) {
            candies.get(index).setQuantity(totalQuantity - quantity);
            candies.get(index).getStore().editCandy(id, candies.get(index), totalQuantity - quantity);
            System.out.println("Thank you for purchasing! Your total was $" + quantity *
                    candies.get(index).getPrice() );
            Sale sale = new Sale(candies.get(index), quantity, buyer);
            candies.get(index).getStore().addSale(sale);
            buyer.getPurchaseHistory().addPurchase(sale);
        } else {
            System.out.println("Error! Not enough candy on stock!");
        }
    }
    public void buyShoppingCart(Buyer buyer) {
        boolean boughtTooMuch;
        for (int i = 0; i < buyer.getShoppingCart().getPurchases().size(); i++) {
            int candyID = buyer.getShoppingCart().getPurchases().get(i).getCandyBought().getCandyID();
            int index = getIndex(candyID);
            int quantityPurchased = buyer.getShoppingCart().getPurchases().get(i).getQuantityBought();
            int totalQuantity = candies.get(index).getQuantity();
            if (totalQuantity >= quantityPurchased) {
                Sale sale = new Sale(candies.get(index), totalQuantity, buyer);
                buyer.getPurchaseHistory().addPurchase(sale);
                candies.get(index).setQuantity(totalQuantity - quantityPurchased);
            }
        }
    }
    public String search(String keyWord) {
        String result = "";

        for (int i = 0; i < candies.size(); i++) {
            String name = candies.get(i).getName();
            String store = candies.get(i).getStore().getName();
            String description = candies.get(i).getDescription();

            if (name.contains(keyWord) || store.contains(keyWord) || description.contains(keyWord)) {
                result += "Product ID: " + candies.get(i).getCandyID() + ", Product Name: " +
                        candies.get(i).getName() + ", Store: " +
                        candies.get(i).getStore().getName() + ", Price: " +
                        candies.get(i).getPrice() + "\n";

            }
        }

        if (!result.isEmpty()) {
            return result;
        } else {
            result = "No candies found!";
            return result;
        }
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