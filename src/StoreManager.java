import java.io.Serializable;
import java.util.ArrayList;
import java.io.*;

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

    public ArrayList<Store> getStoresByBuyer(Buyer buyer) {
        ArrayList<Store> storesByBuyer = new ArrayList<>();
        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);
            if (store.getBuyers().contains(buyer) && !storesByBuyer.contains(store)) {
                storesByBuyer.add(store);
            }
        }
        return storesByBuyer;
    }

    public void buyInstantly(Candy candy, int quantityBought, Buyer buyer) {
        Sale sale = new Sale(candy, quantityBought, buyer);
        Store store = getStore(candy.getStore());
        store.addSale(sale);
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(stores.size());

        for (Store store : stores) {
            out.writeUnshared(store);
        }
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int storesSize = in.readInt();

        stores = new ArrayList<>(storesSize);

        for (int i = 0; i < storesSize; i++) {
            Store store = (Store) in.readUnshared();
            stores.add(store);
        }
    }
}