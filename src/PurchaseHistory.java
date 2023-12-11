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
            if (store.getName().equals(purchases.get(j).getCandyBought().getStore())) {
                numOfProductsBought += purchases.get(j).getQuantityBought();
            }
        }
        return numOfProductsBought;
    }
    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public void exportHistory(File f) throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(f));
        for (int i = 0; i < purchases.size(); i++) {
            pw.println(purchases.get(i).toString());
        }
        pw.close();
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(purchases.size());

        for (Purchase purchase : purchases) {
            out.writeUnshared(purchase);
        }
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        int shoppingCartSize = in.readInt();

        purchases = new ArrayList<>(shoppingCartSize);

        for (int i = 0; i < shoppingCartSize; i++) {
            Purchase purchase = (Purchase) in.readUnshared();
            purchases.add(purchase);
        }
    }
}