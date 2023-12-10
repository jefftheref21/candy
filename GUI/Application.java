import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Application implements Runnable {
    UserClient client;

    JFrame signUpDialog;
    JFrame loginDialog;

    JLabel userTypeLabel;

    JButton signUpButton;
    JButton loginButton;

    JTextField usernameTextField;
    JTextField passwordTextField;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == signUpButton) {
                if (userTypeLabel.getText().equals("Buyer")) {
                    Buyer buyer = new Buyer(usernameTextField.getText(), passwordTextField.getText());
                    try {
                        client.sendSignUp(buyer);
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                } else {
                    Seller seller = new Seller(usernameTextField.getText(), passwordTextField.getText());
                    try {
                        client.sendSignUp(seller);
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                }
                client.receiveAction();

                if (client.getAction() == Action.VALID_CREDENTIALS_BUYER) {
                    showSuccessfulSignUpDialog();
                    runMarketplace();
                    signUpDialog.dispose();
                } else if (client.getAction() == Action.VALID_CREDENTIALS_SELLER) {
                    showSuccessfulSignUpDialog();
                    runControlCenter();
                    signUpDialog.dispose();
                } else if (client.getAction() == Action.INVALID_CREDENTIALS) {
                    showUnsuccessfulSignUpDialog();
                    showSignUpDialog();
                }
            }
            if (e.getSource() == loginButton) {
                try {
                    client.sendLogin(new User(usernameTextField.getText(), passwordTextField.getText()));
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
                client.receiveAction();

                if (client.getAction() == Action.VALID_CREDENTIALS_BUYER) {
                    showSuccessfulLoginDialog();
                    runMarketplace();
                    loginDialog.dispose();
                } else if (client.getAction() == Action.VALID_CREDENTIALS_SELLER) {
                    showSuccessfulLoginDialog();
                    runControlCenter();
                    loginDialog.dispose();
                } else if (client.getAction() == Action.INVALID_CREDENTIALS) {
                    showUnsuccessfulLoginDialog();
//                    loginDialog.dispose();
//                    showLoginDialog();
                }
            }
        }
    };

    public Application() throws IOException {
        client = new UserClient(this);
    }

    public static void main(String[] args) throws IOException {
        runApplication();
    }

    public static void runApplication() throws IOException {
        SwingUtilities.invokeLater(new Application());
    }

    public void runMarketplace() {
        try {
            SwingUtilities.invokeLater(new Marketplace(client.getSocket()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runControlCenter() {
        try {
             SwingUtilities.invokeLater(new ControlCenter(client.getSocket()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        showWelcomeMessageDialog();
        showStartingDialog();
    }

    /**
     * Displays Welcome Message
     */
    public void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to the Candy Marketplace",
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Dialog that makes user choose whether to sign up or login
     */
    public void showStartingDialog() {
        String[] options = {"Sign Up", "Login"};
        int signUpOrLogin = JOptionPane.showOptionDialog(null, "Select an option:",
                "Sign Up or Login", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (signUpOrLogin == 0) {
            showUserTypeDialog();
            showSignUpDialog();
        } else {
            showLoginDialog();
        }
    }

    /**
     * Dialog that makes user choose their user type
     * @return the type of user
     */
    public void showUserTypeDialog() {
        String[] options = {"Buyer", "Seller"};
        int userType = JOptionPane.showOptionDialog(null, "Select your user type:",
                "User Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        userTypeLabel = new JLabel(options[userType]);
    }

    public void showSuccessfulSignUpDialog() {
        JOptionPane.showMessageDialog(null, "Sign up successful!",
                "Sign Up Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public void showUnsuccessfulSignUpDialog() {
        JOptionPane.showMessageDialog(null, "Sign up failed!",
                "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessfulLoginDialog() {
        JOptionPane.showMessageDialog(null, "Login successful!",
                "Login Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public void showUnsuccessfulLoginDialog() {
        JOptionPane.showMessageDialog(null, "Login failed!",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * For signing up
     * @return might get rid of return --> void
     */
    public void showSignUpDialog() {
        signUpDialog = new JFrame("Sign Up");
        GridBagConstraints gbc = new GridBagConstraints(0,0, 2, 1, 0.5, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        Container content = signUpDialog.getContentPane();
        content.setLayout(new GridBagLayout());

        JLabel signUpLabel = new JLabel("Candy Marketplace Sign Up");
        content.add(signUpLabel, gbc);

        gbc.gridy = 1;
        content.add(userTypeLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        content.add(usernameLabel, gbc);

        usernameTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(usernameTextField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        content.add(passwordLabel, gbc);

        passwordTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(passwordTextField, gbc);

        // Let's make it so that you can't repeat usernames
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(actionListener);

        gbc.gridwidth = 2;
        gbc.gridy = 4;
        content.add(signUpButton, gbc);

        signUpDialog.pack();
        signUpDialog.setLocationRelativeTo(null);
        signUpDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpDialog.setVisible(true);
    }

    /**
     * For logging in
     * @return might get rid of this return --> void
     */
    public void showLoginDialog() {
        loginDialog = new JFrame("Login");
        GridBagConstraints gbc = new GridBagConstraints(0,0, 2, 1, 0.5, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);
        Container content = loginDialog.getContentPane();
        content.setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel("Candy Marketplace Login");
        content.add(loginLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        content.add(usernameLabel, gbc);

        usernameTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(usernameTextField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        content.add(passwordLabel, gbc);

        passwordTextField = new JTextField(8);
        gbc.gridx = 1;
        content.add(passwordTextField, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);

        gbc.gridwidth = 2;
        gbc.gridy = 3;

        content.add(loginButton, gbc);

        loginDialog.pack();
        loginDialog.setLocationRelativeTo(null);
        loginDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginDialog.setVisible(true);
    }
}
