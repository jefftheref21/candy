import java.util.ArrayList;

/**
 * Store method to handle candy
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class Store {
    private String name;
    private ArrayList<Integer> indexes;
    private ArrayList<Sale> sales;
    public Store() {
        name = "";
        indexes = new ArrayList<>();
        sales = new ArrayList<>();
    }
    public Store(String name) {
        this.name = name;
        indexes = new ArrayList<>();
        sales = new ArrayList<>();
    }
    public Store(String name, ArrayList<Integer> indexes, ArrayList<Sale> sales) {
        this.name = name;
        this.indexes = indexes;
        this.sales = sales;
    }

    public String getName() {
        return name;
    }


    public ArrayList<Sale> getSales() {
        return sales;
    }
    public ArrayList<Integer> getIndexes() {
        return indexes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndexes(ArrayList<Integer> indexes) {
        this.indexes = indexes;
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

        for (int i = 0; i < indexes.size(); i++) {
            total = sales.get(i).getQuantityBought() * CandyManager.candies.get(indexes.get(i)).getPrice();
        }

        return total;
    }
    public void deleteCandy(int candyID) {
        int index = CandyManager.candyIDs.indexOf(candyID);
        if (index != -1) {
            CandyManager.candies.remove(index);
            CandyManager.candyIDs.remove(index);
            CandyManager.quantities.remove(index);
        } else {
            System.out.println("Candy not found in the candies array.");
        }
    }
    public void addCandy(Candy newCandy, int newQuantity , int newID) {
        CandyManager.candies.add(newCandy);
        CandyManager.quantities.add(newQuantity);
        CandyManager.candyIDs.add(newID);
        CandyManager.prodCounter++;
        indexes.add(CandyManager.getIndex(newID));
    }
    public void editCandy(int candyID, Candy updatedCandy, int newQuantity) {
        int index = CandyManager.candyIDs.indexOf(candyID);
        if (index != -1) {
            CandyManager.candies.set(index, updatedCandy);
            CandyManager.quantities.set(index, newQuantity);
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
}