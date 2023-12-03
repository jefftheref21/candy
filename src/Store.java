import java.io.Serializable;
import java.util.*;

/**
 * Store method to handle candy
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class Store implements Serializable {
    private String name;
    private ArrayList<Candy> candies;
    private ArrayList<Sale> sales;
    public Store() {
        name = "";
        candies = new ArrayList<>();
        sales = new ArrayList<>();
    }
    public Store(String name) {
        this.name = name;
        candies = new ArrayList<>();
        sales = new ArrayList<>();
    }
    public Store(String name, ArrayList<Candy> candies, ArrayList<Sale> sales) {
        this.name = name;
        this.candies = candies;
        this.sales = sales;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }
    public ArrayList<Candy> getCandies() {
        return candies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCandies(ArrayList<Candy> candies) {
        this.candies = candies;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }
    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public double totalRevenue() {
        int length = sales.size();
        double total = 0;

        for (int i = 0; i < candies.size(); i++) {
            total = sales.get(i).getQuantityBought() * candies.get(i).getPrice();
        }

        return total;
    }
    public ArrayList<Buyer> getBuyers() {
        ArrayList<Buyer> buyers = new ArrayList<>();
        for (int i = 0; i < sales.size(); i++) {
            buyers.add(sales.get(i).getBuyerAccount());
        }
        return buyers;
    }
    public ArrayList<Sale> getSalesByBuyer(Buyer buyer) {
        ArrayList<Sale> sales = new ArrayList<>();
        for (Sale sale: this.sales) {
            if (sale.getBuyerAccount().equals(buyer)) {
                sales.add(sale);
            }
        }
        return sales;
    }
    public void deleteCandy(int candyID, CandyManager cm) {
        int index = cm.getIndex(candyID);
        if (index != -1) {
            cm.candies.remove(index);
            candies.remove(cm.candies.get(index));
        } else {
            System.out.println("Candy not found in the candies array.");
        }
    }
    public void addCandy(Candy newCandy, CandyManager cm) {
        cm.candies.add(newCandy);
        cm.prodCounter++;
        candies.add(newCandy);
    }
    public void editCandy(int candyID, Candy updatedCandy, CandyManager cm) {
        int index = cm.getIndex(candyID);
        if (index != -1) {
            candies.set(candies.indexOf(cm.candies.get(index)), updatedCandy);
            cm.candies.set(index, updatedCandy);
        } else {
            System.out.println("Old candy not found in the candies array.");
        }
    }
    public int getNumberOfProductsSold() {
        int totalNum = 0;
        for (int i = 0; i < sales.size(); i++) {
            totalNum += sales.get(i).getQuantityBought();
        }
        return totalNum;
    }
    // just for debugging purposes
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (candies.size() > 0) {
            str.append(Integer.toString(candies.get(0).getQuantity()));
            for (int i = 1; i < candies.size(); i++) {
                str.append("," + Integer.toString(candies.get(i).getQuantity()));
            }
        } else {
            str.append("NC");
        }
        return str.toString();
    }
}