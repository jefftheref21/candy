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

    public void writeToFile() throws IOException, ClassNotFoundException {
            File f = new File("Users.txt");
            ArrayList<User> users = new ArrayList<>();
            if (f.exists() && f.length() > 0) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                users = (ArrayList<User>) ois.readObject();
                ois.close();
            }
            boolean present = false;
            for (int i = 0; i < users.size(); i++) {
                User currUser = users.get(i);
                if (currUser instanceof Seller) {
                    Seller currSeller = (Seller) currUser;
                    for (int k = 0; k < currSeller.getStoreManager().getStores().size(); k++) {
                        Store currStore = currSeller.getStoreManager().getStores().get(k);
                        ArrayList<Candy> currCandies = new ArrayList<>();
                        for (int j = 0; j < CandyManager.candies.size(); j++) {
                            if (CandyManager.candies.get(j).getStore().getName().equals(currStore.getName())) {
                                currCandies.add(CandyManager.candies.get(j));
                            }
                        }
                        currSeller.getStoreManager().getStores().get(k).setCandies(currCandies);
                    }
                }
                if (currUser.getUsername().equals(this.getUsername()) && currUser.getPassword().equals(this.getPassword())) {
                    users.set(i, this);
                    present = true;
                }
            }
            if (!present) {
                users.add(this);
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f, false));
            oos.writeObject(users);
            oos.flush();
            oos.close();
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