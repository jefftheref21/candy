import java.io.*;
import java.util.ArrayList;

public class PurchaseHistory implements Serializable {
    private ArrayList<Purchase> purchases;
    public PurchaseHistory() {
        purchases = new ArrayList<>();
    }
    public PurchaseHistory(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public int getNumOfProductsBoughtAt(Store store) {
        int numOfProductsBought = 0;
        for (int j = 0; j < purchases.size(); j++) {
            if (store.getName().equals(purchases.get(j).getCandyBought().getStore().getName())) {
                numOfProductsBought += purchases.get(j).getQuantityBought();
            }
        }
        return numOfProductsBought;
    }
    public String addPurchase(Purchase purchase) {
        purchases.add(purchase);
        return "Purchase added to history.";
    }

    public String viewHistory() {
        StringBuilder output = new StringBuilder();

        if (purchases.isEmpty()) {
            output.append("Your have no purchase history in this account.\n");
        } else {
            output.append("History of Purchases:\n");
            for (Purchase purchase : purchases) {
                String saleInfo = "Candy ID: " + purchase.getCandyBought().getCandyID() +
                        ", Candy Name: " + purchase.getCandyBought().getName() +
                        ", Quantity: " + purchase.getQuantityBought() + "\n";
                output.append(saleInfo);
            }
        }

        return output.toString();
    }


    /**
     * Write to new file that shows their entire purchase history
     * Use the viewHistory function
     */
    public boolean exportHistoryToFile(String username) {
        File f = new File(username + "PurchaseHistory.txt");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            for (int i = 0; i < purchases.size(); i++) {
                pw.println(purchases.get(i).toString());
            }
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exportHistory() {
        File f = new File("purchaseHistory.txt");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(purchases);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}