import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ControlCenter extends JFrame implements Runnable {
    public static final Color buttonColor = new Color(235, 158, 52);
    public static final Color outerColor = new Color(245, 130, 75);
    public static final Color backgroundColor = new Color(235, 83, 52);

    SellerClient sellerClient;
    JPanel storePanel;
    Container mainContent;

    JButton viewProductsButton;
    JButton viewSalesButton;
    JButton viewStoreStatisticsButton;

    JButton createStoreButton;

    JButton addCandyButton;
    JButton saveCandyButton; // For saving the edits to candies
    JButton deleteCandyButton;

    JButton importButton;
    JButton exportButton;
    JButton customerShoppingCartsButton;

    JButton editStoreButton;

    JTextField createStoreNameTextField;

    JTextField nameTextField;
    JTextField priceTextField;
    JTextField storeNameTextField;
    JTextField quantityTextField;
    JTextField descriptionTextField;

    JTextField newNameTextField;
    JTextField newPriceTextField;
    JTextField newStoreNameTextField;
    JTextField newQuantityTextField;
    JTextField newDescriptionTextField;

    Store storeSelected;

    Candy candySelected;

    JButton sortButton;
    JComboBox sortComboBox;
    String[] sortOptions = {"Price - Least to Greatest", "Price - Greatest to Least",
            "Quantity - Least to Greatest", "Quantity - Greatest to Least"};

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == createStoreButton) {
                String storeName = createStoreNameTextField.getText();
                Store newStore = new Store(storeName);

                sellerClient.sendCreateStore(newStore);

                sellerClient.receiveAction();

                if (sellerClient.getAction() == Action.CREATE_STORE_SUCCESSFUL) {
                    Messages.showSuccessfulCreateStoreDialog();
                } else if (sellerClient.getAction() == Action.CREATE_STORE_UNSUCCESSFUL) {
                    Messages.showUnsuccessfulCreateStoreDialog();
                }
                updateScreen();
            }
            if (e.getSource() == addCandyButton) {
                sellerClient.sendToGetCandyID();

                int candyID = sellerClient.receiveCandyID();

                String name = nameTextField.getText();
                String storeName = storeNameTextField.getText();
                String description = descriptionTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());

                Candy newCandy = new Candy(name, storeName, description, candyID, quantity, price);

                sellerClient.sendAddCandy(newCandy);
            }

            if (e.getSource() == deleteCandyButton) {
                sellerClient.sendDeleteCandy(candySelected);

                sellerClient.receiveAction();

                if (sellerClient.getAction() == Action.DELETE_CANDY_SUCCESSFUL) {
                    Messages.showSuccessfulDeleteCandyDialog();
                } else if (sellerClient.getAction() == Action.DELETE_CANDY_UNSUCCESSFUL) {
                    Messages.showUnsuccessfulDeleteCandyDialog();
                }
            }

            if (e.getSource() == viewProductsButton) {
                displayCandyButtons(storeSelected);
            }

            if (e.getSource() == viewSalesButton) {
                sellerClient.sendViewSales(storeSelected);
                ArrayList<Sale> updatedSales = sellerClient.receiveUpdatedSales();
                viewSalesInformationDialog(storeSelected, updatedSales);
            }
            if (e.getSource() == saveCandyButton) {
                candySelected.setName(nameTextField.getText());
                candySelected.setPrice(Double.parseDouble(priceTextField.getText()));
                candySelected.setQuantity(Integer.parseInt(quantityTextField.getText()));
                candySelected.setDescription(descriptionTextField.getText());

                sellerClient.sendEditCandy(candySelected);

                sellerClient.receiveAction();

                if (sellerClient.getAction() == Action.EDIT_CANDY_UNSUCCESSFUL) {
                    Messages.showSuccessfulEditCandyDialog();
                } else if (sellerClient.getAction() == Action.EDIT_CANDY_SUCCESSFUL) {
                    Messages.showUnsuccessfulEditCandyDialog();
                }
            }

            if (e.getSource() == viewStoreStatisticsButton) {
                sellerClient.sendViewStoreStatistics(storeSelected);
                sellerClient.receiveStoreStatistics();
                ArrayList<Sale> sales = storeSelected.getSales();

                showStoreStatisticsDialog(storeSelected, sales);
            }

            if (e.getSource() == importButton) {
                String filePath = Messages.getImportPath();
                if (filePath != null && !filePath.isEmpty()) {
                    sellerClient.sendImportCSV(filePath);
                }

                sellerClient.receiveAction();

                if (sellerClient.getAction() == Action.IMPORT_SUCCESSFUL) {
                    Messages.showSuccessfulImportDialog();
                } else if (sellerClient.getAction() == Action.IMPORT_UNSUCCESSFUL) {
                    Messages.showUnsuccessfulImportDialog();
                }
            }

            if (e.getSource() == exportButton) {
                String filePath = Messages.getExportPath();
                if (filePath != null && !filePath.isEmpty()) {
                    sellerClient.sendExportCSV(filePath);
                }

                sellerClient.receiveAction();

                if (sellerClient.getAction() == Action.EXPORT_SUCCESSFUL) {
                    Messages.showSuccessfulExportDialog();
                } else if (sellerClient.getAction() == Action.EXPORT_UNSUCCESSFUL) {
                    Messages.showUnsuccessfulExportDialog();
                }
            }

            if (e.getSource() == customerShoppingCartsButton) {
                sellerClient.sendCustomerShoppingCarts(storeSelected);
                ArrayList<ShoppingCart> customerShoppingCarts = sellerClient.receiveCustomerShoppingCarts();

                viewCustomerShoppingCartsDialog(customerShoppingCarts);
            }
        }
    };
    public ControlCenter(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        sellerClient = new SellerClient(socket, in, out, this);
    }

    public void run() {
        mainContent = getContentPane();
        mainContent.setLayout(new BorderLayout());

        displayTopPanel(mainContent);

        displayBottomPanel(mainContent);

        ArrayList<Store> stores = new ArrayList<>();
        stores.add(new Store("Store 1"));
        stores.add(new Store("Store 2"));
        stores.add(new Store("Store 3"));
        displayStoreButtons(stores, mainContent);

        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Should have sort
     * @param content - To add to the larger frame
     */
    public void displayTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(outerColor);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 20, 10, 20), 0, 0);

        JButton createStoreDisplayButton  = new JButton(new AbstractAction("Create Store") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateStoreDialog();
            }
        });
        createStoreDisplayButton.setBackground(buttonColor);

        JButton addCandyDisplayButton = new JButton(new AbstractAction("Add Candy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddCandyDialog();
            }
        });
        addCandyDisplayButton.setBackground(buttonColor);


        topPanel.add(createStoreDisplayButton, gbc);

        gbc.gridx = 1;
        topPanel.add(addCandyDisplayButton, gbc);

        content.add(topPanel, BorderLayout.NORTH);
    }

    public void displayBottomPanel(Container content) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(outerColor);
        bottomPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 20, 10, 20), 0, 0);

        // Import, Export, and Store Statistics buttons
        importButton = new JButton("Import CSV");
        exportButton = new JButton("Export to CSV");


        importButton.setBackground(buttonColor);
        exportButton.setBackground(buttonColor);

        importButton.addActionListener(actionListener);

        exportButton.addActionListener(actionListener);



        bottomPanel.add(importButton, gbc);

        gbc.gridx = 1;
        bottomPanel.add(exportButton, gbc);


        content.add(bottomPanel, BorderLayout.SOUTH);
    }

    public JPanel displayStoreButtons(ArrayList<Store> stores, Container content) {
        if (storePanel == null) {
            storePanel = new JPanel();
        }
        storePanel.setLayout(new GridBagLayout());
        storePanel.setBackground(backgroundColor);
        content.add(new JScrollPane(storePanel), BorderLayout.CENTER);

        for (int i = 0; i < stores.size(); i++) {
            Store currentStore = stores.get(i);

            Candy candy1 = new Candy("Chocolate Bar", currentStore.getName(), "Delicious chocolate",
                    1, 50, 1.00);
            Candy candy2 = new Candy("Sour Candy", currentStore.getName(), "Tasty sour candy",
                    2, 25, 2.00);
            currentStore.addCandy(candy1, new CandyManager());
            currentStore.addCandy(candy2, new CandyManager());

            JButton storeButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    storeSelected = currentStore;
                    showStoreOptionDialog(currentStore);
                }
            });

            storeButton.setBackground(buttonColor);
            storeButton.setPreferredSize(new Dimension(100, 100));
            storeButton.setHorizontalAlignment(SwingConstants.CENTER);
            storeButton.setText(currentStore.getName());
            storePanel.add(storeButton, new GridBagConstraints(i % 4, i / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5));
        }
        return storePanel;
    }

    public void showStoreOptionDialog(Store store) {
        JFrame storeOptionFrame = new JFrame("Store Options");
        Container content = storeOptionFrame.getContentPane();
        content.setLayout(new GridBagLayout());
        content.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        viewProductsButton = new JButton("View Products");
        viewSalesButton = new JButton("View Sales");
        viewStoreStatisticsButton = new JButton("View Store Statistics");
        editStoreButton = new JButton("Edit Store");
        customerShoppingCartsButton = new JButton("View Customer Shopping Carts");


        viewProductsButton.setBackground(buttonColor);
        viewSalesButton.setBackground(buttonColor);
        viewStoreStatisticsButton.setBackground(buttonColor);
        editStoreButton.setBackground(buttonColor);
        customerShoppingCartsButton.setBackground(buttonColor);


        viewProductsButton.addActionListener(actionListener);
        viewSalesButton.addActionListener(actionListener);
        viewStoreStatisticsButton.addActionListener(actionListener);
        editStoreButton.addActionListener(actionListener);
        customerShoppingCartsButton.addActionListener(actionListener);

        content.add(viewProductsButton, gbc);

        gbc.gridy = 1;
        content.add(viewSalesButton, gbc);

        gbc.gridy = 2;
        content.add(viewStoreStatisticsButton, gbc);
        gbc.gridy = 3;
        content.add(editStoreButton, gbc);
        gbc.gridy = 4;
        content.add(customerShoppingCartsButton, gbc);

        storeOptionFrame.setSize(250, 250);
        storeOptionFrame.setLocationRelativeTo(null);
        storeOptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        storeOptionFrame.setVisible(true);
    }

    public void displayCandyButtons(Store store) {
        // Dummy data for candies

        JFrame candyFrame = new JFrame("Candies in " + store.getName());
        Container candyContent = candyFrame.getContentPane();

        JPanel candyPanel = new JPanel();
        candyPanel.setLayout(new GridBagLayout());
        candyPanel.setBackground(backgroundColor);
        int skipped = 0;

        for (int i = 0; i < store.getCandies().size(); i++) {
            Candy currCandy = store.getCandies().get(i);
            if (currCandy.getQuantity() == 0) {
                skipped++;
                continue;
            }
            JButton currButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    candySelected = currCandy;
                    showEditCandyDialog(store, candySelected);
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

        candyContent.add(new JScrollPane(candyPanel));
        candyFrame.setSize(350, 350);
        candyFrame.setLocationRelativeTo(null);
        candyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        candyFrame.setVisible(true);
    }


    public void showEditCandyDialog(Store store, Candy currCandy) {
        JFrame jf = new JFrame("Edit Candy");
        Container content = jf.getContentPane();

        content.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        nameTextField = new JTextField(currCandy.getName());
        priceTextField = new JTextField(String.valueOf(currCandy.getPrice()));
        quantityTextField = new JTextField(String.valueOf(currCandy.getQuantity()));
        descriptionTextField = new JTextField(currCandy.getDescription());

        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        content.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        content.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        content.add(priceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        content.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        content.add(quantityTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        content.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        content.add(descriptionTextField, gbc);

        saveCandyButton = new JButton("Save");
        saveCandyButton.addActionListener(actionListener);
        candySelected = currCandy;

        deleteCandyButton = new JButton(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteCandyButton) {
                    showDeleteCandyConfirmation(store, currCandy.getName());
                    jf.dispose();  // Close the edit dialog after deletion
                }
            }
        });

        Dimension buttonSize = new Dimension(150, 25);
        saveCandyButton.setPreferredSize(buttonSize);
        deleteCandyButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        content.add(saveCandyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        content.add(deleteCandyButton, gbc);

        jf.setSize(300, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showCreateStoreDialog() {
        JFrame jf = new JFrame("Add Store");
        Container content = jf.getContentPane();
        content.setLayout(new GridBagLayout());
        content.setBackground(outerColor);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        JLabel storeNameLabel = new JLabel("Store Name:");
        content.add(storeNameLabel, gbc);

        createStoreNameTextField = new JTextField(12);

        gbc.gridy = 1;
        content.add(createStoreNameTextField, gbc);

        createStoreButton = new JButton("Create Store");
        createStoreButton.addActionListener(actionListener);
        createStoreButton.setBackground(buttonColor);

        gbc.gridy = 2;
        content.add(createStoreButton, gbc);

        jf.setSize(200, 200);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showAddCandyDialog() {
        JFrame jf = new JFrame("Add Candy");
        Container content = jf.getContentPane();
        content.setLayout(new GridBagLayout());
        content.setBackground(outerColor);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0);

        newNameTextField = new JTextField(12);
        newStoreNameTextField = new JTextField(12);
        newDescriptionTextField = new JTextField(12);
        newPriceTextField = new JTextField(12);
        newQuantityTextField = new JTextField(12);

        gbc.gridx = 0;
        content.add(new JLabel("Name: "), gbc);

        gbc.gridx = 1;
        content.add(newNameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        content.add(new JLabel("Store Name: "), gbc);

        gbc.gridx = 1;
        content.add(newStoreNameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        content.add(new JLabel("Description: "), gbc);

        gbc.gridx = 1;
        content.add(newDescriptionTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        content.add(new JLabel("Price: "), gbc);

        gbc.gridx = 1;
        content.add(newPriceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        content.add(new JLabel("Quantity: "), gbc);

        gbc.gridx = 1;
        content.add(newQuantityTextField, gbc);

        addCandyButton = new JButton("Add");
        addCandyButton.addActionListener(actionListener);
        addCandyButton.setBackground(buttonColor);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        content.add(addCandyButton, gbc);

        jf.setSize(300, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }


    public void viewSalesInformationDialog(Store store, ArrayList<Sale> sales) {
        JFrame salesFrame = new JFrame("Sales Information for " + store.getName());
        Container salesContent = salesFrame.getContentPane();

        // Dummy data for sales
        Sale sale1 = new Sale(new Candy("Chocolate Bar", store.getName(), "Delicious chocolate",
                1, 50, 1.00), 5, new Buyer("John Doe", "password1"));
        Sale sale2 = new Sale(new Candy("Sour Candy", store.getName(), "Tasty sour candy",
                2, 25, 2.00), 3, new Buyer("Jane Doe", "password2"));
        sales.add(sale1);
        sales.add(sale2);
//
        String[] columnNames = {"Customer Name", "Revenue"};

        //2D array to hold sales data (temporary):
        Object[][] data = new Object[sales.size()][columnNames.length];
        for (int i = 0; i < sales.size(); i++) {
            data[i][0] = sales.get(i).getBuyerAccount().getUsername();  // Customer name
            data[i][1] = sales.get(i).getTotalRevenue();  // Total revenue
        }

        JTable salesTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(salesTable);

        salesContent.add(scrollPane);

        // Set the size of the sales information dialog to a reasonable size
        salesFrame.setSize(400, 300);
        salesFrame.setLocationRelativeTo(null);
        salesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        salesFrame.setVisible(true);
    }

    public void showStoreStatisticsDialog(Store store, ArrayList<Sale> sales) {
        JFrame jf = new JFrame(store.getName() + " Store Statistics");
        Container content = jf.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BorderLayout());

        String[] customerColumnNames = {"Customer Name", "Number of Items Purchased"};
        Object[][] customerData = new Object[sales.size()][customerColumnNames.length];

        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);
            customerData[i][0] = sale.getBuyerAccount().getUsername();  // Customer name
            customerData[i][1] = sale.getQuantityBought();  // Number of items purchased
        }

        JTable customerTable = new JTable(customerData, customerColumnNames);
        JScrollPane customerScrollPane = new JScrollPane(customerTable);

        customerPanel.add(new JLabel("Customer Statistics"), BorderLayout.NORTH);
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        String[] productColumnNames = {"Product Name", "Number of Sales"};
        Object[][] productData = new Object[sales.size()][productColumnNames.length];

        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);
            productData[i][0] = sale.getCandyBought().getName();
            productData[i][1] = sale.getQuantityBought();
        }

        JTable productTable = new JTable(productData, productColumnNames);
        JScrollPane productScrollPane = new JScrollPane(productTable);

        productPanel.add(new JLabel("Product Statistics"), BorderLayout.NORTH);
        productPanel.add(productScrollPane, BorderLayout.CENTER);

        // Add the customer and product panels to the main content pane
        content.add(customerPanel, BorderLayout.WEST);
        content.add(productPanel, BorderLayout.EAST);

        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout());
        sortButton = new JButton("Sort");
        sortComboBox = new JComboBox<>(sortOptions);
        sortPanel.add(sortComboBox);
        sortPanel.add(sortButton);
        content.add(sortPanel, BorderLayout.SOUTH);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showDeleteCandyConfirmation(Store store, String candyName) {
        int confirmDialogResult = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete " + candyName + " from " + store.getName() + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmDialogResult == JOptionPane.YES_OPTION) {
            // Implement candy deletion logic here
            JOptionPane.showMessageDialog(null, candyName + " was successfully deleted!",
                    "Deletion Successful", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void viewCustomerShoppingCartsDialog(ArrayList<ShoppingCart> sc) {
        JFrame jf = new JFrame("Customer Shopping Carts");
        Container content = jf.getContentPane();
        content.setLayout(new BorderLayout());

        JPanel buyerPanel = new JPanel();
        buyerPanel.setLayout(new BorderLayout());

        String[] buyerColumnNames = {"Buyer Name", "Total Products"};
        ArrayList<Object[]> buyerData = new ArrayList<>();

        for (int i = 0; i < sc.size(); i++) {
            Object[] rowData = {"Buyer " + i, sc.get(i).getPurchases().size()};
            buyerData.add(rowData);
        }

        Object[][] buyerArray = new Object[buyerData.size()][buyerColumnNames.length];
        buyerArray = buyerData.toArray(buyerArray);

        JTable buyerTable = new JTable(buyerArray, buyerColumnNames);
        JScrollPane buyerScrollPane = new JScrollPane(buyerTable);

        buyerPanel.add(new JLabel("Buyer Statistics"), BorderLayout.NORTH);
        buyerPanel.add(buyerScrollPane, BorderLayout.CENTER);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        String[] productColumnNames = {"Candy Name", "Store Name", "Quantity", "Price"};
        ArrayList<Object[]> productData = new ArrayList<>();

        for (ShoppingCart scart : sc) {
            for (Purchase purchase : scart.getPurchases()) {
                Candy candy = purchase.getCandyBought();
                Object[] rowData = {candy.getName(), candy.getStore(), purchase.getQuantityBought(), candy.getPrice()};
                productData.add(rowData);
            }
        }

        Object[][] productArray = new Object[productData.size()][productColumnNames.length];
        productArray = productData.toArray(productArray);

        JTable productTable = new JTable(productArray, productColumnNames);
        JScrollPane productScrollPane = new JScrollPane(productTable);

        productPanel.add(new JLabel("Product Details"), BorderLayout.NORTH);
        productPanel.add(productScrollPane, BorderLayout.CENTER);

        content.add(buyerPanel, BorderLayout.NORTH);
        content.add(productPanel, BorderLayout.CENTER);

        jf.setSize(800, 600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }
    public void updateScreen() {
        getContentPane().remove(storePanel);
        storePanel = displayStoreButtons(sellerClient.getStoreManager().getStores(), mainContent);

        getContentPane().add(storePanel, BorderLayout.CENTER);
        getContentPane().revalidate();
    }
}