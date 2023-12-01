import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainServer {

    private LinkedHashMap<String, UserThread> userInfo = new LinkedHashMap<>();

    // TCP Components
    private ServerSocket serverSocket;
    private CandyManager candyManager;
    public MainServer(CandyManager candyManager) {
        this.candyManager = candyManager;
    }

    public static void main(String[] args) {
        CandyManager candyManager = readFile();
        MainServer server = new MainServer(new CandyManager());
        server.startServer(); // start the server
        writeFile();
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
    public static CandyManager readFile() {
        CandyManager candyManager = new CandyManager();

        return candyManager;
    }

    // TODO: Once again, Pablo, you know what to do
    public static void writeFile() {

    }
}
