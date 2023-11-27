import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Marketplace {
    public Candy[] candies;
    public static final Color outerColor = new Color(245, 130, 75);
    public Marketplace(Candy[] candies) {
        this.candies = candies;
    }

    public void run() {
        JFrame jf = new JFrame("Marketplace");

        Container content = jf.getContentPane();

        content.setLayout(new BorderLayout());

        displayTopPanel(content);

        displayCandyButtons(candies, content);

        displaySidePanel(content);

        displayBottomPanel(content);

        jf.setSize(650, 450); // This can be toggled with, but toggle with relating factors as well
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    /**
     * Panel that has a drop-down menu for choosing how to sort the marketplace and a search button
     * with a text field to input the word
     * Only candies related to the search will be displayed, the others will disappear
     * @param content - To add to the larger frame
     */
    public static void displayTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(outerColor);

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

    /**
     * Panel that has two buttons - the shopping cart button and the purchase history button
     * Each should take the buyer to their respective dialogs
     * @param content - To add to the larger frame
     */
    public static void displaySidePanel(Container content) {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBackground(outerColor);

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

    /**
     * Panel that has two buttons - one for exporting the purchase history to a file and another
     * for viewing the dashboard for store statistics
     * TODO: When export is successful, a dialog should appear confirming it
     * TODO: The dashboard for store statistics, including the sort buttons
     * @param content - To add to the larger frame
     */
    public static void displayBottomPanel(Container content) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(outerColor);
        GridBagConstraints gbc =  new GridBagConstraints(0, 0, 1, 1, 0.8, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        JButton exportHistoryButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // exportHistory();
            }
        });
        exportHistoryButton.setText("Export Purchase History");
        bottomPanel.add(exportHistoryButton, gbc);

        JButton viewStatisticsButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // viewStatistics();
            }
        });
        viewStatisticsButton.setText("View Store Statistics");
        gbc.gridx = 50;
        bottomPanel.add(viewStatisticsButton, gbc);

        content.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Displays the buttons of all the individual candies on the market
     * When clicked on, the user will be taken to the product page, where they can buy, add to their
     * shopping cart, or go back
     * @param candies - all the candies *WILL BE CHANGED TO CANDYMANAGER
     * @param content - To add to the larger frame
     */
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

    /**
     * Dialog showing the candy and all of its attributes
     * @param currCandy - candy selected by user
     */
    public static void showCandyPageDialog(Candy currCandy) {
        JFrame jf = new JFrame("Candy Page");
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel storeLabel = new JLabel(currCandy.getStore().getName());
        panel.add(storeLabel, gbc);

        JLabel nameLabel = new JLabel(currCandy.getName());
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        JLabel priceLabel = new JLabel("" + currCandy.getPrice());
        gbc.gridy = 2;
        panel.add(priceLabel, gbc);

        JLabel descriptionLabel = new JLabel(currCandy.getDescription());
        gbc.gridy = 3;
        panel.add(descriptionLabel, gbc);

        JLabel quantityLabel = new JLabel("" + currCandy.getQuantity());
        gbc.gridy = 4;
        panel.add(quantityLabel, gbc);
        JButton buyButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuyDialog(currCandy);
            }
        });
        buyButton.setText("Buy");
        gbc.gridy = 5;
        jf.add(buyButton, gbc);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    /**
     * Dialog that asks for the quantity that the user wants to buy
     * Makes sure that quantity requested is not above quantity of product sold
     * @param currCandy - candy selected by user
     */
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
