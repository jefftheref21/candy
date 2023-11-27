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
                    showMarketplaceDialog(new Candy[0]); // Change this later
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

        content.setLayout(new BorderLayout());

        displayTopPanel(content);

        displayCandyButtons(candies, content);

        displaySidePanel(content);

        displayBottomPanel(content);

        jf.setSize(650, 450);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void displayTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());

        JLabel sortLabel = new JLabel("Sort by: ");
        topPanel.add(sortLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 5, 5));

        String[] sortOptions = {"Price - Least to Greatest", "Price - Greatest to Least",
                "Quantity - Least to Greatest", "Quantity - Greatest to Least"};

        JComboBox sortComboBox = new JComboBox(sortOptions);
        topPanel.add(sortComboBox, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 5, 10, 120), 5, 5));

        JTextField searchTextField = new JTextField(8);
        JButton searchButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = searchTextField.getText();
                // Candy[] candies = CandyManager.searchCandy(search);
                // displayCandyButtons(candies, content);
            }
        });
        searchButton.setText("Search");

        topPanel.add(searchTextField, new GridBagConstraints(100, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 5), 5, 5));
        topPanel.add(searchButton, new GridBagConstraints(101, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 5, 10, 10), 5, 5));
        content.add(topPanel, BorderLayout.NORTH);
    }

    public static void displaySidePanel(Container content) {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        ImageIcon shoppingCartIcon = new ImageIcon("images/shoppingCart.png");
        Image shoppingCartImg = shoppingCartIcon.getImage();
        Image newShoppingCartImg = shoppingCartImg.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH ) ;
        shoppingCartIcon = new ImageIcon(newShoppingCartImg);
        JButton shoppingCartButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // showShoppingCart();
            }
        });
        shoppingCartButton.setIcon(shoppingCartIcon);
        shoppingCartButton.setMargin(new Insets(0,0,0,0));

        sidePanel.add(shoppingCartButton, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 50, 10), 5, 5));

        ImageIcon historyIcon = new ImageIcon("images/history.png");
        Image historyImg = historyIcon.getImage();
        Image newHistoryImg = historyImg.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH ) ;
        historyIcon = new ImageIcon(newHistoryImg);
        JButton historyButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // showPurchaseHistory();
            }
        });
        historyButton.setIcon(historyIcon);
        historyButton.setMargin(new Insets(0,0,0,0));

        sidePanel.add(historyButton, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(50, 10, 10, 10), 5, 5));

        content.add(sidePanel, BorderLayout.EAST);
    }

    public static void displayBottomPanel(Container content) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());

        JButton exportHistoryButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // exportHistory();
            }
        });
        exportHistoryButton.setText("Export Purchase History");
        bottomPanel.add(exportHistoryButton, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 50), 5, 5));

        JButton viewStatisticsButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // viewStatistics();
            }
        });
        viewStatisticsButton.setText("View Store Statistics");
        bottomPanel.add(viewStatisticsButton, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 50, 10, 10), 5, 5));

        content.add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void displayCandyButtons(Candy[] candies, Container content) {
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        Color backgroundColor = new Color(235, 83, 52);
        jp.setBackground(backgroundColor);

        for (int i = 0; i < candies.length; i++) { // Change candies to CandyManager later on
            Candy currCandy = candies[i];
            JButton currButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showCandyPageDialog(currCandy);
                }
            });
            Color buttonColor = new Color(235, 158, 52);
            currButton.setBackground(buttonColor);
            currButton.setPreferredSize(new Dimension(100, 100));
            currButton.setHorizontalAlignment(SwingConstants.CENTER);
            String buttonText = currCandy.getStore().getName() + "\n" + currCandy.getName() + "\n$"
                    + currCandy.getPrice() + "\n" + currCandy.getQuantity();
            currButton.setText("<html>" + buttonText.replaceAll("\\n", "<br>") + "</html>");
            System.out.println(currCandy.getName());

            jp.add(currButton, new GridBagConstraints(i % 4, i / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5));

            content.add(jp);
        }
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
