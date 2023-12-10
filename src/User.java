import java.io.*;
import java.util.ArrayList;

/**
 * Users to login
 *
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 * @version Nov 13, 2023
 */

public class User implements Serializable {
    private String username;
    private String password;
    public User() {
        username = null;
        password = null;
    }
    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // Maybe not necessary in Project 5
    public String toString() {
        return username + ", " + password;
    }
}