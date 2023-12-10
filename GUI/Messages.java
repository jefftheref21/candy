import javax.swing.*;

/**
 * Messages
 * This class contains all the messages that will be displayed to the user after pressing certain buttons.
 *
 * @author Jeffrey Wu, Nathan Park, Aadiv Reki, Pablo Garces, Jaden Ye
 * @version 1.0
 */
public class Messages {
    // APPLICATION MESSAGES
    public static void showSuccessfulSignUpDialog() {
        JOptionPane.showMessageDialog(null, "Sign up successful!",
                "Sign Up Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulSignUpDialog() {
        JOptionPane.showMessageDialog(null, "Sign up failed!",
                "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessfulLoginDialog() {
        JOptionPane.showMessageDialog(null, "Login successful!",
                "Login Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulLoginDialog() {
        JOptionPane.showMessageDialog(null, "Login failed!",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
    }

    // MARKETPLACE MESSAGES

    public static void showSuccessfulPurchase() {
        JOptionPane.showMessageDialog(null, "Thank you for your purchase",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showNumberFormatError() {
        JOptionPane.showMessageDialog(null, "Please enter a valid number",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showQuantityExceededError() {
        JOptionPane.showMessageDialog(null, "The quantity entered exceeds the current " +
                "quantity. Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showExportHistorySuccessful() {
        JOptionPane.showMessageDialog(null, "Purchase history exported successfully",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showExportHistoryUnsuccessful() {
        JOptionPane.showMessageDialog(null, "Purchase history export unsuccessful",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
