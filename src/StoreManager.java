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

    public static ArrayList<Store> getAllStores() {
        ArrayList<Store> allStores = new ArrayList<>();
        for (int i = 0; i < CandyManager.candies.size(); i++) {
            if (!allStores.contains(CandyManager.candies.get(i).getStore())) {
                allStores.add(CandyManager.candies.get(i).getStore());
            }
        }
        return allStores;
    }
    /**
     *
     * @return String of all stores in seller's manager
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Stores<");
        if (stores.size() == 0) {
            output.append(new Store().toString());
        } else {
            for (int i = 0; i < stores.size(); i++) {
                output.append(stores.get(i).toString());
                if (i != stores.size() - 1) {
                    output.append("#");
                }
            }
        }
        output.append(">");
        return output.toString();
    }
}