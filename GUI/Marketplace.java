import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

public class Marketplace extends JFrame implements Runnable {
    public static final Color buttonColor = new Color(235, 158, 52);
    public static final Color outerColor = new Color(245, 130, 75);
    public static final Color backgroundColor = new Color(235, 83, 52);

    BuyerClient buyerClient;

    JFrame candyPageFrame;
    JFrame shoppingCartFrame;
    JPanel candyPanel;

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
    JButton sortStoreProductsButton;
    JButton sortBuyerProductsButton;

    JTextField searchTextField;
    JTextField quantityToBuyTextField;
    JTextField sortStoreProductsTextField;
    JTextField sortBuyerProductsTextField;
    
    Candy candySelected;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == sortButton) {
                // need to figure out how to update marketplace with sorted candy
                System.out.println(sortComboBox.getSelectedIndex());
                int sort = sortComboBox.getSelectedIndex();

                buyerClient.receiveSortCandies(sort);
                updateScreen();
            }
            if (e.getSource() == searchButton) {
                String searchWord = searchTextField.getText();
                if (searchWord.isEmpty()) {

                    buyerClient.sendCandyManager();
                    buyerClient.receiveCandyManager();
                    updateScreen();
                    return;
                }

                ArrayList<Candy> result = buyerClient.getCandyManager().search(searchWord);

                buyerClient.getCandyManager().setCandies(result);
                if (result.isEmpty()) {
                    Messages.showSearchUnsuccesful();
                } else {
                    updateScreen();
                }
            }

            if (e.getSource() == buyShoppingCartButton) {
                try {
                    buyerClient.sendBuyShoppingCart();
                } catch (NumberFormatException ex) {
                    Messages.showNumberFormatError();
                    return;
                }

                buyerClient.receiveAction();

                buyerClient.sendCandyManager();
                buyerClient.receiveCandyManager();

                switch (buyerClient.getAction()) {
                    case BUY_SUCCESSFUL:
                        buyerClient.sendRemoveShoppingCart(candySelected, candySelected.getQuantity());

                        buyerClient.receiveAction();

                        if (buyerClient.getAction() == Action.REMOVE_FROM_CART_SUCCESSFUL) {
                            buyerClient.sendShoppingCart();
                            buyerClient.receiveShoppingCart();
                            shoppingCartFrame.dispose();
                        }

                        shoppingCartFrame.dispose();
                        updateScreen();
                        Messages.showSuccessfulPurchase();
                        break;
                    case BUY_QUANTITY_EXCEEDS:
                        Messages.showQuantityExceededError();
                        break;
                }
            }

            if (e.getSource() == buyButton) {
                try {
                    int quantityToBuy = Integer.parseInt(quantityToBuyTextField.getText());
                    buyerClient.sendCandyProduct(candySelected, "BUY_INSTANTLY", quantityToBuy);
                } catch (NumberFormatException ex) {
                    Messages.showNumberFormatError();
                    return;
                }

                buyerClient.receiveAction();

                buyerClient.sendCandyManager();
                buyerClient.receiveCandyManager();

                switch (buyerClient.getAction()) {
                    case BUY_SUCCESSFUL: {
                        buyerClient.sendShoppingCart();
                        buyerClient.receiveShoppingCart();

                        candyPageFrame.dispose();

                        updateScreen();
                        Messages.showSuccessfulPurchase();

                        break;
                    }
                    case BUY_QUANTITY_EXCEEDS:
                        Messages.showQuantityExceededError();
                        break;
                    case BUY_QUANTITY_INVALID:
                        Messages.showInvalidQuantityError();
                        break;
                }

            }

            if (e.getSource() == addToCartButton) {
                try {
                    int quantityToBuy = Integer.parseInt(quantityToBuyTextField.getText());
                    buyerClient.sendCandyProduct(candySelected, "ADD_TO_CART", quantityToBuy);
                } catch (NumberFormatException ex) {
                    Messages.showNumberFormatError();
                }

                buyerClient.receiveAction();

                switch(buyerClient.getAction()) {
                    case ADD_TO_CART_SUCCESSFUL:
                        buyerClient.sendShoppingCart();
                        buyerClient.receiveShoppingCart();

                        candyPageFrame.dispose();

                        Messages.showAddToCartSuccessful();
                        break;
                    case ADD_TO_CART_EXCEEDS:
                        Messages.showQuantityExceededError();
                        break;
                    case ADD_TO_CART_INVALID:
                        Messages.showInvalidQuantityError();
                        break;
                }
            }

            if (e.getSource() == removeFromCartButton) {
                buyerClient.sendRemoveShoppingCart(candySelected, Integer.parseInt(quantityToBuyTextField.getText()));
                buyerClient.receiveAction();

                if (buyerClient.getAction() == Action.REMOVE_FROM_CART_SUCCESSFUL) {
                    buyerClient.sendShoppingCart();
                    buyerClient.receiveShoppingCart();
                    shoppingCartFrame.dispose();
                    Messages.showRemoveToCartSuccessful();
                }
            }

            if (e.getSource() == shoppingCartButton) {
                buyerClient.sendShoppingCart();
                buyerClient.receiveShoppingCart();

                showShoppingCartDialog(buyerClient.getShoppingCart());
            }

            if (e.getSource() == historyButton) {
                buyerClient.sendHistory();
                buyerClient.receivePurchaseHistory();

                showPurchaseHistoryDialog(buyerClient.getPurchaseHistory());
            }

            if (e.getSource() == exportHistoryButton) {
                String filePath = Messages.getExportPath();
                buyerClient.sendExportPurchaseHistory(filePath);

                buyerClient.receiveAction();

                if (buyerClient.getAction() == Action.EXPORT_HISTORY_SUCCESSFUL) {
                    Messages.showExportHistorySuccessful();
                } else if (buyerClient.getAction() == Action.EXPORT_HISTORY_UNSUCCESSFUL){
                    Messages.showExportHistoryUnsuccessful();
                }
            }

            if (e.getSource() == viewStatisticsButton) {
                buyerClient.sendStoreManager();
                buyerClient.receiveStoreManager();

                ArrayList<Store> storesByProducts = buyerClient.getStoreManager().getStores();

                ArrayList<Store> storesByBuyer = buyerClient.getStoreManager().getStoresByBuyer(buyerClient);

                showStoreStatisticsDialog(storesByProducts, storesByBuyer);
            }

            if (e.getSource() == sortStoreProductsButton) {
                int choice = Integer.parseInt(sortStoreProductsTextField.getText());
                buyerClient.sendSortProductStats(choice);
                buyerClient.receiveStoreManager();

                ArrayList<Store> storesByProducts = buyerClient.getStoreManager().getStores();

                ArrayList<Store> storesByBuyer = buyerClient.getStoreManager().getStoresByBuyer(buyerClient);

                showStoreStatisticsDialog(storesByProducts, storesByBuyer);
            }

            if (e.getSource() == sortBuyerProductsButton) {
                int choice = Integer.parseInt(sortBuyerProductsTextField.getText());
                buyerClient.sendSortBuyerStats(choice);
                buyerClient.receiveStoreManager();

                ArrayList<Store> storesByProducts = buyerClient.getStoreManager().getStores();

                ArrayList<Store> storesByBuyer = buyerClient.getStoreManager().getStoresByBuyer(buyerClient);

                showStoreStatisticsDialog(storesByProducts, storesByBuyer);
            }
        }
    };

    public Marketplace(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        buyerClient = new BuyerClient(socket, in, out,this);
    }

    public void run() {
        buyerClient.sendCandyManager();
        buyerClient.receiveCandyManager();

        setUpUI();
    }

    public void setUpUI() {
        setTitle("Marketplace");

        Container content = getContentPane();

        content.setLayout(new BorderLayout());

        displayTopPanel(content);

        candyPanel = displayCandyButtons(buyerClient.getCandyManager().candies);

        content.add(candyPanel, BorderLayout.CENTER);

        displaySidePanel(content);

        setSize(650, 450); // This can be toggled with, but toggle with relating factors as well
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void updateScreen() {
        getContentPane().remove(candyPanel);

        candyPanel = displayCandyButtons(buyerClient.getCandyManager().candies);

        getContentPane().add(candyPanel, BorderLayout.CENTER);
        getContentPane().revalidate();
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
     */
    public JPanel displayCandyButtons(ArrayList<Candy> candies) {
        JPanel candyPanel = new JPanel();
        candyPanel.setLayout(new GridBagLayout());
        candyPanel.setBackground(backgroundColor);
        int skipped = 0;

        for (int i = 0; i < candies.size(); i++) {
            Candy currCandy = candies.get(i);
            if (currCandy.getQuantity() == 0) {
                skipped++;
                continue;
            }
            JButton currButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    candySelected = currCandy;
                    showCandyPageDialog();
                }
            });

            currButton.setBackground(buttonColor);
            currButton.setPreferredSize(new Dimension(100, 100));
            currButton.setHorizontalAlignment(SwingConstants.CENTER);
            String buttonText = currCandy.getStore() + "\n" + currCandy.getName() + "\n$"
                    + currCandy.getPrice() + "\n" + currCandy.getQuantity();
            currButton.setText("<html>" + buttonText.replaceAll("\\n", "<br>") + "</html>");
            System.out.println(currCandy.getName());

            candyPanel.add(currButton, new GridBagConstraints((i - skipped) % 4, (i - skipped) / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5));
        }

        return candyPanel;
    }

    /**
     * Dialog showing the candy and all of its attributes
     */
    public void showCandyPageDialog() {
        candyPageFrame = new JFrame("Candy Page");
        Container content = candyPageFrame.getContentPane();

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        content.setLayout(new GridBagLayout());
        content.setBackground(outerColor);

        JLabel nameLabel = new JLabel(candySelected.getName());
        content.add(nameLabel, gbc);

        JLabel descriptionLabel = new JLabel("Candy Description: " + candySelected.getDescription());
        gbc.gridy = 1;
        content.add(descriptionLabel, gbc);

        JLabel quantityLabel = new JLabel("Candy Quantity: " + candySelected.getQuantity());
        gbc.gridy = 2;
        content.add(quantityLabel, gbc);

        JLabel priceLabel = new JLabel("Candy Price: " + candySelected.getPrice());
        gbc.gridy = 3;
        content.add(priceLabel, gbc);

        buyButton = new JButton("Buy");
        buyButton.addActionListener(actionListener);
        buyButton.setBackground(buttonColor);

        gbc.gridy = 4;
        content.add(buyButton, gbc);

        JLabel quantityToBuyLabel = new JLabel("Quantity to Buy: ");

        gbc.gridx = 1;
        content.add(quantityToBuyLabel, gbc);

        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(actionListener);
        addToCartButton.setBackground(buttonColor);

        gbc.gridx = 0;
        gbc.gridy = 5;
        content.add(addToCartButton, gbc);

        quantityToBuyTextField = new JTextField(8);

        gbc.gridx = 1;
        content.add(quantityToBuyTextField, gbc);

        JButton exitButton = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                candyPageFrame.dispose();
            }
        });
        exitButton.setText("Exit");
        exitButton.setBackground(buttonColor);

        gbc.gridx = 0;
        gbc.gridy = 6;
        content.add(exitButton, gbc);

        candyPageFrame.pack();
        candyPageFrame.setLocationRelativeTo(null);
        candyPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        candyPageFrame.setVisible(true);
    }

    public void showShoppingCartDialog(ShoppingCart shoppingCart) {
        shoppingCartFrame = new JFrame("Shopping Cart");
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        Container content = shoppingCartFrame.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(outerColor);
        JLabel shoppingCartLabel = new JLabel("Shopping Cart");
        titlePanel.add(shoppingCartLabel);

        JPanel shoppingCartInfo = new JPanel();
        shoppingCartInfo.setBackground(outerColor);

        String[] columnNames = {"Candy ID", "Store", "Candy Name", "Quantity Bought"};

        Object[][] data = new Object[shoppingCart.getPurchases().size()][columnNames.length];
        for (int i = 0; i < shoppingCart.getPurchases().size(); i++) {
            Candy currCandy = shoppingCart.getPurchases().get(i).getCandyBought();
            data[i][0] = currCandy.getCandyID();  // Candy ID
            data[i][1] = currCandy.getStore();  // Store name
            data[i][2] = currCandy.getName();  // Candy name
            data[i][3] = shoppingCart.getPurchases().get(i).getQuantityBought();  // Quantity bought
        }

        JTable shoppingCartTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(shoppingCartTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        shoppingCartInfo.add(scrollPane);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(outerColor);
        bottomPanel.setLayout(new GridBagLayout());

        removeFromCartButton = new JButton("Remove from Cart");
        removeFromCartButton.setBackground(buttonColor);
        removeFromCartButton.addActionListener(actionListener);

        buyShoppingCartButton = new JButton("Buy All");
        buyShoppingCartButton.setBackground(buttonColor);
        buyShoppingCartButton.addActionListener(actionListener);

        bottomPanel.add(removeFromCartButton, gbc);

        gbc.gridx = 1;
        bottomPanel.add(buyShoppingCartButton, gbc);

        content.add(titlePanel, BorderLayout.NORTH);
        content.add(shoppingCartInfo, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);

        shoppingCartFrame.add(titlePanel, BorderLayout.NORTH);
        shoppingCartFrame.add(shoppingCartInfo, BorderLayout.CENTER);

        shoppingCartFrame.pack();
        shoppingCartFrame.setLocationRelativeTo(null);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartFrame.setVisible(true);
    }

    public void showPurchaseHistoryDialog(PurchaseHistory purchaseHistory) {
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

        String[] columnNames = {"Candy ID", "Store", "Candy Name", "Quantity Bought"};

        Object[][] data = new Object[purchaseHistory.getPurchases().size()][columnNames.length];
        for (int i = 0; i < purchaseHistory.getPurchases().size(); i++) {
            Candy currCandy = purchaseHistory.getPurchases().get(i).getCandyBought();
            data[i][0] = currCandy.getCandyID();  // Candy ID
            data[i][1] = currCandy.getStore();  // Store name
            data[i][2] = currCandy.getName();  // Candy name
            data[i][3] = purchaseHistory.getPurchases().get(i).getQuantityBought();  // Quantity bought
        }

        JTable historyTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(historyTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        purchaseHistoryInfo.add(scrollPane);

        JPanel exportPanel = new JPanel();
        exportPanel.setBackground(outerColor);

        exportHistoryButton = new JButton("Export Purchase History");
        exportHistoryButton.setBackground(buttonColor);
        exportHistoryButton.addActionListener(actionListener);

        exportPanel.add(exportHistoryButton);

        content.add(titlePanel, BorderLayout.NORTH);
        content.add(purchaseHistoryInfo, BorderLayout.CENTER);
        content.add(exportPanel, BorderLayout.SOUTH);

        jf.pack();;
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showStoreStatisticsDialog(ArrayList<Store> storesByProducts, ArrayList<Store> storesByBuyer) {
        JFrame jf = new JFrame("Store Statistics");
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        Container content = jf.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(outerColor);
        JLabel storeStatisticsLabel = new JLabel("Store Statistics");
        titlePanel.add(storeStatisticsLabel);

        JPanel storeStatisticsPanel = new JPanel();
        storeStatisticsPanel.setLayout(new FlowLayout());

        JPanel storeProductsPanel = new JPanel();
        storeProductsPanel.setBackground(outerColor);
        storeProductsPanel.setLayout(new BorderLayout());

        JLabel statisticsInfo = new JLabel("Stores - Products Sold");
        storeProductsPanel.add(statisticsInfo, BorderLayout.NORTH);

        String[] storeProducts = {"Store Name", "Number of Products Sold"};
        Object[][] storeProductsData = new Object[storesByProducts.size()][storeProducts.length];

        for (int i = 0; i < storesByProducts.size(); i++) {
            Store store = storesByProducts.get(i);
            storeProductsData[i][0] = store.getName();  // Store name
            storeProductsData[i][1] = store.getNumberOfProductsSold();  // Number of products sold
        }

        JTable storeProductsTable = new JTable(storeProductsData, storeProducts);
        JScrollPane storeScrollPane = new JScrollPane(storeProductsTable);

        storeProductsPanel.add(storeScrollPane, BorderLayout.CENTER);

        JPanel leftBottomPanel = new JPanel();
        leftBottomPanel.setLayout(new FlowLayout());

        sortStoreProductsTextField = new JTextField(8);

        sortStoreProductsButton = new JButton("Sort");
        sortStoreProductsButton.setBackground(buttonColor);
        sortStoreProductsButton.addActionListener(actionListener);

        leftBottomPanel.add(sortStoreProductsTextField);
        leftBottomPanel.add(sortStoreProductsButton);

        storeProductsPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        storeStatisticsPanel.add(storeProductsPanel);

        JPanel storeBuyersPanel = new JPanel();
        storeBuyersPanel.setBackground(outerColor);
        storeBuyersPanel.setLayout(new BorderLayout());

        JLabel storeBuyersLabel = new JLabel("Stores - Buyer Products");
        storeBuyersPanel.add(storeBuyersLabel, BorderLayout.NORTH);

        String[] buyerProducts = {"Store Name", "Products Purchased by You"};
        Object[][] buyerProductsData = new Object[storesByBuyer.size()][buyerProducts.length];

        for (int i = 0; i < storesByBuyer.size(); i++) {
            Store store = storesByBuyer.get(i);
            buyerProductsData[i][0] = store.getName();
            buyerProductsData[i][1] = store.getNumberProductsBought(buyerClient);
        }

        JTable buyerProductsTable = new JTable(buyerProductsData, buyerProducts);
        JScrollPane buyerScrollPane = new JScrollPane(buyerProductsTable);

        storeBuyersPanel.add(buyerScrollPane, BorderLayout.CENTER);

        JPanel rightBottomPanel = new JPanel();
        rightBottomPanel.setLayout(new FlowLayout());

        sortBuyerProductsTextField = new JTextField(8);

        sortBuyerProductsButton = new JButton("Sort");
        sortBuyerProductsButton.setBackground(buttonColor);
        sortBuyerProductsButton.addActionListener(actionListener);

        rightBottomPanel.add(sortBuyerProductsTextField);
        rightBottomPanel.add(sortBuyerProductsButton);

        storeBuyersPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        storeStatisticsPanel.add(storeBuyersPanel);

        content.add(titlePanel, BorderLayout.NORTH);
        content.add(storeStatisticsPanel, BorderLayout.CENTER);

        jf.pack();;
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }
}
