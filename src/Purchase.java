/**
 * Purchases of candy
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class Purchase {
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
        // toString method for file writing. Create method called toMenu if you want different formatting.
        // delimiting character: $
        return String.format("Purchase<%s$%s$%s>", candyBought.getName(), candyBought.getCandyID(), quantityBought);
    }
}