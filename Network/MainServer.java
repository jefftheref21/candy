import java.io.*;
import java.net.*;
import java.util.*;

public class MainServer {
    private static LinkedHashMap<String, UserThread> userInfo = new LinkedHashMap<String, UserThread>();

    // TCP Components
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerSocket serverSocket;
    private CandyManager candyManager;

    private StoreManager storeManager;
    private Action action;

    private ArrayList<User> users;

    public MainServer(CandyManager candyManager, StoreManager storeManager, ArrayList<User> users) {
        this.candyManager = candyManager;
        this.storeManager = storeManager;
        this.users = users;
    }

    public static void main(String[] args) throws IOException {
        CandyManager candyManager = new CandyManager();
        Candy candy1 = new Candy("Snickers", "Target", "Chocolate bar", 1, 50, 1.00);
        Candy candy2 = new Candy("Twix", "Walmart", "Chocolate bar", 2, 25, 2.00);
        Candy candy3 = new Candy("M&Ms", "Walgreens", "Chocolate bar", 3, 100, 3.00);
        Candy candy4 = new Candy("Kit Kat", "Walmart", "Chocolate bar", 4, 75, 4.00);
        Candy candy5 = new Candy("Sour Patch Kids", "CVS", "Sour candy", 5, 50, 1.00);
        Candy candy6 = new Candy("Sour Skittles", "Target", "Sour candy", 6, 25, 2.00);
        Candy candy7 = new Candy("Hershey's", "CVS", "Sour candy", 7, 100, 3.00);
        Candy candy8 = new Candy("Reese's", "Walgreens", "Sour candy", 8, 75, 4.00);
        Candy candy9 = new Candy("Starburst", "Target", "Fruity candy", 9, 50, 1.00);
        ArrayList<Candy> candies = new ArrayList<>();
        candies.add(candy1);
        candies.add(candy2);
        candies.add(candy3);
        candies.add(candy4);
        candies.add(candy5);
        candies.add(candy6);
        candies.add(candy7);
        candies.add(candy8);
        candies.add(candy9);
        candyManager.setCandies(candies);
        candyManager.setProdCounter(7);

        ArrayList<Candy> candyWalmart = new ArrayList<>();
        candyWalmart.add(candy2);
        candyWalmart.add(candy4);
        ArrayList<Candy> candyTarget = new ArrayList<>();
        candyTarget.add(candy1);
        candyTarget.add(candy6);
        candyTarget.add(candy9);
        ArrayList<Candy> candyWalgreens = new ArrayList<>();
        candyWalgreens.add(candy3);
        candyWalgreens.add(candy8);
        ArrayList<Candy> candyCVS = new ArrayList<>();
        candyCVS.add(candy5);
        candyCVS.add(candy7);

        Store store1 = new Store("Walmart", candyWalmart, new ArrayList<>());
        Store store2 = new Store("Target", candyTarget, new ArrayList<>());
        Store store3 = new Store("Walgreens", candyWalgreens, new ArrayList<>());
        Store store4 = new Store("CVS", candyCVS, new ArrayList<>());
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(store1);
        stores.add(store2);
        stores.add(store3);
        stores.add(store4);

        StoreManager storeManager = new StoreManager(stores);

        ArrayList<User> users = new ArrayList<>();
        users.add(new Buyer("jeff", "pass"));

        Seller seller = new Seller("bezos", "money");
        seller.setStoreManager(storeManager);

        users.add(seller);

        MainServer server = new MainServer(candyManager, storeManager, users);
        server.initializeUsers();
        server.startServer(); // start the server

    }

    /**
     * Hardcoding two user accounts, one for buyer and one for seller
     */
    public void initializeUsers() {
    }

    public void startServer() throws IOException {
        int portNo = 1234;

            serverSocket = new ServerSocket(portNo, 0, InetAddress.getLocalHost());
            System.out.println(serverSocket);

            System.out.println(serverSocket.getInetAddress().getHostName() + ":"
                    + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                new UserThread(socket, candyManager, storeManager, users);
            }
    }
}