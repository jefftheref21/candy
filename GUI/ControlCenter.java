import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ControlCenter extends JFrame implements Runnable {
    public static final Color buttonColor = new Color(235, 158, 52);
    public static final Color outerColor = new Color(245, 130, 75);
    public static final Color backgroundColor = new Color(235, 83, 52);

    SellerClient sellerClient;



    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };
    public ControlCenter(Socket socket) throws IOException {
        sellerClient = new SellerClient(socket, this);
    }

    public void run() {
        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        displayTopPanel(content);
        displaySidePanel(content);
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
    public static void displayTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        topPanel.setBackground(Marketplace.outerColor);
        content.add(topPanel, BorderLayout.NORTH);

        JButton addCandyButton = new JButton(new AbstractAction("Add Candy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddCandyDialog();
            }
        });
        topPanel.add(addCandyButton, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(10, 10, 10, 10), 0, 0));

        JTextField searchTextField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchTextField, new GridBagConstraints(1, 0, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 10), 0, 0));
        topPanel.add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0, 0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 10), 0, 0));
    }


    /**
     *
     * @param content - To add to the larger frame
     */
    public static void displaySidePanel(Container content) {
        JPanel sidePanel = new JPanel();
        content.add(sidePanel, BorderLayout.WEST);

        // View candies button
        JButton viewCandiesButton = new JButton("View Candies");
        viewCandiesButton.setBackground(buttonColor);
        viewCandiesButton.setPreferredSize(new Dimension(150, 50));
        sidePanel.add(viewCandiesButton);
    }

    public static void displayBottomPanel(Container content) {
        JPanel bottomPanel = new JPanel();
        content.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBackground(Marketplace.outerColor);

        // Import, Export, and Store Statistics buttons
        JButton importsButton = new JButton("Import");
        JButton exportsButton = new JButton("Export");
        JButton storeStatisticsButton = new JButton("Store Statistics");

        importsButton.setBackground(buttonColor);
        exportsButton.setBackground(buttonColor);
        storeStatisticsButton.setBackground(buttonColor);

        importsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = JOptionPane.showInputDialog("Enter file path for import:");
                if (filePath != null && !filePath.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Import Successful");
                    // Implement import logic here
                }
            }
        });

        exportsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = JOptionPane.showInputDialog("Enter file path for export:");
                if (filePath != null && !filePath.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Export Successful");
                    // Implement export logic here
                }
            }
        });

        bottomPanel.add(importsButton);
        bottomPanel.add(exportsButton);
        bottomPanel.add(storeStatisticsButton);
    }

    public static void displayStoreButtons(Store[] stores, Container content) {
        JPanel storePanel = new JPanel();
        storePanel.setLayout(new GridBagLayout());
        storePanel.setBackground(Color.WHITE);
        content.add(new JScrollPane(storePanel), BorderLayout.CENTER);

        for (int i = 0; i < stores.length; i++) {
            Store currStore = stores[i];
            GridBagConstraints gbc = new GridBagConstraints(i % 4, i / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5);

            JButton storeButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showStoreOptionDialog(currStore);
                }
            });

            storeButton.setBackground(buttonColor);
            storeButton.setPreferredSize(new Dimension(150, 50));
            storeButton.setHorizontalAlignment(SwingConstants.CENTER);
            storeButton.setText(currStore.getName());
            storePanel.add(storeButton, gbc);
        }
    }

    public static void showStoreOptionDialog(Store store) {
        String[] options = {"View Products", "View Sales"};
        int signUpOrLogin = JOptionPane.showOptionDialog(null, "Store: " + store.getName(),
                "Store Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (signUpOrLogin == 0) {
            // View Products
            displayCandyButtons(store);
        } else {
            // View Sales
            viewSalesInformation(store);
        }
    }

    public static void displayCandyButtons(Store store) {
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
                    showEditCandyDialog(store, currCandy);
                }
            });

            candyButton.setBackground(buttonColor);
            candyButton.setPreferredSize(new Dimension(150, 50));
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


    public static void showEditCandyDialog(Store store, Candy currCandy) {
        JFrame jf = new JFrame("Edit Candy");
        Container content = jf.getContentPane();

        content.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JTextField nameTextField = new JTextField(currCandy.getName());
        JTextField priceTextField = new JTextField(String.valueOf(currCandy.getPrice()));
        JTextField quantityTextField = new JTextField(String.valueOf(currCandy.getQuantity()));
        JTextField descriptionTextField = new JTextField(currCandy.getDescription());

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

        JButton saveButton = new JButton(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update candy information with the new values
                currCandy.setName(nameTextField.getText());
                currCandy.setPrice(Double.parseDouble(priceTextField.getText()));
                currCandy.setQuantity(Integer.parseInt(quantityTextField.getText()));
                currCandy.setDescription(descriptionTextField.getText());

                jf.dispose();
            }
        });

        JButton deleteButton = new JButton(new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteCandyConfirmation(store, currCandy.getName());
                jf.dispose();  // Close the edit dialog after deletion
            }
        });

        Dimension buttonSize = new Dimension(150, 25);
        saveButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        content.add(saveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        content.add(deleteButton, gbc);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void showAddCandyDialog() {
        JFrame jf = new JFrame("Add Candy");
        Container content = jf.getContentPane();

        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(150, 25));  // Set preferred size

        JTextField candyIDTextField = new JTextField();
        candyIDTextField.setPreferredSize(new Dimension(150, 25));  // Set preferred size

        JTextField storeNameTextField = new JTextField();
        storeNameTextField.setPreferredSize(new Dimension(150, 25));  // Set preferred size

        JTextField descriptionTextField = new JTextField();
        descriptionTextField.setPreferredSize(new Dimension(150, 25));  // Set preferred size

        JTextField priceTextField = new JTextField();
        priceTextField.setPreferredSize(new Dimension(150, 25));  // Set preferred size

        JTextField quantityTextField = new JTextField();
        quantityTextField.setPreferredSize(new Dimension(150, 25));  // Set preferred size

        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        content.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        content.add(new JLabel("Candy ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        content.add(candyIDTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        content.add(new JLabel("Store Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        content.add(storeNameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        content.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        content.add(descriptionTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        content.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        content.add(priceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        content.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        content.add(quantityTextField, gbc);

        JButton addButton = new JButton(new AbstractAction("Add Candy") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                int candyID = Integer.parseInt(candyIDTextField.getText());
                String storeName = storeNameTextField.getText();
                String description = descriptionTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());

                Candy newCandy = new Candy(name, new Store(storeName), description, candyID, quantity, price);

                jf.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        content.add(addButton, gbc);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }



    public static void viewSalesInformation(Store store) {
        JFrame jf = new JFrame("Sales Information");
        // Add logic to display sales information for the store
        JLabel salesLabel = new JLabel("Sales information for " + store.getName());
        jf.add(salesLabel);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }

    public static void showDeleteCandyConfirmation(Store store, String candyName) {
        int confirmDialogResult = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete " + candyName + " from " + store.getName() + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmDialogResult == JOptionPane.YES_OPTION) {
            // Implement candy deletion logic here
            JOptionPane.showMessageDialog(null, candyName + " was successfully deleted from " + store.getName());
        }
    }
}
