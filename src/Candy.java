import java.io.*;
import java.util.*;
/**
 * Candy class
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
// no read or write
// toString method done
public class Candy implements Serializable {
    private String name; // Same thing as name in this case
    private int candyID;
    private String store;
    private String description;
    private double price;
    private int quantity;
    public Candy(String name, String store, String description, int candyID, int quantity, double price) {
        this.name = name;
        this.store = store;
        this.description = description;
        this.candyID = candyID;
        this.price = price;
        this.quantity = quantity;
    }
    public Candy() {
        name = null;
        store = null;
        description = null;
        quantity = 0;
        double price = 0.0;
    }

    /**
     * getName
     * @return Name of candy
     */
    public String getName() {
        return name;
    }

    /**
     * getStore
     * @return Store of candy
     */
    public String getStore() {
        return store;
    }

    /**
     * getDescription
     * @return Description of candy
     */
    public String getDescription() {
        return description;
    }

    public int getCandyID() {
        return candyID;
    }

    /**
     * getPrice
     * @return Price of candy
     */
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    /**
     * setName
     * @param name - inputted name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setStore
     * @param store - inputted store
     */
    public void setStore(String store) {
        this.store = store;
    }

    /**
     * setDescription
     * @param description - inputted description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCandyID(int candyID) {
        this.candyID = candyID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * setPrice
     * @param price - inputted price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return String.format("Candy<%s_%s_%s_%s_%s_%s>", name, store, description, candyID,quantity,  price);
    }
    public String toCSV() {
        return String.format("%s,%s,%s,%s,%s", name, store, description, quantity, price);
    }
}