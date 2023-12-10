import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class UserThread extends User implements Runnable {
    // TCP Components
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Thread thread;
    private Thread buyerThread;
    private Thread sellerThread;

    private Action action;
    private CandyManager cm;
    private static HashMap<String, User> userDatabase = new HashMap<>();

    public UserThread(Socket socket, CandyManager cm) {
        try {
            this.socket = socket;
            this.cm = cm;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            thread = new Thread(this);
            thread.start();
        } catch (IOException ie) {
            handleException(ie);
        }
    }

    public void run() {
        try {
            String str = in.readLine();
            action = Action.valueOf(str);

            switch (action) {
                case SIGNUP:
                    handleSignup();
                    break;
                case LOGIN:
                    handleLogin();
                    break;
                case BUYER:
                    buyerThread = new Thread(new BuyerThread(socket, cm));
                    buyerThread.start();
                    break;
                case SELLER:
                    sellerThread = new Thread(new SellerThread(socket, cm));
                    sellerThread.start();
                    break;
                default:
                    out.println("Invalid action.");
                    break;
            }
        } catch (IOException ie) {
            handleException(ie);
        } finally {
            closeResources();
        }
    }

    private void handleSignup(String username, String password) throws IOException {
        // checks for validation

            // Check if the username already exists in the database
            if (userDatabase.containsKey(username)) {
                out.println("Username already exists. Signup failed.");
            } else {
                // Add user to the database
                userDatabase.put(username, new User(username, password));
                out.println("Signup successful! Welcome, " + username + ".");
            }
    }

    private void handleLogin(String username, String password) throws IOException {
        // checks for validation
        if (userDatabase.containsKey(username)) {
            User user = userDatabase.get(username);
            // Check if the password matches
            if (user.getPassword().equals(password)) {
                // Inform the client that login was successful
                out.println("Login successful! Welcome back, " + username + ".");
            } else {
                // Inform the client that login failed
                out.println("Invalid password. Login failed.");
            }
        } else {
            // Inform the client that login failed
            out.println("Username not found. Login failed.");
        }
    }

    private void handleException(IOException e) {
        System.err.println("Error: " + e.getMessage());
    }

    private void closeResources() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            handleException(e);
        }
    }
}
