import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MainServer {

    private LinkedHashMap<String, UserThread> userInfo = new LinkedHashMap<>();

    // TCP Components
    private ServerSocket serverSocket;
    private CandyManager candyManager;
    public MainServer(CandyManager candyManager) {
        this.candyManager = candyManager;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // CandyManager candyManager = readFile("filename.txt");
        MainServer server = new MainServer(new CandyManager());
        server.startServer(); // start the server
        // writeFile("filename.txt", candyManager);
    }

    public void startServer() {
        int portNo = 1234;

        try {
            serverSocket = new ServerSocket(portNo, 0, InetAddress.getLocalHost());
            System.out.println(serverSocket);

            System.out.println(serverSocket.getInetAddress().getHostName() + ":"
                    + serverSocket.getLocalPort());

            while (true) {
                Socket socket = serverSocket.accept();
                new UserThread(socket);
            }
        } catch (IOException e) {
            System.out.println("IO Exception:" + e);
            System.exit(1);
        }
    }

    public HashMap<String, UserThread> getUserInfo() {
        return userInfo;
    }

    // TODO: Pablo, you know what to do
    public static CandyManager readFile(String filename) throws IOException, ClassNotFoundException {
        CandyManager candyManager = new CandyManager();
        FileInputStream fi = new FileInputStream(new File(filename));
        ObjectInputStream ois = new ObjectInputStream(fi);
        candyManager.candies = (ArrayList<Candy>) ois.readObject();
        ois.close();
        return candyManager;
    }

    // TODO: Once again, Pablo, you know what to do
    public static void writeFile(String filename, CandyManager cm) throws IOException {
        FileOutputStream fo = new FileOutputStream(new File(filename));
        ObjectOutputStream oos = new ObjectOutputStream(fo);
        oos.writeObject(cm.candies);
        oos.flush();
        oos.close();
    }
}
