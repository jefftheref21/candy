import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


/**
 * Buyers of Candy Marketplace
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
// class with read and write to files
// needs toString method

public class Buyer extends User {
    private ShoppingCart shoppingCart;
    private PurchaseHistory purchaseHistory;
    public Buyer() {
        super();
        shoppingCart = new ShoppingCart();
        purchaseHistory = new PurchaseHistory();
    }

    public Buyer(String username, String password) {
        super(username, password);
        shoppingCart = new ShoppingCart();
        purchaseHistory = new PurchaseHistory();
    }
    /* // Lets not use this constructors, instead just set it afterwards
    public Buyer(String username, String password, ShoppingCart shoppingCart) {
        super(username, password);
        this.shoppingCart = shoppingCart;
        purchaseHistory = new PurchaseHistory();
    }
    public Buyer (String username, String password, ShoppingCart shoppingCart, PurchaseHistory purchaseHistory) {
        super(username, password);
        this.shoppingCart = shoppingCart;
        this.purchaseHistory = purchaseHistory;
    }
     */

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addToShoppingCart(Purchase purchase) {
        shoppingCart.addItem(purchase);
    }

    public String viewMarketplace() {
        StringBuilder marketPlace = new StringBuilder("Welcome to the marketplace!\nAvailable Candies:\n");
        for (int i = 0; i < CandyManager.candies.size(); i++) {
            if (CandyManager.quantities.get(i) > 0) {
                String candyInfo = "Candy ID: " + CandyManager.candyIDs.get(i) +
                        ", Candy Name: " + CandyManager.candies.get(i).getName() +
                        ", Store: " + CandyManager.candies.get(i).getStore().getName() +
                        ", Price: " + CandyManager.candies.get(i).getPrice() + "\n";
                marketPlace.append(candyInfo);
            }
        }
        return marketPlace.toString();
    }

    public String search(String keyWord) {
        String result = "";
        int length = CandyManager.candies.size();

        for (int i = 0; i < length; i++) {
            String name = CandyManager.candies.get(i).getName();
            String store = CandyManager.candies.get(i).getStore().getName();
            String description = CandyManager.candies.get(i).getDescription();

            if (name.contains(keyWord) || store.contains(keyWord) || description.contains(keyWord)) {
                result += "Product ID: " + CandyManager.candyIDs.get(i) + ", Product Name: " +
                        CandyManager.candies.get(i).getName() + ", Store: " +
                        CandyManager.candies.get(i).getStore().getName() + ", Price: " +
                        CandyManager.candies.get(i).getPrice() + "\n";

            }
        }

        if (!result.isEmpty()) {
            return result;
        } else {
            result = "No candies found!";
            return result;
        }
    }

    /**
     *

     * @return String of sorted statistics based on something
     */
    public void sortProducts(int choice) {
        // TODO: Sort for price or quantity
        for (int i = 1; i < CandyManager.candies.size(); i++) {
            int temp = i;
            int j = i - 1;

            switch (choice) {
                case 1 -> { // Least to greatest by price
                    while (j >= 0 && CandyManager.candies.get(temp).getPrice() <
                            CandyManager.candies.get(j).getPrice()) {
                        Collections.swap(CandyManager.candies, temp, j);
                        Collections.swap(CandyManager.candyIDs, temp, j);
                        Collections.swap(CandyManager.quantities, temp, j);

                        temp = j;
                        j--;
                    }
                }
                case 2 -> { // Greatest to least by price
                    while (j >= 0 && CandyManager.candies.get(temp).getPrice() >
                            CandyManager.candies.get(j).getPrice()) {
                        Collections.swap(CandyManager.candies, temp, j);
                        Collections.swap(CandyManager.candyIDs, temp, j);
                        Collections.swap(CandyManager.quantities, temp, j);

                        temp = j;
                        j--;
                    }
                }
                case 3 -> { // Least to greatest by quantity
                    while (j >= 0 && CandyManager.quantities.get(temp) < CandyManager.quantities.get(j)) {
                        Collections.swap(CandyManager.candies, temp, j);
                        Collections.swap(CandyManager.candyIDs, temp, j);
                        Collections.swap(CandyManager.quantities, temp, j);

                        temp = j;
                        j--;
                    }
                }
                case 4 -> { // Greatest to least by quantity
                    while (j >= 0 && CandyManager.quantities.get(temp) > CandyManager.quantities.get(j)) {
                        Collections.swap(CandyManager.candies, temp, j);
                        Collections.swap(CandyManager.candyIDs, temp, j);
                        Collections.swap(CandyManager.quantities, temp, j);

                        temp = j;
                        j--;
                    }
                }
            }
        }
    }



