import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ControlCenter extends JFrame implements Runnable {
    public static final Color buttonColor = new Color(235, 158, 52);
    public static final Color outerColor = new Color(245, 130, 75);
    public static final Color backgroundColor = new Color(235, 83, 52);

    SellerClient sellerClient;

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
            }
            if (e.getSource() == addCandyButton) {
                sellerClient.sendToGetCandyID();

                int candyID = sellerClient.receiveCandyID();

                String name = nameTextField.getText();
                String storeName = storeNameTextField.getText();
                String description = descriptionTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());

                Candy newCandy = new Candy(name, new Store(storeName), description, candyID, quantity, price);

                sellerClient.sendAddCandy(newCandy);
            }

            if (e.getSource() == saveCandyButton) {
                candySelected.setName(nameTextField.getText());
                candySelected.setPrice(Double.parseDouble(priceTextField.getText()));
                candySelected.setQuantity(Integer.parseInt(quantityTextField.getText()));
                candySelected.setDescription(descriptionTextField.getText());

                sellerClient.sendEditCandy(candySelected);

                sellerClient.receiveAction();

                if (sellerClient.getAction() == Action.EDIT_CANDY_SUCCESSFUL) {
                    Messages.showSuccessfulEditCandyDialog();
                } else if (sellerClient.getAction() == Action.EDIT_CANDY_UNSUCCESSFUL) {
                    Messages.showUnsuccessfulEditCandyDialog();
                }
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

                sellerClient.receiveUpdatedSales();

                viewSalesInformationDialog(storeSelected);
            }

            if (e.getSource() == viewStoreStatisticsButton) {
                sellerClient.sendViewStoreStatistics(storeSelected);

                sellerClient.receiveStoreStatistics();

                showStoreStatisticsDialog();
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
                // sellerClient.sendCustomerShoppingCarts();
            }
        }
    };
    public ControlCenter(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        sellerClient = new SellerClient(socket, in, out, this);
    }

    public void run() {
        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        displayTopPanel(content);

        displayBottomPanel(content);

        Store[] stores = {new Store("Store1"), new Store("Store2"), new Store("Store3")};
        displayStoreButtons(stores, content);

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

        JTextField searchTextField = new JTextField(8);
        JButton searchButton = new JButton("Search");

        topPanel.add(createStoreDisplayButton, gbc);

        gbc.gridx = 1;
        topPanel.add(addCandyDisplayButton, gbc);

        gbc.gridx = 2;
        topPanel.add(searchTextField, gbc);

        gbc.gridx = 3;
        topPanel.add(searchButton, gbc);

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
        customerShoppingCartsButton = new JButton("View Customer Shopping Carts");

        importButton.setBackground(buttonColor);
        exportButton.setBackground(buttonColor);
        customerShoppingCartsButton.setBackground(buttonColor);

        importButton.addActionListener(actionListener);

        exportButton.addActionListener(actionListener);

        bottomPanel.add(importButton, gbc);

        gbc.gridx = 1;
        bottomPanel.add(exportButton, gbc);

        gbc.gridx = 2;
        bottomPanel.add(customerShoppingCartsButton, gbc);

        content.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void displayStoreButtons(Store[] stores, Container content) {
        JPanel storePanel = new JPanel();
        storePanel.setLayout(new GridBagLayout());
        storePanel.setBackground(backgroundColor);
        content.add(new JScrollPane(storePanel), BorderLayout.CENTER);

        for (int i = 0; i < stores.length; i++) {
            Store currStore = stores[i];
            GridBagConstraints gbc = new GridBagConstraints(i % 4, i / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5);

            JButton storeButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    storeSelected = currStore;
                    showStoreOptionDialog(currStore);
                }
            });

            storeButton.setBackground(buttonColor);
            storeButton.setPreferredSize(new Dimension(100, 100));
            storeButton.setHorizontalAlignment(SwingConstants.CENTER);
            storeButton.setText(currStore.getName());
            storePanel.add(storeButton, gbc);
        }
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

        viewProductsButton.setBackground(buttonColor);
        viewSalesButton.setBackground(buttonColor);
        viewStoreStatisticsButton.setBackground(buttonColor);

        viewProductsButton.addActionListener(actionListener);
        viewSalesButton.addActionListener(actionListener);
        viewStoreStatisticsButton.addActionListener(actionListener);

        content.add(viewProductsButton, gbc);

        gbc.gridy = 1;
        content.add(viewSalesButton, gbc);

        gbc.gridy = 2;
        content.add(viewStoreStatisticsButton, gbc);

        storeOptionFrame.pack();
        storeOptionFrame.setLocationRelativeTo(null);
        storeOptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        storeOptionFrame.setVisible(true);
    }

    public void displayCandyButtons(Store store) {
        // Dummy data for candies
        Candy candy1 = new Candy("Chocolate Bar", store, "Delicious chocolate", 1, 50, 1.00);
        Candy candy2 = new Candy("Sour Candy", store, "Tasty sour candy", 2, 25, 2.00);
        Candy[] candies = {candy1, candy2};

        JFrame candyFrame = new JFrame("Candies in " + store.getName());
        Container candyContent = candyFrame.getContentPane();

        JPanel candyPanel = new JPanel();
        candyPanel.setLayout(new GridBagLayout());

        for (int i = 0; i < candies.length; i++) {
            Candy currCandy = candies[i];
            GridBagConstraints gbc = new GridBagConstraints(i % 2, i / 2, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5);

            JButton candyButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    candySelected = currCandy;
                    showEditCandyDialog(store, currCandy);
                }
            });

            candyButton.setBackground(buttonColor);
            candyButton.setPreferredSize(new Dimension(100, 100));
            candyButton.setHorizontalAlignment(SwingConstants.CENTER);
            candyButton.setText(currCandy.getName());
            candyPanel.add(candyButton, gbc);
        }

        candyContent.add(new JScrollPane(candyPanel));
        candyFrame.pack();
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

        deleteCandyButton = new JButton(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteCandyConfirmation(store, currCandy.getName());
                jf.dispose();  // Close the edit dialog after deletion
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


    public void viewSalesInformationDialog(Store store) {
        JFrame jf = new JFrame("Sales Information");
        // Add logic to display sales information for the store
        JLabel salesLabel = new JLabel("Sales information for " + store.getName());
        jf.add(salesLabel);

        jf.setSize(300, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public void showStoreStatisticsDialog() {
        JFrame jf = new JFrame("Store Statistics");
        // Add logic to display store statistics
        JLabel statisticsLabel = new JLabel("Store statistics");
        jf.add(statisticsLabel);
        // TODO: Add logic to display store statistics

        jf.setSize(300, 300);
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
}