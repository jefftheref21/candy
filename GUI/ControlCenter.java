import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class ControlCenter extends JDialog {
    public static final Color buttonColor = new Color(235, 158, 52);
    public ControlCenter() {

    }

    public void run() {
        JFrame jf = new JFrame("Control Center");

        Container content = jf.getContentPane();
        content.setLayout(new BorderLayout());
        displayTopPanel(content);
        displaySidePanel(content);
        displayBottomPanel(content);

    }

    /**
     * Should have sort
     * @param content - To add to the larger frame
     */
    public static void displayTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        content.add(topPanel);
    }

    /**
     *
     * @param content - To add to the larger frame
     */
    public static void displaySidePanel(Container content) {
        JPanel sidePanel = new JPanel();
        content.add(sidePanel);
    }

    public static void displayBottomPanel(Container content) {
        JPanel bottomPanel = new JPanel();
        content.add(bottomPanel);
    }

    public static void displayStoreButtons(Store[] stores, Container content) {
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());

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
            storeButton.setPreferredSize(new Dimension(100, 100));
            storeButton.setHorizontalAlignment(SwingConstants.CENTER);
            String buttonText = currStore.getName();
            storeButton.setText(buttonText);
            System.out.println(currStore.getName());

            jp.add(storeButton, gbc);
        }
    }

    public static void showStoreOptionDialog(Store store) {
        String[] options = {"View Products", "View Sales"};
        int signUpOrLogin = JOptionPane.showOptionDialog(null, "Store:",
                "Store Options", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (signUpOrLogin == 0) {
            // View Products
            // displayCandyButtons(store);
        } else {
            // View Sales

        }
    }

    public static void displayCandyButtons(Store store) {
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());

        for (int i = 0; i < store.getCandies().size(); i++) {
            Candy currCandy = store.getCandies().get(i);
            GridBagConstraints gbc = new GridBagConstraints(i % 4, i / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5);

            JButton candyButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Edit candy information
                    // showEditCandyDialog(store);
                }
            });

            candyButton.setBackground(buttonColor);
            candyButton.setPreferredSize(new Dimension(100, 100));
            candyButton.setHorizontalAlignment(SwingConstants.CENTER);
            String buttonText = currCandy.getName();
            candyButton.setText(buttonText);
            System.out.println(currCandy.getName());

            jp.add(candyButton, gbc);
        }
    }

    public static void showEditCandyDialog() {
        JFrame jf = new JFrame("Edit Candy");
        Container content = jf.getContentPane();

        content.setLayout(new GridBagLayout());

        JTextField nameTextField = new JTextField(10);
        JTextField priceTextField = new JTextField(10);
        JTextField quantityTextField = new JTextField(10);
        JTextField descriptionTextField = new JTextField(10);

        content.add(nameTextField);
        content.add(priceTextField);
        content.add(quantityTextField);
        content.add(descriptionTextField);
    }
}
