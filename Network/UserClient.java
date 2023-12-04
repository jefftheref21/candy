import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class UserClient extends User {
    private Socket socket;
    private final Application application;

    private final BufferedReader in;
    private final PrintWriter out;
    private Action action;

    public UserClient(Application application) throws IOException {
        this.application = application;

        initConnection();

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void initConnection() {
        String hostname = "10.186.142.72";
        int portNo = 1234;
        try {
            socket = new Socket(hostname, portNo);

            JOptionPane.showMessageDialog(null, "Server connected!",
                    "Connection Confirmation", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server connection failed!",
                    "Connection Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendSignUp(String act, String username, String password) {
        out.println(act);
        out.println(username);
        out.println(password);
    }

    public void sendLogin(String act, String username, String password) {
        out.println(act);
        out.println(username);
        out.println(password);
    }

    public Action receiveAction() {
        try {
            String input = in.readLine();
            action = Action.valueOf(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return action;
    }
}
