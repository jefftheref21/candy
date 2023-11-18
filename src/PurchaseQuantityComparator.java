import java.util.Comparator;

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
        int result = Integer.compare(
                o1.getBuyerAccount().getTotalPurchaseQuantity(selectedStore),
                o2.getBuyerAccount().getTotalPurchaseQuantity(selectedStore)
        );
        return ascending ? result : -result;
    }
}