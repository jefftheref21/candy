import java.util.*;
import java.io.*;

/**
 * Manager that handles all of the candies
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class CandyManager {
    // class doesn't need a constructor, because we're just interested in the static candies ArrayList.
    public static ArrayList<Candy> candies = new ArrayList<>();
    public static ArrayList<Integer> candyIDs = new ArrayList<>();
    public static ArrayList<Integer> quantities = new ArrayList<>();
    public static int prodCounter = 0;

    public static void readCandy() throws IOException {
        //
        BufferedReader br = new BufferedReader(new FileReader("Candies.txt"));
        prodCounter = Integer.parseInt(br.readLine());
        String line = br.readLine();
        while (line != null) {
            String[] data = line.substring(line.indexOf('<') + 1, line.indexOf('>')).split("_", 6);
            candyIDs.add(Integer.parseInt(data[3]));
            quantities.add(Integer.parseInt(data[4]));
            candies.add(new Candy(data[0], new Store(data[1]), data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5])));
            line = br.readLine();
        }
        br.close();
    }
    public static void writeCandy() throws IOException {
        PrintWriter pw = new PrintWriter(new FileOutputStream("Candies.txt"));
        pw.println(prodCounter);
        for (int i = 0; i < candies.size(); i++) {
            pw.println(candyIDs.get(i) + "," + quantities.get(i) + "," + candies.get(i).toString());
        }
        pw.close();
    }
    public static int getIndex(int id) {
        for (int i = 0; i < candyIDs.size(); i++) {
            if (candyIDs.get(i) == id) {
                return i;
            }
        }
        return -1;
    }
}