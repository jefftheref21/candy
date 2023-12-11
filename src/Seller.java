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
}