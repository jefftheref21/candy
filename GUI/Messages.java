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

    // CONTROLCENTER MESSAGES

    public static void showSuccessfulCreateStoreDialog() {
        JOptionPane.showMessageDialog(null, "Store created successfully!",
                "Create Store Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulCreateStoreDialog() {
        JOptionPane.showMessageDialog(null, "Store creation failed!",
                "Create Store Failed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessfulAddCandyDialog() {
        JOptionPane.showMessageDialog(null, "Candy added successfully!",
                "Add Candy Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulAddCandyDialog() {
        JOptionPane.showMessageDialog(null, "Candy add failed!",
                "Add Candy Failed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessfulEditCandyDialog() {
        JOptionPane.showMessageDialog(null, "Candy edited successfully!",
                "Edit Candy Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulEditCandyDialog() {
        JOptionPane.showMessageDialog(null, "Candy edit failed!",
                "Edit Candy Failed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessfulDeleteCandyDialog() {
        JOptionPane.showMessageDialog(null, "Candy deleted successfully!",
                "Delete Candy Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulDeleteCandyDialog() {
        JOptionPane.showMessageDialog(null, "Candy delete failed!",
                "Delete Candy Failed", JOptionPane.ERROR_MESSAGE);
    }

    public static String getImportPath() {
        String fileName = JOptionPane.showInputDialog(null, "Enter file path for import:",
                "Import from CSV", JOptionPane.PLAIN_MESSAGE);
        return fileName;
    }

    public static String getExportPath() {
        String fileName = JOptionPane.showInputDialog(null, "Enter file path for export:",
                "Export to CSV", JOptionPane.PLAIN_MESSAGE);
        return fileName;
    }

    public static void showSuccessfulImportDialog() {
        JOptionPane.showMessageDialog(null, "Import successful!",
                "Import Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showSuccessfulExportDialog() {
        JOptionPane.showMessageDialog(null, "Export successful!",
                "Export Confirmation", JOptionPane.PLAIN_MESSAGE);
    }

    public static void showUnsuccessfulImportDialog() {
        JOptionPane.showMessageDialog(null, "Import failed!",
                "Import Failed", JOptionPane.ERROR_MESSAGE);
    }

    public static void showUnsuccessfulExportDialog() {
        JOptionPane.showMessageDialog(null, "Export failed!",
                "Export Failed", JOptionPane.ERROR_MESSAGE);
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

    public static void showAddToCartSuccessful() {
        JOptionPane.showMessageDialog(null, "Added to Shopping Cart Successful",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showRemoveToCartSuccessful() {
        JOptionPane.showMessageDialog(null, "Removed to Shopping Cart Successful",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
