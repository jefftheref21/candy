import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class Marketplace extends JFrame implements Runnable {
    public static final Color buttonColor = new Color(235, 158, 52);
    public static final Color outerColor = new Color(245, 130, 75);
    public static final Color backgroundColor = new Color(235, 83, 52);

    BuyerClient buyerClient;

    JButton sortButton;
    JComboBox sortComboBox;
    String[] sortOptions = {"Price - Least to Greatest", "Price - Greatest to Least",
            "Quantity - Least to Greatest", "Quantity - Greatest to Least"};
    JButton searchButton;

    JButton buyButton;
    JButton addToCartButton;
    JButton removeFromCartButton;

    JButton shoppingCartButton;
    JButton buyShoppingCartButton;
    JButton historyButton;
    JButton exportHistoryButton;
    JButton viewStatisticsButton;

    JTextField searchTextField;
    JTextField quantityToBuyTextField;

    Candy candySelected;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sortButton) {
                // need to figure out how to update marketplace with sorted candy
                System.out.println(sortComboBox.getSelectedIndex());
                int sort = sortComboBox.getSelectedIndex();

                buyerClient.sendSortDecision(sort);
                buyerClient.receiveSortCandies();
                run();
            }
            if (e.getSource() == searchButton) {
                // TODO
                String searchWord = searchTextField.getText();
                buyerClient.sendSearchDecision(searchWord);
            }

            if (e.getSource() == buyButton) {
                try {
                    int quantityToBuy = Integer.parseInt(quantityToBuyTextField.getText());
                    buyerClient.sendCandyProduct(candySelected, "BUY_INSTANTLY", quantityToBuy);
                } catch (NumberFormatException ex) {
                    Messages.showNumberFormatError();
                }
                buyerClient.receiveAction();

                switch (buyerClient.getAction()) {
                    case BUY_SUCCESSFUL:
                        Messages.showSuccessfulPurchase();
                        break;
                    case BUY_QUANTITY_EXCEEDS:
                        Messages.showQuantityExceededError();
                        break;
                    case BUY_QUANTITY_INVALID:
                        Messages.showNumberFormatError();
                        break;
                }

            }

            if (e.getSource() == addToCartButton) {
                try {
                    buyerClient.sendCandyProduct(candySelected, "ADD_TO_CART",
                            Integer.parseInt(quantityToBuyTextField.getText()));
                } catch (NumberFormatException ex) {
                    Messages.showNumberFormatError();
                }
                buyerClient.receiveAction();

                if (buyerClient.getAction() == Action.BUY_QUANTITY_EXCEEDS) {
                    Messages.showQuantityExceededError();
                } else if (buyerClient.getAction() == Action.ADD_TO_CART) {
                    Messages.showAddToCartSuccessful();
                }
            }
            if (e.getSource() == removeFromCartButton) {
                buyerClient.sendRemoveShoppingCart(candySelected, Integer.parseInt(quantityToBuyTextField.getText()));
                buyerClient.receiveAction();

                if (buyerClient.getAction() == Action.REMOVE_FROM_CART) {
                    Messages.showRemoveToCartSuccessful();
                }
            }
            if (e.getSource() == shoppingCartButton) {
                // TODO
                // send to server that we need shopping cart
                // servers sends back shopping cart


                showShoppingCartDialog();
            }
            if (e.getSource() == buyShoppingCartButton) {
                buyerClient.sendBuyShoppingCart();
                buyerClient.receiveAction();

                switch (buyerClient.getAction()) {
                    case BUY_SUCCESSFUL:
                        Messages.showSuccessfulPurchase();
                        break;
                    case BUY_QUANTITY_EXCEEDS:
                        Messages.showQuantityExceededError();
                        break;
                }
            }
            if (e.getSource() == historyButton) {
                // TODO Must be updated from server
                showPurchaseHistoryDialog();
            }
            if (e.getSource() == exportHistoryButton) {
                // TODO buyerClient.sendExportPurchaseHistory()
                // buyerClient.sendExportPurchaseHistory();
                boolean expSuccess = buyerClient.getPurchaseHistory().exportHistoryToFile(buyerClient.getUsername());
                if (expSuccess) {
                    Messages.showExportHistorySuccessful();
                } else {
                    Messages.showExportHistoryUnsuccessful();
                }
            }
            if (e.getSource() == viewStatisticsButton) {
                // TODO Must have data sent from server

                //buyerClient.
            }

        }
    };

    public Marketplace(Socket socket) throws IOException {
        buyerClient = new BuyerClient(socket, this);
    }

    public void run() {
        setTitle("Marketplace");

        Container content = getContentPane();

        content.setLayout(new BorderLayout());

        displayTopPanel(content);

        displayCandyButtons(buyerClient.getCandyManager().candies, content);

        displaySidePanel(content);

        setSize(650, 450); // This can be toggled with, but toggle with relating factors as well
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }



    /**
     * Panel that has a drop-down menu for choosing how to sort the marketplace and a search button
     * with a text field to input the word
     * Only candies related to the search will be displayed, the others will disappear
     * @param content - To add to the larger frame
     */
    public void displayTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(outerColor);
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        sortComboBox = new JComboBox(sortOptions);

        sortButton = new JButton("Sort");
        sortButton.addActionListener(actionListener);
        sortButton.setBackground(buttonColor);

        topPanel.add(sortComboBox, gbc);

        gbc.gridx = 1;
        topPanel.add(sortButton, gbc);

        searchTextField = new JTextField(8);
        searchButton = new JButton("Search");

        searchButton.setBackground(buttonColor);
        searchButton.addActionListener(actionListener);

        gbc.gridx = 2;
        topPanel.add(searchTextField, gbc);

        gbc.gridx = 3;
        topPanel.add(searchButton, gbc);

        content.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Panel that has two buttons - the shopping cart button and the purchase history button
     * Each should take the buyer to their respective dialogs
     * @param content - To add to the larger frame
     */
    public void displaySidePanel(Container content) {
        JPanel sidePanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(20, 10, 20, 10), 0, 0);
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setBackground(outerColor);

        ImageIcon shoppingCartIcon = new ImageIcon("images/shoppingCart.png");
        Image shoppingCartImg = shoppingCartIcon.getImage();
        Image newShoppingCartImg = shoppingCartImg.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        shoppingCartIcon = new ImageIcon(newShoppingCartImg);

        shoppingCartButton = new JButton();
        shoppingCartButton.setBackground(buttonColor);
        shoppingCartButton.setIcon(shoppingCartIcon);
        shoppingCartButton.addActionListener(actionListener);
        shoppingCartButton.setMargin(new Insets(0, 0, 0, 0));

        sidePanel.add(shoppingCartButton, gbc);

        ImageIcon historyIcon = new ImageIcon("images/history.png");
        Image historyImg = historyIcon.getImage();
        Image newHistoryImg = historyImg.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        historyIcon = new ImageIcon(newHistoryImg);

        historyButton = new JButton();
        historyButton.setBackground(buttonColor);
        historyButton.setIcon(historyIcon);
        historyButton.addActionListener(actionListener);
        historyButton.setMargin(new Insets(0, 0, 0, 0));

        gbc.gridy = 1;
        sidePanel.add(historyButton, gbc);

        ImageIcon storeIcon = new ImageIcon("images/shop.png");
        Image storeImg = storeIcon.getImage();
        Image newStoreImg = storeImg.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
        storeIcon = new ImageIcon(newStoreImg);

        viewStatisticsButton = new JButton();
        viewStatisticsButton.setBackground(buttonColor);;
        viewStatisticsButton.setIcon(storeIcon);
        viewStatisticsButton.addActionListener(actionListener);
        viewStatisticsButton.setMargin(new Insets(0, 0, 0, 0));

        gbc.gridy = 2;
        sidePanel.add(viewStatisticsButton, gbc);

        content.add(sidePanel, BorderLayout.EAST);
    }

    /**
     * Displays the buttons of all the individual candies on the market
     * When clicked on, the user will be taken to the product page, where they can buy, add to their
     * shopping cart, or go back
     * @param candies - all the candies *WILL BE CHANGED TO CANDYMANAGER
     * @param content - To add to the larger frame
     */
    public void displayCandyButtons(ArrayList<Candy> candies, Container content) {
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        jp.setBackground(backgroundColor);

        for (int i = 0; i < candies.size(); i++) { // Change candies to CandyManager later on
            Candy currCandy = candies.get(i);
            JButton currButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    candySelected = currCandy;
                    showCandyPageDialog(currCandy);
                }
            });

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
    public void showCandyPageDialog(Candy currCandy) {
        JFrame jf = new JFrame("Candy Page");
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(outerColor);

        JLabel nameLabel = new JLabel(currCandy.getName());
        panel.add(nameLabel, gbc);

        JLabel descriptionLabel = new JLabel("Product Description: " + currCandy.getDescription());
        gbc.gridy = 1;
        panel.add(descriptionLabel, gbc);

        JLabel quantityLabel = new JLabel("Product Quantity: " + currCandy.getQuantity());
        gbc.gridy = 2;
        panel.add(quantityLabel, gbc);

        JLabel priceLabel = new JLabel("Product Price: " + currCandy.getPrice());
        gbc.gridy = 3;
        panel.add(priceLabel, gbc);

        buyButton = new JButton("Buy");
        buyButton.addActionListener(actionListener);
        buyButton.setBackground(buttonColor);

        gbc.gridy = 4;
        panel.add(buyButton, gbc);

        JLabel quantityToBuyLabel = new JLabel("Quantity to Buy: ");

        gbc.gridx = 1;
        panel.add(quantityToBuyLabel, gbc);

        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(actionListener);
        addToCartButton.setBackground(buttonColor);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(addToCartButton, gbc);

        quantityToBuyTextField = new JTextField(8);

        gbc.gridx = 1;
        panel.add(quantityToBuyTextField, gbc);

        JButton exitButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
        exitButton.setText("Exit");
        exitButton.setBackground(buttonColor);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(exitButton, gbc);

        jf.add(panel);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showShoppingCartDialog() {
        JFrame jf = new JFrame("Shopping Cart");
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        Container content = jf.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(outerColor);
        JLabel shoppingCartLabel = new JLabel("Shopping Cart");
        titlePanel.add(shoppingCartLabel);

        JPanel shoppingCartInfo = new JPanel();
        shoppingCartInfo.setBackground(outerColor);
        shoppingCartInfo.setLayout(new GridBagLayout());

        JLabel candyIDLabel = new JLabel("Candy ID");
        JLabel storeNameLabel = new JLabel("Store");
        JLabel candyNameLabel = new JLabel("Candy Name");
        JLabel quantityLabel = new JLabel("Quantity");

        shoppingCartInfo.add(candyIDLabel, gbc);
        gbc.gridx = 1;
        shoppingCartInfo.add(storeNameLabel, gbc);
        gbc.gridx = 2;
        shoppingCartInfo.add(candyNameLabel, gbc);
        gbc.gridx = 3;
        shoppingCartInfo.add(quantityLabel, gbc);

        ShoppingCart shoppingCart = buyerClient.getShoppingCart();
        shoppingCart = new ShoppingCart();
        Candy candy1 = new Candy("Snickers", new Store("Walmart"), "Chocolate bar", 1, 50, 1.00);
        Purchase purchase = new Purchase(candy1, 10);
        shoppingCart.addItem(purchase);

        for (int i = 0; i < shoppingCart.getPurchases().size(); i++) {
            JLabel idLabel = new JLabel(shoppingCart.getPurchases().get(i).getCandyBought().getCandyID() + "");
            JLabel storeLabel = new JLabel(shoppingCart.getPurchases().get(i).getCandyBought().getStore().getName());
            JLabel nameLabel = new JLabel(shoppingCart.getPurchases().get(i).getCandyBought().getName());
            JLabel quantityBoughtLabel = new JLabel(shoppingCart.getPurchases().get(i).getQuantityBought() + "");
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            shoppingCartInfo.add(idLabel, gbc);
            gbc.gridx = 1;
            shoppingCartInfo.add(storeLabel, gbc);
            gbc.gridx = 2;
            shoppingCartInfo.add(nameLabel, gbc);
            gbc.gridx = 3;
            shoppingCartInfo.add(quantityBoughtLabel, gbc);
        }

        buyShoppingCartButton = new JButton("Buy All");
        buyShoppingCartButton.setBackground(buttonColor);
        buyShoppingCartButton.addActionListener(actionListener);

        gbc.gridy++;
        shoppingCartInfo.add(buyShoppingCartButton, gbc);

        jf.add(titlePanel, BorderLayout.NORTH);
        jf.add(shoppingCartInfo, BorderLayout.CENTER);

        jf.setSize(400, 400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showPurchaseHistoryDialog() {
        JFrame jf = new JFrame("Purchase History");
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        Container content = jf.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(outerColor);

        JLabel purchaseHistoryLabel = new JLabel("Purchase History");
        titlePanel.add(purchaseHistoryLabel);

        JPanel purchaseHistoryInfo = new JPanel();
        purchaseHistoryInfo.setBackground(outerColor);
        purchaseHistoryInfo.setLayout(new GridBagLayout());

        JLabel candyIDLabel = new JLabel("Candy ID");
        JLabel storeNameLabel = new JLabel("Store");
        JLabel candyNameLabel = new JLabel("Candy Name");
        JLabel quantityLabel = new JLabel("Quantity");

        purchaseHistoryInfo.add(candyIDLabel, gbc);
        gbc.gridx = 1;
        purchaseHistoryInfo.add(storeNameLabel, gbc);
        gbc.gridx = 2;
        purchaseHistoryInfo.add(candyNameLabel, gbc);
        gbc.gridx = 3;
        purchaseHistoryInfo.add(quantityLabel, gbc);

        PurchaseHistory purchaseHistory = buyerClient.getPurchaseHistory();

        for (int i = 0; i < purchaseHistory.getPurchases().size(); i++) {
            JLabel idLabel = new JLabel(purchaseHistory.getPurchases().get(i).getCandyBought().getCandyID() + "");
            JLabel storeLabel = new JLabel(purchaseHistory.getPurchases().get(i).getCandyBought().getStore().getName());
            JLabel nameLabel = new JLabel(purchaseHistory.getPurchases().get(i).getCandyBought().getName());
            JLabel quantityBoughtLabel = new JLabel(purchaseHistory.getPurchases().get(i).getQuantityBought() + "");
            gbc.gridy = i + 1;
            gbc.gridx = 0;
            purchaseHistoryInfo.add(idLabel, gbc);
            gbc.gridx = 1;
            purchaseHistoryInfo.add(storeLabel, gbc);
            gbc.gridx = 2;
            purchaseHistoryInfo.add(nameLabel, gbc);
            gbc.gridx = 3;
            purchaseHistoryInfo.add(quantityBoughtLabel, gbc);
        }

        JPanel exportPanel = new JPanel();
        exportPanel.setBackground(outerColor);
        exportHistoryButton = new JButton("Export Purchase History");
        exportHistoryButton.setBackground(buttonColor);
        exportHistoryButton.addActionListener(actionListener);

        exportPanel.add(exportHistoryButton);

        content.add(titlePanel, BorderLayout.NORTH);
        content.add(purchaseHistoryInfo, BorderLayout.CENTER);
        content.add(exportPanel, BorderLayout.SOUTH);

        jf.setSize(400, 400);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }


}
