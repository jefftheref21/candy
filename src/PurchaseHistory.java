import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PurchaseHistory {
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
    public void exportHistoryToFile() {
        File f = new File("purchaseHistory.txt");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            for (int i = 0; i < purchases.size(); i++) {
                pw.println(purchases.get(i).toString());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
