import java.util.Comparator;

/**
 * Candy Quantity Comparator
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
class CandyQuantityComparator implements Comparator<Candy> {
    private final boolean ascending;

    public CandyQuantityComparator(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(Candy candy1, Candy candy2) {
        // Compare candies based on their quantity
        int result = Integer.compare(candy1.getTotalQuantity(), candy2.getTotalQuantity());

        // Adjust result based on the desired sorting direction
        return ascending ? result : -result;
    }
}

