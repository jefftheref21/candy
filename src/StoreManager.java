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

    public Store getStore(String storeName) {
        Store store = new Store();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getName().equals(storeName)) {
                store = stores.get(i);
            }
        }
        return store;
    }

    public void buyInstantly(Candy candy, int quantityBought, Buyer buyer) {
        Sale sale = new Sale(candy, quantityBought, buyer);
        Store store = getStore(candy.getStore());
        store.addSale(sale);
    }
}