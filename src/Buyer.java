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
    @Override
    public String toString() {
        return String.format("%s, %s", super.toString(),this.shoppingCart.toString());
    }
}