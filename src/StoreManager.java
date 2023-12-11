import java.io.Serializable;
import java.util.ArrayList;

/**
 * Store Manager to handle all the stores
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class StoreManager implements Serializable {
    private ArrayList<Store> stores;
    public StoreManager() {
        this.stores = new ArrayList<>();
    }
    public StoreManager(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }
    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }
    public void addStore(Store store) {
        stores.add(store);
    }

    public void removeStore(Store store) {
        stores.remove(store);
    }

}