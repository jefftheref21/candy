import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Application implements Runnable {
    /*
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ) {

            }
        }
    }; */
    public Application() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    public void run() {
        Candy candy1 = new Candy("Snickers", new Store("Walmart"), "Chocolate bar", 1, 50, 1.00);
        Candy candy2 = new Candy("Twix", new Store("Walmart"), "Chocolate bar",2, 25, 2.00);
        Candy candy3 = new Candy("M&Ms", new Store("Walmart"), "Chocolate bar", 3, 100, 3.00);
        Candy[] candies = new Candy[3];
        candies[0] = candy1;
        candies[1] = candy2;
        candies[2] = candy3;
        // showWelcomeMessageDialog();
        // showStartingDialog();
        showMarketplaceDialog(candies);
    }

    public static void showWelcomeMessageDialog() {
        JOptionPane.showMessageDialog(null, "Welcome to the Candy Marketplace",
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }

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

    public static String showUserTypeDialog() {
        String[] options = {"Buyer", "Seller"};
        int userType = JOptionPane.showOptionDialog(null, "Select your user type:",
                "User Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        return options[userType];
    }

    public static String showSignUpDialog(String type) {
        JFrame jf = new JFrame("Sign Up");

        Container content = jf.getContentPane();
        content.setLayout(new GridBagLayout());

        JLabel signUpLabel = new JLabel("Candy Marketplace Sign Up");
        content.add(signUpLabel, new GridBagConstraints(0,0, 2, 1, 0.5, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 5), 5, 5));

        JLabel usernameLabel = new JLabel("Username: ");
        content.add(usernameLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));
        JTextField usernameTextField = new JTextField(8);
        content.add(usernameTextField, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 5, 5));

        JLabel passwordLabel = new JLabel("Password: ");
        content.add(passwordLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));
        JTextField passwordTextField = new JTextField(8);
        content.add(passwordTextField, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 10), 5, 5));

        // Let's make it so that you can't repeat usernames
        JButton signUpButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                User user = new User(username, password); // Change this later
                if (user.checkAccount("Users.txt")) { // Changes this later
                    System.out.println("Change this later.");
                    showMarketplaceDialog(new Candy[0]);
                } else {
                    JOptionPane.showMessageDialog(null, "Username already taken",
                            "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        signUpButton.setText("Sign Up");
        content.add(signUpButton, new GridBagConstraints(0, 3, 2, 1, 0, 0,
                GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 10), 5, 5));

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

        return "";
    }
    public static boolean showLoginDialog(String type) {
        JFrame jf = new JFrame("Login");

        Container content = jf.getContentPane();
        content.setLayout(new GridBagLayout());

        JLabel loginLabel = new JLabel("Candy Marketplace Login");
        content.add(loginLabel, new GridBagConstraints(0,0, 2, 1, 0.5, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 5), 5, 5));

        JLabel usernameLabel = new JLabel("Username: ");
        content.add(usernameLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));
        JTextField usernameTextField = new JTextField(8);
        content.add(usernameTextField, new GridBagConstraints(1, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 5), 5, 5));

        JLabel passwordLabel = new JLabel("Password: ");
        content.add(passwordLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));

        JTextField passwordTextField = new JTextField(8);
        content.add(passwordTextField, new GridBagConstraints(1, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 5, 5, 10), 5, 5));


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

    public static void showMarketplaceDialog(Candy[] candies) {
        JFrame jf = new JFrame("Marketplace");

        Container content = jf.getContentPane();
        for (int i = 0; i < candies.length; i++) {
            Candy currCandy = candies[i];
            JButton currButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCandyPageDialog(currCandy);
                }
            });

            currButton.setText(currCandy.getName() + "\n$" + currCandy.getPrice() +
                    "\n" + currCandy.getQuantity());
            currButton.setBackground(Color.lightGray);
            System.out.println(currCandy.getName());
            content.add(currButton);
            // new GridBagConstraints(i % 3, i / 3, 1, 1, 0, 0,
            //                    GridBagConstraints.LINE_START, GridBagConstraints.NONE,
            //                    new Insets(10, 10, 10, 10), 5, 5)
        }


        jf.setSize(600, 400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);

    }

    public static void showCandyPageDialog(Candy currCandy) {
        JFrame jf = new JFrame("Candy Page");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel storeLabel = new JLabel(currCandy.getStore().getName());
        panel.add(storeLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 5), 5, 5));

        JLabel nameLabel = new JLabel(currCandy.getName());
        panel.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));

        JLabel priceLabel = new JLabel("" + currCandy.getPrice());
        panel.add(priceLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));

        JLabel descriptionLabel = new JLabel(currCandy.getDescription());
        panel.add(descriptionLabel, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));

        JLabel quantityLabel = new JLabel("" + currCandy.getQuantity());
        panel.add(quantityLabel, new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));
        JButton buyButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuyDialog(currCandy);
            }
        });
        buyButton.setText("Buy");
        jf.add(buyButton, new GridBagConstraints(0, 5, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void showBuyDialog(Candy currCandy) {
        JFrame jf = new JFrame("Buy Candy");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel(currCandy.getName());
        panel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 5), 5, 5));

        JLabel priceLabel = new JLabel("" + currCandy.getPrice());
        panel.add(priceLabel, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 5, 5), 5, 5));

        JLabel quantityLabel = new JLabel("" + currCandy.getQuantity());
        panel.add(quantityLabel, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(5, 10, 10, 5), 5, 5));

        JLabel quantityToBuyLabel = new JLabel("Quantity to buy: ");
        panel.add(quantityToBuyLabel, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 5), 5, 5));
        JTextField quantityToBuyTextField = new JTextField(8);
        panel.add(quantityToBuyTextField, new GridBagConstraints(1, 3, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 5, 10, 5), 5, 5));
        /*
        JButton buyButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantityToBuy = Integer.parseInt(quantityToBuyTextField.getText());
                if (quantityToBuy > currCandy.getQuantity()) {
                    JOptionPane.showMessageDialog(null, "Not enough candy in stock",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    currCandy.setQuantity(currCandy.getQuantity() - quantityToBuy);
                    JOptionPane.showMessageDialog(null, "Purchase successful",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        buyButton.setText("Buy");
        panel.add

         */
    }
}
