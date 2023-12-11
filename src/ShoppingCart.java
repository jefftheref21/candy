import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
// no read or write. Goes into the user files.

/**
 * Shopping cart method that handles all the purchases within a user
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
// toString method done
public class ShoppingCart implements Serializable {
    private ArrayList<Purchase> purchases;

    public ShoppingCart(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public ShoppingCart() {
        purchases = new ArrayList<>();
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchase> candiesBought) {
        this.purchases = purchases;
    }

    public void addItem(Purchase purchase) {
        purchases.add(purchase);
    }

    public boolean removeItem(Purchase purchase) {
        return purchases.remove(purchase);
    }


    /**
     * From the shopping cart stored with in the buyer object
     *
     * @return String for all purchases in shopping cart
     */
    public String viewShoppingCart() {
        StringBuilder output = new StringBuilder();

        if (purchases.isEmpty()) {
            output.append("Your shopping cart is currently empty.\n");
        } else {
            output.append("Shopping Cart:\n");
            for (int i = 0; i < purchases.size(); i++) {
                Purchase purchase = purchases.get(i);
                String purchaseInfo = "Candy ID: " + purchase.getCandyBought().getCandyID() +
                        ", Candy Name: " + purchase.getCandyBought().getName() +
                        ", Quantity: " + purchase.getQuantityBought() + "\n";
                output.append(purchaseInfo);
            }
        }
        return output.toString();
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(purchases.size());

        for (Purchase purchase : purchases) {
            out.writeUnshared(purchase);
        }
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int shoppingCartSize = in.readInt();

        purchases = new ArrayList<>(shoppingCartSize);

        for (int i = 0; i < shoppingCartSize; i++) {
            Purchase purchase = (Purchase) in.readUnshared();
            purchases.add(purchase);
        }
    }
}