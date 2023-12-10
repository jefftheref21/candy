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
    private Action action;

    private ArrayList<User> users;

    public MainServer(CandyManager candyManager) {
        this.candyManager = candyManager;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // CandyManager candyManager = readFile("filename.txt");
        MainServer server = new MainServer(new CandyManager());
        server.initializeUsers();
        server.startServer(); // start the server
        // writeFile("filename.txt", candyManager);
    }

    public void initializeUsers() {
        users = new ArrayList<>();
        users.add(new Seller("jeff", "pass"));
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
                new UserThread(socket, candyManager, users);
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
        candyManager.candies = (ArrayList<Candy>) ois.readObject();
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
}