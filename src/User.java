import java.io.*;
import java.util.ArrayList;

/**
 * Users to login
 *
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 * @version Nov 13, 2023
 */

public class User {
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

    public void writeToFile(String filename) {
        try {
            File f = new File("Users.txt");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            ArrayList<User> users = (ArrayList<User>) ois.readObject();
            ois.close();
            boolean present = false;
            for (int i = 0; i < users.size(); i++) {
                User currUser = users.get(i);
                if (currUser.getUsername().equals(this.getUsername()) && currUser.getPassword().equals(this.getPassword())) {
                    currUser = this;
                    present = true;
                }
            }
            if (!present) {
                users.add(this);
            }
            oos.writeObject(users);
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkAccount(String filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            boolean found = false;
            ArrayList<User> users = (ArrayList<User>) ois.readObject();
            ois.close();
            for (int i = 0; i < users.size(); i++) {
                User currUser = users.get(i);
                if (currUser.getUsername().equals(this.getUsername()) && currUser.getPassword().equals(this.getPassword())) {
                    found = true;
                }
            }
            return found;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Maybe not necessary in Project 5
    public String toString() {
        return username + ", " + password;
    }
}