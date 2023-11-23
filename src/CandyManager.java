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

    public static int getIndex(int id) {
        for (int i = 0; i < candyIDs.size(); i++) {
            if (candyIDs.get(i) == id) {
                return i;
            }
        }
        return -1;
    }
}