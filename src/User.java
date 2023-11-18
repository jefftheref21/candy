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

    /* Change all of this
    public void writeToFile(String filename) {
        // credentials format: username,password&any relevant info like ShoppingCart for buyers or stores for sellers
        // I think there should only be one file writing operation happening at the end of a User session.
        try {
            File f = new File(filename);
            PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));
            pw.println(this.username + "," + this.password);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String fileName, String additional) {
        try {
            File f = new File(fileName);
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String toWrite = this.username + "," + this.password;
            String line = br.readLine();
            boolean added = false;
            while (line != null) {
                if (line.split("&", 2)[0].equals(toWrite)) {
                    lines.add(toWrite + "&" + additional);
                    added = true;
                } else {
                    lines.add(line);
                }
                line = br.readLine();
            }
            br.close();
            if (!added) {
                lines.add(toWrite);
            }
            PrintWriter pw = new PrintWriter(new FileOutputStream(f));
            for (int i = 0; i < lines.size(); i++) {
                pw.println(lines.get(i));
            }
            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean checkAccount(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            boolean found = false;
            while (line != null) {
                String[] roughInfo = line.split("&", 2);
                String[] userInfo = roughInfo[0].split(",", 2);
                String accountUser = userInfo[0];
                String accountPass = userInfo[1];
                line = br.readLine();
                if (accountUser.equals(username) && accountPass.equals(password)) {
                    found =true;
                }
            }
            br.close();
            return found;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    */
    // Maybe not necessary in Project 5
    public String toString() {
        return username + ", " + password;
    }
}