import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class ControlCenter extends JDialog {
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
            JButton storeButton = new JButton(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            Color buttonColor = new Color(235, 158, 52);
            storeButton.setBackground(buttonColor);
            storeButton.setPreferredSize(new Dimension(100, 100));
            storeButton.setHorizontalAlignment(SwingConstants.CENTER);
            String buttonText = currStore.getName() + "\n" + currStore.getName() + "\n$"
                    + currStore + "\n" + currStore;
            storeButton.setText("<html>" + buttonText.replaceAll("\\n", "<br>") + "</html>");
            System.out.println(currStore.getName());

            jp.add(storeButton, new GridBagConstraints(i % 4, i / 4, 1, 1,
                    0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                    new Insets(10, 10, 10, 10), 5, 5));
        }
    }

    public static void displayCandyButtons() {

    }
}
