import java.util.Comparator;
import java.util.ArrayList;

/**
 * Purchases of quantity comparator
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */

class PurchaseQuantityComparator implements Comparator<Sale> {
    private final boolean ascending;
    private final Store selectedStore;

    public PurchaseQuantityComparator(Store selectedStore, boolean ascending) {
        this.selectedStore = selectedStore;
        this.ascending = ascending;
    }

    @Override
    public int compare(Sale o1, Sale o2) {
        return compare(o1, o2, selectedStore);
    }

    public int compare(Sale o1, Sale o2, Store selectedStore) {
        ArrayList<Sale> s1 = selectedStore.getSalesByBuyer(o1.getBuyerAccount());
        ArrayList<Sale> s2 = selectedStore.getSalesByBuyer(o2.getBuyerAccount());
        int t1 = 0;
        int t2 = 0;
        for (int i = 0; i < s1.size(); i++) {
            t1 += s1.get(i).getQuantityBought();
        }
        for (int i = 0; i < s2.size(); i++) {
            t2 += s2.get(i).getQuantityBought();
        }
        int result = Integer.compare(t1, t2);
        return ascending ? result : -result;
    }
}