import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BuyerThread extends Buyer implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Action action;

    // Add this field
    private CandyManager candyManager;

    public BuyerThread(Socket socket) {
        try {
            this.socket = socket;
            this.candyManager = candyManager; // Initialize CandyManager

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            switch (action) {
                case VIEW_PRODUCT_PAGE:
                    // get stuff from client
                    int productID = 0;
                    try {
                        productID = Integer.parseInt(in.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String productPage = candyManager.viewProductPage(productID);
                    out.println(productPage);
                    break;

                case SORT_STORE_STATS:
                    // get stuff from client
                    int sortChoice = 0;
                    int storeChoice = 0;
                    try {
                        sortChoice = Integer.parseInt(in.readLine());
                        storeChoice = Integer.parseInt(in.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //Store store = getStoreByChoice(storeChoice); // i will eventually make this method
                    //candyManager.sortStoreStatistics(store.getSales(), sortChoice, this);
                    //out.println("Store statistics sorted.");
                    break;

                case TOTAL_PURCHASE_QUANTITY:
                    int storeIndex = 0;
                    int buyerIndex = 0;
                    try {
                        storeIndex = Integer.parseInt(in.readLine());
                        buyerIndex = Integer.parseInt(in.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //Store purchaseStore = getStoreByChoice(storeIndex); // i will eventually make this method
                    //Buyer purchaseBuyer = getBuyerByChoice(buyerIndex); // i will eventually make this method
                    //int purchaseQuantity = candyManager.getTotalPurchaseQuantity(purchaseStore, purchaseBuyer);
                    //out.println("Total Purchase Quantity: " + purchaseQuantity);
                    break;

                case SORT_PRODUCTS:
                    // get stuff from client
                    int sortProductsChoice = 0;
                    try {
                        sortProductsChoice = Integer.parseInt(in.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    candyManager.sortProducts(sortProductsChoice);
                    out.println("Products sorted.");
                    break;
            }
        }
    }
}
