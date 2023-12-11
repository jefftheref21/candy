import java.io.Serializable;

/**
 * Purchases of candy
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class Purchase implements Serializable {
    private Candy candyBought;
    private int quantityBought;
    public Purchase(Candy candyBought, int quantityBought) {
        this.candyBought = candyBought;
        this.quantityBought = quantityBought;
    }

    public Candy getCandyBought() {
        return candyBought;
    }

    public int getQuantityBought() {
        return quantityBought;
    }

    public void setCandyBought(Candy candyBought) {
        this.candyBought = candyBought;
    }

    public void setQuantityBought(int quantityBought) {
        this.quantityBought = quantityBought;
    }

    public String toString() {
        return "Candy ID: " + candyBought.getCandyID() + ", Store Name: " + candyBought.getStore() +
                ", Candy Name: " + candyBought.getName() + ", Quantity: " + quantityBought;
    }
}