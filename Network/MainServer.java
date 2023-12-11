import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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

    public MainServer(CandyManager candyManager, StoreManager storeManager) {
        this.candyManager = candyManager;
        this.storeManager = storeManager;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // CandyManager candyManager = readFile("filename.txt");
        Candy candy1 = new Candy("Snickers", "Walmart", "Chocolate bar", 1, 50, 1.00);
        Candy candy2 = new Candy("Twix", "Walmart", "Chocolate bar",2, 25, 2.00);
        Candy candy3 = new Candy("M&Ms", "Walmart", "Chocolate bar", 3, 100, 3.00);
        Candy candy4 = new Candy("Kit Kat", "Walmart", "Chocolate bar", 4, 75, 4.00);
        Candy candy5 = new Candy("Sour Patch Kids", "Walmart", "Sour candy", 5, 50, 1.00);
        Candy candy6 = new Candy("Sour Skittles", "Walmart", "Sour candy", 6, 25, 2.00);
        ArrayList<Candy> candies = new ArrayList<>();
        candies.add(candy1);
        candies.add(candy2);
        candies.add(candy3);
        candies.add(candy4);
        candies.add(candy5);
        candies.add(candy6);
        CandyManager candyManager = new CandyManager(candies, 7);

        Store store1 = new Store("Walmart", candies, new ArrayList<>());
        ArrayList<Store> stores = new ArrayList<>();
        stores.add(store1);

        StoreManager storeManager = new StoreManager(stores);

        MainServer server = new MainServer(candyManager, storeManager);
        server.initializeUsers();
        server.startServer(); // start the server
        // writeFile("filename.txt", candyManager);
    }

    public void initializeUsers() {
        users = new ArrayList<>();
        users.add(new Buyer("jeff", "pass"));
    }

    public void startServer() throws ClassNotFoundException {
        int portNo = 1234;

        try {
            serverSocket = new ServerSocket(portNo, 0, InetAddress.getLocalHost());
            System.out.println(serverSocket);

            System.out.println(serverSocket.getInetAddress().getHostName() + ":"
                    + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                new UserThread(socket, candyManager, storeManager, users);
            }
        } catch (IOException e) {
            System.out.println("IO Exception:" + e);
            System.exit(1);
        }
    }


    // TODO: Pablo, you know what to do
    public static CandyManager readFile(String filename) throws IOException, ClassNotFoundException {
        CandyManager candyManager = new CandyManager();
        FileInputStream fi = new FileInputStream(new File(filename));
        ObjectInputStream ois = new ObjectInputStream(fi);
        candyManager.candies = (ArrayList<Candy>) ois.readUnshared();
//        ois.close();
        return candyManager;
    }

    // TODO: Once again, Pablo, you know what to do
    public static void writeFile(String filename, CandyManager cm) throws IOException {
        FileOutputStream fo = new FileOutputStream(new File(filename));
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(cm.candies);
        oos.flush();
//        oos.close();
    }
    public void writeToFile(User user) throws IOException, ClassNotFoundException {
        File f = new File("Users.txt");
        ArrayList<User> users = new ArrayList<>();
        if (f.exists() && f.length() > 0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            users = (ArrayList<User>) ois.readObject();
            ois.close();
        }
        boolean present = false;
        for (int i = 0; i < users.size(); i++) {
            User currUser = users.get(i);
            if (currUser instanceof Seller) {
                Seller currSeller = (Seller) currUser;
                for (int k = 0; k < currSeller.getStoreManager().getStores().size(); k++) {
                    Store currStore = currSeller.getStoreManager().getStores().get(k);
                    ArrayList<Candy> currCandies = new ArrayList<>();
                    for (int j = 0; j < candyManager.candies.size(); j++) {
                        if (candyManager.candies.get(j).getStore().equals(currStore.getName())) {
                            currCandies.add(candyManager.candies.get(j));
                        }
                    }
                    currSeller.getStoreManager().getStores().get(k).setCandies(currCandies);
                }
            }
            if (currUser.getUsername().equals(user.getUsername()) && currUser.getPassword().equals(user.getPassword())) {
                users.set(i, user);
                present = true;
            }
        }
        if (!present) {
            users.add(user);
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, false));
        oos.writeObject(users);
        oos.flush();
        oos.close();
    }
}