//    public ArrayList<Seller> getSellersFrom(ArrayList<User> users) {
//        ArrayList<String> sellerNames = new ArrayList<>();
//        for (User user : users) {
//            if (user instanceof Seller) {
//
//                sellerNames.add(user.getUsername());
//            }
//        }
//        return sellerNames;
//    }

    /**
     * Should
     * @return String for store name, number of products, and products bought from customer
     */
    public String viewStoreStatistics(ArrayList<Store> stores, ArrayList<User> users) {
        StringBuilder output = new StringBuilder();
        System.out.println("Show up!");
        for (Store store : stores) {
            int numOfProductsBought = purchaseHistory.getNumOfProductsBoughtAt(store);

            String storeName = "Store Name: " + store.getName() + "\n";
            output.append(storeName);

            for (User user : users) {
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

    /**
     * Should change
     * @param stores - all the stores
     */
    public void sortStoreStatistics(ArrayList<Store> stores, int choice) {
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
                        numOfProductsBought.add(purchaseHistory.getNumOfProductsBoughtAt(store));
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
                        numOfProductsBought.add(purchaseHistory.getNumOfProductsBoughtAt(store));
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

    public String viewProductPage(int productID) {
        String productPage = "";
        String productName = "";
        String productDescription = "";
        int productQuantity = 0;
        for (int i = 0; i < CandyManager.candyIDs.size(); i++) {
            if (CandyManager.candyIDs.get(i) == productID) {
                productName = CandyManager.candies.get(i).getName();
                productDescription = CandyManager.candies.get(i).getDescription();
                productQuantity = CandyManager.quantities.get(i);
            }
        }
        productPage = "About " + productName + ":\n";
        productPage += "Product Quantity: " + productQuantity + "\n";
        productPage += "Product Description: " + productDescription + "\n";
        return productPage;
    }

    public void buyInstantly(int id, int quantity, Buyer buyer) throws IOException {
        int index = CandyManager.candyIDs.indexOf(id);
        int totalQuantity = CandyManager.quantities.get(index);
        Candy currCandy = CandyManager.candies.get(index);
        if (totalQuantity > quantity) {
            currCandy.setQuantity(totalQuantity - quantity);
            currCandy.getStore().editCandy(id, currCandy, totalQuantity - quantity);
            System.out.println("Thank you for purchasing! Your total was $" + quantity *
                    CandyManager.candies.get(index).getPrice() );
            Sale sale = new Sale(CandyManager.candies.get(index), quantity, buyer);
            purchaseHistory.addPurchase(sale);
            currCandy.getStore().addSale(sale);
        } else if (totalQuantity == quantity) {
            currCandy.setQuantity(0);
            currCandy.getStore().editCandy(id, currCandy, 0);
            System.out.println("Thank you for purchasing! Your total was $" + quantity *
                    CandyManager.candies.get(index).getPrice());
            Sale sale = new Sale(CandyManager.candies.get(index), quantity, buyer);
            purchaseHistory.addPurchase(sale);
            currCandy.getStore().addSale(sale);
        } else {
            System.out.println("Error! Not enough candy on stock!");
        }
    }

    public void buyShoppingCart(Buyer buyer) {
        boolean boughtTooMuch;
        for (int i = 0; i < shoppingCart.getPurchases().size(); i++) {
            int candyID = shoppingCart.getPurchases().get(i).getCandyBought().getCandyID();
            int candyIndex = CandyManager.candyIDs.indexOf(candyID);
            int quantityPurchased = shoppingCart.getPurchases().get(i).getQuantityBought();
            int totalQuantity = CandyManager.quantities.get(candyID);

            if (totalQuantity > quantityPurchased) {
                CandyManager.quantities.remove(candyIndex);
                CandyManager.quantities.add(candyIndex, totalQuantity - quantityPurchased);
                Sale sale = new Sale(CandyManager.candies.get(candyIndex), totalQuantity, buyer);
                purchaseHistory.addPurchase(sale);
            } else if (totalQuantity == quantityPurchased) {
                CandyManager.quantities.remove(candyIndex);
                CandyManager.quantities.add(candyIndex, 0);
                Sale sale = new Sale(CandyManager.candies.get(candyIndex), totalQuantity, buyer);
                purchaseHistory.addPurchase(sale);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(getUsername(), buyer.getUsername()) &&
                Objects.equals(getPassword(), buyer.getPassword()) &&
                Objects.equals(shoppingCart, buyer.shoppingCart) &&
                Objects.equals(purchaseHistory, buyer.purchaseHistory);
    }
    public int getTotalPurchaseQuantity(Store store){
        int purchaseQuantity = 0;
        for(int k = 0; k < purchaseHistory.getPurchases().size(); k++) {
            if (store.getSales().contains(purchaseHistory.getPurchases().get(k))) {
                purchaseQuantity +=  purchaseHistory.getPurchases().get(k).getQuantityBought();
            }
        }
        return purchaseQuantity;
    }
    @Override
    public String toString() {
        return String.format("%s, %s", super.toString(),this.shoppingCart.toString());
    }
}