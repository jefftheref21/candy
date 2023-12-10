import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyerClient extends Buyer {
    private Socket socket;
    private Marketplace marketplace;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Action action;

    private CandyManager candyManager;

    public BuyerClient(Socket socket, Marketplace marketplace) throws IOException {
        this.socket = socket;
        this.marketplace = marketplace;
        Candy candy1 = new Candy("Snickers", new Store("Walmart"), "Chocolate bar", 1, 50, 1.00);
        Candy candy2 = new Candy("Twix", new Store("Walmart"), "Chocolate bar",2, 25, 2.00);
        Candy candy3 = new Candy("M&Ms", new Store("Walmart"), "Chocolate bar", 3, 100, 3.00);
        Candy candy4 = new Candy("Kit Kat", new Store("Walmart"), "Chocolate bar", 4, 75, 4.00);
        Candy candy5 = new Candy("Sour Patch Kids", new Store("Walmart"), "Sour candy", 5, 50, 1.00);
        Candy candy6 = new Candy("Sour Skittles", new Store("Walmart"), "Sour candy", 6, 25, 2.00);
        ArrayList<Candy> candies = new ArrayList<>();
        candies.add(candy1);
        candies.add(candy2);
        candies.add(candy3);
        candies.add(candy4);
        candies.add(candy5);
        candies.add(candy6);
        candyManager = new CandyManager(candies, 7);

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Action getAction() {
        return action;
    }

    public CandyManager getCandyManager() {
        return candyManager;
    }

    public void setCandyManager(CandyManager candyManager) {
        this.candyManager = candyManager;
    }

    //Sends the button in which the user clicks within marketplace.
    //ie sort, shopping cart, ect.
    public void sendBuyerDecision(String type) {
        try {
            out.writeUTF(type);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sends the candy in which the buyer would like to buy
    //The type refers if the user would like to put in shopping cart or buy instantly
    public void sendCandyProduct(Candy candy, String type, int quantity) {
        try {
            out.writeObject(candy);
            out.flush();
            out.writeUTF(type);
            out.flush();
            out.write(quantity);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sends to server which user would like to buy everything in their shopping cart
    public void sendBuyShoppingCart() {
        try {
            HashMap hashmap = new HashMap<String, Buyer>();
            hashmap.put(Action.BUY_SHOPPING_CART, this);
            out.writeObject(hashmap);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sends to server the candy in which the user would like to remove from their shopping cart
    public void sendRemoveShoppingCart(Candy candy) {
        try {
            HashMap hashmap = new HashMap<String, Buyer>();
            hashmap.put(Action.REMOVE_FROM_CART, candy);
            out.writeObject(hashmap);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sends to server the users search text
    public void sendSearchResults(String text) {
        try {
            out.writeUTF(text);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //receives the arraylist of candies that the user searched from the server
    public ArrayList<Candy> receiveSearchCandies() {
        try {
            ArrayList<Candy> searchCandy = (ArrayList<Candy>) in.readObject();
            return searchCandy;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Sends to server the users decision in which they would like to sort the marketplace
    public void sendSortDecision(String decision) {
        try {
            out.writeUTF(decision);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Candy> receiveSortCandies() {
        try {
            ArrayList<Candy> sortCandy = (ArrayList<Candy>) in.readObject();
            return sortCandy;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Action receiveAction() {
        try {
            String input = in.readLine();
            action = Action.valueOf(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return action;
    }

}
