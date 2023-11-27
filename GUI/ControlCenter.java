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

    public static void displaySidePanel(Container content) {
        JPanel sidePanel = new JPanel();
        content.add(sidePanel);
    }

    public static void displayBottomPanel(Container content) {
        JPanel bottomPanel = new JPanel();
        content.add(bottomPanel);
    }

    public static void displayStoreButtons() {

    }

    public static void displayCandyButtons() {

    }
}
