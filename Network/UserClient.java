import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class UserClient {
    private Socket socket;
    private final Application application;
    private Action action;

    private final BufferedReader in;
    private final PrintWriter out;

    public UserClient(Application application) throws IOException {
        this.application = application;

        initConnection();

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        runClient();
    }

    public void initConnection() {
        String hostname = "10.186.142.229";
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

    public void runClient() {
        application.run();
        try {
            String str = in.readLine();
            action = Action.valueOf(str);
            switch (action) {
                case SIGNUP:
                    break;
                case LOGIN:
                    break;
                case BUYER:
                    break;
                case SELLER:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new UserClient(new Application());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
