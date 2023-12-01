import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

//sample comment
//sample comment nathan park
public class Application implements Runnable {
    UserClient client;
    JButton signUpButton;
    JButton loginButton;
    JButton buyerButton;
    JButton sellerButton;


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signUpButton) {
                // client.sendSignUp();
            }
            if (e.getSource() == loginButton) {
                // client.sendLogin();
            }
            if (e.getSource() == buyerButton) {
                // client.sendBuyer();
            }
            if (e.getSource() == sellerButton) {
                // client.sendSeller();
            }
        }
    };

    public Application() throws IOException {
        client = new UserClient(this);
    }

    public static void main(String[] args) throws IOException {
        // SwingUtilities.invokeLater(new Application());
    }

    public void run() {
        Candy candy1 = new Candy("Snickers", new Store("Walmart"), "Chocolate bar", 1, 50, 1.00);
        Candy candy2 = new Candy("Twix", new Store("Walmart"), "Chocolate bar",2, 25, 2.00);
        Candy candy3 = new Candy("M&Ms", new Store("Walmart"), "Chocolate bar", 3, 100, 3.00);
        Candy candy4 = new Candy("Kit Kat", new Store("Walmart"), "Chocolate bar", 4, 75, 4.00);
        Candy candy5 = new Candy("Sour Patch Kids", new Store("Walmart"), "Sour candy", 5, 50, 1.00);
        Candy candy6 = new Candy("Sour Skittles", new Store("Walmart"), "Sour candy", 6, 25, 2.00);
        Candy[] candies = new Candy[6];
        candies[0] = candy1;
        candies[1] = candy2;
        candies[2] = candy3;
        candies[3] = candy4;
        candies[4] = candy5;
        candies[5] = candy6;

        showWelcomeMessageDialog();
        showStartingDialog();
        // Marketplace.
        Marketplace marketplace = new Marketplace(candies);
        marketplace.run();

        ControlCenter controlCenter = new ControlCenter();

    }

    /**
     * Displays Welcome Message
     */
    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to the Candy Marketplace",
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Dialog that makes user choose whether to sign up or login
     */
    public static void showStartingDialog() {
        String[] options = {"Sign Up", "Login"};
        int signUpOrLogin = JOptionPane.showOptionDialog(null, "Select an option:",
                "Sign Up or Login", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        String type;
        if (signUpOrLogin == 0) {
            type = showUserTypeDialog();
            showSignUpDialog(type);
        } else {
            type = showUserTypeDialog();
            showLoginDialog(type);
        }
    }

    /**
     * Dialog that makes user choose their user type
     * @return the type of user
     */
    public static String showUserTypeDialog() {
        String[] options = {"Buyer", "Seller"};
        int userType = JOptionPane.showOptionDialog(null, "Select your user type:",
                "User Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        return options[userType];
    }

    /**
     * For signing up
     * @param type - based on type of user
     * @return might get rid of return --> void
     */
    public static String showSignUpDialog(String type) {
        JFrame jf = new JFrame("Sign Up");
        GridBagConstraints gbc = new GridBagConstraints(0,0, 2, 1, 0.5, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        Container content = jf.getContentPane();
        content.setLayout(new GridBagLayout());

        JLabel signUpLabel = new JLabel("Candy Marketplace Sign Up");
        content.add(signUpLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        content.add(usernameLabel, gbc);

        JTextField usernameTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(usernameTextField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        content.add(passwordLabel, gbc);

        JTextField passwordTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(passwordTextField, gbc);

        // Let's make it so that you can't repeat usernames
        JButton signUpButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                User user = new User(username, password); // Change this later
                if (user.checkAccount("Users.txt")) { // Changes this later
                    System.out.println("Change this later.");
                    // showMarketplaceDialog(new Candy[0]); // Change this later
                } else {
                    JOptionPane.showMessageDialog(null, "Username already taken",
                            "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        signUpButton.setText("Sign Up");
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        content.add(signUpButton, gbc);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

        return "";
    }

    /**
     * For logging in
     * @param type - for type of user
     * @return might get rid of this return --> void
     */
    public static boolean showLoginDialog(String type) {
        JFrame jf = new JFrame("Login");
        GridBagConstraints gbc = new GridBagConstraints(0,0, 2, 1, 0.5, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);
        Container content = jf.getContentPane();
        content.setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel("Candy Marketplace Login");
        content.add(loginLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        content.add(usernameLabel, gbc);

        JTextField usernameTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(usernameTextField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        content.add(passwordLabel, gbc);

        JTextField passwordTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(passwordTextField, gbc);

        JButton loginButton = new JButton(new AbstractAction() {
            int attempts = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                boolean userIsThere = true;
                User user = new User(username, password); // Change this later
                if (user.checkAccount("Users.txt") || true) { // Change this later obviously
                    System.out.println("Change this later.");
                    // showMarketplaceDialog();
                } else {
                    attempts++;
                }
                if (attempts >= 3) {
                    System.out.println("Throw error message that resets program");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginButton.setText("Login");
        content.add(loginButton, new GridBagConstraints(0, 3, 2, 1, 0.5, 0,
                GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE,
                new Insets(5, 0, 10, 10), 0, 0));

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

        return true;
    }
}
