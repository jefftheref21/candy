import java.io.Serializable;
import java.util.Objects;

/**
 * Sales of candy
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
//no read or write files
// needs toString method
public class Sale extends Purchase {
    private Buyer buyerAccount;
    private double totalRevenue;
    public Sale(Candy candyBought, int quantityBought, Buyer buyerAccount) {
        super(candyBought, quantityBought);
        this.buyerAccount = buyerAccount;
        totalRevenue = candyBought.getPrice() * quantityBought;
    }

    public Buyer getBuyerAccount() {
        return buyerAccount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setBuyerAccount(Buyer buyerAccount) {
        this.buyerAccount = buyerAccount;
    }
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;  // Call superclass equals method

        Sale sale = (Sale) o;

        // Compare additional attributes
        return Double.compare(sale.totalRevenue, totalRevenue) == 0 &&
                Objects.equals(buyerAccount, sale.buyerAccount);
    }
}