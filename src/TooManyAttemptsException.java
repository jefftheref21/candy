/**
 * Exception of Too Many Attempts
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */

public class TooManyAttemptsException extends Exception {
    public TooManyAttemptsException(String message) {
        super(message);
    }
}