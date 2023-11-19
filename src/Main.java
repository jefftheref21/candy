import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main method of Candy Marketplace
 *
 * @version Nov 13, 2023
 * @authors Pablo Garces, Nathan Park, Aadiv Reki, Jeffrey Wu, Jaden Ye
 */
public class Main {
    public static final String BUYER_MENU = """
            Welcome to the buyer menu. Enter which option you would like!
            1. View Marketplace
            2. Sort Marketplace
            3. Search for Product
            4. View Store Statistics
            5. Sort Store Statistics
            6. Shopping Cart
            7. History
            8. Export History
            9. Exit""";
    public static final String SELLER_MENU = """
            Welcome to the seller menu. Enter which option you would like!
            1. Create Store
            2. Create Product
            3. Edit Product
            4. Delete Product
            5. View Sales
            6. View Customer Statistics
            7. Import Products
            8. Export Products
            9. Sort Customer Statistics
            10. Exit""";

    public static final String BUY_CANDY_MENU = """
            Select which option you would like to proceed with.
            1.Buy Instantly
            2.Add to Shopping Cart
            3.Exit""";
    public static final String SELLER_SORT_MENU = """
            Select which option you would like to proceed with.
            1.Buy Instantly
            2.Add to Shopping Cart
            3.View Description
            4.Exit""";


    public static void main(String[] args) throws TooManyAttemptsException, IOException {
        Scanner scanner = new Scanner(System.in);

        String buyerOrSeller = "";

        Buyer buyer = new Buyer();
        Seller seller = new Seller();

        ArrayList<Store> allStores;


        // ALL OF THIS FOR DEBUGGING
        /**
         Store store = new Store();
         Candy candy = new Candy("Kitkat", store, "tasty", 3.5);
         Candy candy2 = new Candy("Starbursts", store, "tasty", 3.5);
         Candy candy3 = new Candy("Swedish Fish", store, "tasty", 3.5);
         ArrayList<Candy> candies = new ArrayList<>();
         candies.add(candy);
         candies.add(candy2);
         candies.add(candy3);
         store.setCandies(candies);
         Purchase purchase = new Purchase(candy, 10);
         Purchase purchase2 = new Purchase(candy2, 100);
         Purchase purchase3 = new Purchase(candy3, 5);
         ArrayList<Purchase> purchases = new ArrayList<>();
         purchases.add(purchase);
         purchases.add(purchase2);
         purchases.add(purchase3);
         Sale sale = new Sale(candy, 10, buyer);
         Sale sale2 = new Sale(candy2, 100, buyer);
         Sale sale3 = new Sale(candy3, 5, buyer);
         ShoppingCart shoppingCart = new ShoppingCart(purchases);
         ArrayList<Sale> history = new ArrayList<>();
         history.add(sale);
         history.add(sale2);
         history.add(sale3);
         ArrayList<Store> stores = new ArrayList<>();
         stores.add(store);
         StoreManager storeManager = new StoreManager();
         storeManager.setStores(stores);
         // ALL OF THIS FOR DEBUGGING
         **/

        //Welcome page to Candy MarketPlace

        System.out.println("Welcome to Candy Marketplace!");

        boolean setUp = false;
        boolean created = false;
        do {
            System.out.println("Enter number to sign up or login!\n" +
                    "1. Sign up\n" +
                    "2. Login");

            int welcomeDecision = scanner.nextInt();
            scanner.nextLine();
            if (welcomeDecision == 1) {

                do {
                    //TODO create new object of user with information
                    System.out.println("Are you a buyer or seller?");
                    buyerOrSeller = scanner.nextLine();

                    if (buyerOrSeller.equalsIgnoreCase("buyer")) {
                        System.out.println("Enter new username:");
                        String newUsername = scanner.nextLine();
                        System.out.println("Enter new password:");
                        String newPassword = scanner.nextLine();
                        buyer = new Buyer(newUsername, newPassword);
                        buyer.writeToFile("Users.txt", buyer.getShoppingCart().toString());
                        created = true;
                    } else if (buyerOrSeller.equalsIgnoreCase("seller")) {
                        System.out.println("Enter new username:");
                        String newUsername = scanner.nextLine();
                        System.out.println("Enter new password:");
                        String newPassword = scanner.nextLine();
                        seller = new Seller(newUsername, newPassword);
                        System.out.println(seller.getStoreManager().toString());
                        seller.writeToFile("Users.txt", seller.getStoreManager().toString());
                        System.out.println("Seller object written");
                        created = true;
                    } else {
                        System.out.println("Invalid entry! Please try again");
                    }
                } while (!created);
                setUp = true;
            } else if (welcomeDecision == 2) { // login
                int attempts = 0;
                boolean login = false;

                while (attempts < 3 && !login) {
                    System.out.println("Are you a buyer or seller?");
                    buyerOrSeller = scanner.nextLine();

                    //TODO check if user is in system
                    if (buyerOrSeller.equalsIgnoreCase("buyer")) {
                        System.out.println("Enter username:");
                        String username = scanner.nextLine();
                        System.out.println("Enter password:");
                        String password = scanner.nextLine();

                        buyer =(Buyer) getUser(username, password);
                        buyer.readHistory();
                        if (buyer.checkAccount("Users.txt")) {
                            System.out.println("Success! Welcome " + username + "!");
                            login = true;
                        } else {
                            System.out.println("Invalid username or password! Try Again!");
                            attempts++;
                        }
                    } else if (buyerOrSeller.equalsIgnoreCase("seller")) {
                        System.out.println("Enter username:");
                        String username = scanner.nextLine();
                        System.out.println("Enter password:");
                        String password = scanner.nextLine();
                        seller =(Seller) getUser(username, password);
                        // seller.setStoreManager(storeManager);
                        if (seller.checkAccount("Users.txt")) {
                            System.out.println("Success! Welcome " + username + "!");
                            login = true;
                        } else {
                            System.out.println("Invalid username or password! Try Again!");
                            attempts++;
                        }
                    } else {
                        System.out.println("Invalid entry! Please try again");
                    }
                }

                if (attempts == 3) {
                    throw new TooManyAttemptsException("Sorry, you have maxed out your " +
                            "login attempts.");
                }

                setUp = true;
            } else {
                System.out.println("Invalid entry! Please try again");
            }
        } while (!setUp);

        boolean cont = true;
        CandyManager.readCandy();
        do {
            if (buyerOrSeller.equalsIgnoreCase("buyer")) {
                boolean redoBuyer;
                do {
                    redoBuyer = false;
                    System.out.println(BUYER_MENU);
                    int buyerDecision = scanner.nextInt();
                    scanner.nextLine();
                    // @Nathan TODO: When buyer gets onto product page, show option to either buy product or exit
                    // @Aadiv Help nathan actually writing the methods for these to follow along with the logic
                    switch (buyerDecision) {
                        case 1:
                            //TODO View Marketplace
                            // TODO: Be able to view product page and buy

                            System.out.println(buyer.viewMarketplace());

                            boolean candyIDIncorrect = true;
                            int candyID;

                            do {
                                System.out.println("Enter the CandyID for which candy you would like to purchase.");
                                candyID = scanner.nextInt();
                                scanner.nextLine();

                                if (!CandyManager.candyIDs.contains(candyID)) {
                                    System.out.println("Invalid Candy ID. Enter a valid Candy ID.");
                                } else {
                                    candyIDIncorrect = false;
                                }
                            } while (candyIDIncorrect);

                            System.out.println(buyer.viewProductPage(candyID));

                            System.out.println(BUY_CANDY_MENU);
                            int buyMethod = scanner.nextInt();
                            scanner.nextLine();

                            boolean buyMethodInvalid = false;

                            do {
                                switch (buyMethod) {
                                    case 1:
                                        System.out.println("How much candy would you like to buy?");
                                        int quantityBought = scanner.nextInt();
                                        scanner.nextLine();

                                        buyer.buyInstantly(candyID, quantityBought, buyer);
                                        break;
                                    case 2:
                                        System.out.println("How much candy would you like to buy?");
                                        int quantityBoughtShoppingCart = scanner.nextInt();
                                        int index = CandyManager.candyIDs.indexOf(candyID);
                                        scanner.nextLine();
                                        Purchase purchase = new Purchase(CandyManager.candies.get(index)
                                                , quantityBoughtShoppingCart);
                                        buyer.addToShoppingCart(purchase);
                                        System.out.println("Added to Shopping Cart!");
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        System.out.println("Invalid Entry! Please Try Again!");
                                        buyMethodInvalid = true;
                                        break;
                                }
                            } while (buyMethodInvalid);
                            break;
                        case 2: // For sorting
                            System.out.println("Enter the choice you would like to sort the candy.");
                            boolean sortContinue = true;
                            do {
                                System.out.println("1. Sort the candy from least to greatest by price.\n" +
                                        "2. Sort the candy from greatest to least by price.\n" +
                                        "3. Sort the candy from least to greatest by quantity.\n" +
                                        "4. Sort the candy from greatest to least by quantity");
                                int sortChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (sortChoice) {
                                    case 1:
                                        buyer.sortProducts(1);
                                        sortContinue = false;
                                        break;
                                    case 2:
                                        buyer.sortProducts(2);
                                        sortContinue = false;
                                        break;
                                    case 3:
                                        buyer.sortProducts(3);
                                        sortContinue = false;
                                        break;
                                    case 4:
                                        buyer.sortProducts(4);
                                        sortContinue = false;
                                        break;
                                    default:
                                        System.out.println("Invalid Entry! Please try again.");
                                        break;
                                }
                            } while (sortContinue);

                            System.out.println(buyer.viewMarketplace());
                            System.out.println("Enter the CandyID for which candy you would like to purchase.");
                            int candyIDSort = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(buyer.viewProductPage(candyIDSort));

                            System.out.println(BUY_CANDY_MENU);
                            int buyMethodSort = scanner.nextInt();
                            scanner.nextLine();

                            boolean buyMethodInvalidSort = false;

                            do {
                                switch (buyMethodSort) {
                                    case 1:
                                        System.out.println("How much candy would you like to buy?");
                                        int quantityBought = scanner.nextInt();
                                        scanner.nextLine();

                                        buyer.buyInstantly(candyIDSort, quantityBought, buyer);
                                        break;
                                    case 2:
                                        System.out.println("How much candy would you like to buy?");
                                        int quantityBoughtShoppingCart = scanner.nextInt();
                                        int index = CandyManager.candyIDs.get(candyIDSort);
                                        scanner.nextLine();
                                        Purchase purchase = new Purchase(CandyManager.candies.get(index)
                                                , quantityBoughtShoppingCart);
                                        buyer.addToShoppingCart(purchase);
                                        System.out.println("Added to Shopping Cart!");
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        System.out.println("Invalid Entry! Please Try Again!");
                                        buyMethodInvalidSort = true;
                                        break;
                                }
                            } while (buyMethodInvalidSort);

                            break;
                        case 3:
                            boolean searchValid = true;
                            String keyWord = "";
                            do {
                                System.out.println("What would you like to search?");
                                keyWord = scanner.nextLine();
                                System.out.println(buyer.search(keyWord));

                                if (buyer.search(keyWord).equals("No candies found!")) {
                                    System.out.println("Please try another again!");
                                } else {
                                    searchValid = false;
                                }
                            } while (searchValid);


                            System.out.println("Enter the CandyID for which candy you would like to purchase.");
                            int candyIDSearch = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println(buyer.viewProductPage(candyIDSearch));

                            boolean buyMethodInvalidSearch = false;

                            if (!buyer.search(keyWord).equals("No candies found!")) {
                                System.out.println(BUY_CANDY_MENU);
                                int buyMethodSearch = scanner.nextInt();
                                scanner.nextLine();
                                do {
                                    switch (buyMethodSearch) {
                                        case 1:
                                            System.out.println("How much candy would you like to buy?");
                                            int quantityBought = scanner.nextInt();
                                            scanner.nextLine();

                                            buyer.buyInstantly(candyIDSearch, quantityBought, buyer);
                                            break;
                                        case 2:
                                            System.out.println("How much candy would you like to buy?");
                                            int quantityBoughtShoppingCart = scanner.nextInt();
                                            int index = CandyManager.candyIDs.get(candyIDSearch);
                                            scanner.nextLine();
                                            Purchase purchase = new Purchase(CandyManager.candies.get(index)
                                                    , quantityBoughtShoppingCart);
                                            buyer.addToShoppingCart(purchase);
                                            System.out.println("Added to Shopping Cart!");
                                            break;
                                        case 3:
                                            buyMethodInvalidSearch = false;
                                            break;
                                        default:
                                            System.out.println("Invalid Entry! Please Try Again!");
                                            buyMethodInvalidSearch = true;
                                            break;
                                    }
                                } while (buyMethodInvalidSearch);
                            }
                            break;
                        case 4:
                            //TODO Statistics method
                            // @Nathan TODO: calling statistics method, then perform sorting selection or exiting it
                            allStores = StoreManager.getAllStores();

                            System.out.println(buyer.viewStoreStatistics(allStores, readFile("Users.txt")));
                            break;
                        case 5:
                            allStores = StoreManager.getAllStores();
                            System.out.println("1. Sort store statistics by number of products sold " +
                                    "from least to greatest\n" +
                                    "2. Sort store statistics by number of products sold from greatest to least\n" +
                                    "3. Sort store statistics by number of products you have bought " +
                                    "from least to greatest\n" +
                                    "4. Sort store statistics by number of products you have bought " +
                                    "greatest to least\n" +
                                    "5. Exit");
                            int storeStatisticChoice = scanner.nextInt();
                            scanner.nextLine();

                            boolean doneWithStatistics;
                            do {
                                if (storeStatisticChoice >= 1 && storeStatisticChoice <= 4) {
                                    buyer.sortStoreStatistics(allStores, storeStatisticChoice);
                                    buyer.viewStoreStatistics(allStores, readFile("Users.txt"));
                                    doneWithStatistics = true;
                                } else if (storeStatisticChoice == 5) {
                                    break;
                                } else {
                                    System.out.println("Invalid entry! Please try again!");
                                    doneWithStatistics = false;
                                }
                            } while (!doneWithStatistics);
                        case 6:
                            System.out.println(buyer.viewShoppingCart());
                            break;
                        case 7:
                            System.out.println(buyer.viewHistory());
                            break;
                        case 8:
                            buyer.exportHistoryToFile();
                            break;
                        case 9:
                            System.out.println("Thank you for using our Candy Marketplace!");
                            buyer.exportHistoryToFile();
                            buyer.writeToFile("Users.txt", buyer.getShoppingCart().toString());
                            CandyManager.writeCandy();
                            cont = false;
                            break;
                        default:
                            System.out.println("Invalid entry! Please try again");
                            redoBuyer = true;
                            break;
                    }
                } while (redoBuyer);
            } else if (buyerOrSeller.equalsIgnoreCase("seller")) {
                boolean redoSeller;
                do {
                    redoSeller = false;
                    System.out.println(SELLER_MENU);
                    int sellerDecision = scanner.nextInt();
                    scanner.nextLine();

                    // @Jaden you wrote most of this code anyway somewhere
                    // Remember to assume you have all the information in the object itself
                    // No need for file i/o stuff I'm pretty sure
                    switch (sellerDecision) {
                        case 1:
                            System.out.println("What is your name of your new store?");
                            String newStoreName = scanner.nextLine();
                            seller.getStoreManager().getStores().add(new Store(newStoreName));
                            seller.writeToFile("Users.txt", seller.getStoreManager().toString());
                            System.out.println(newStoreName + " store created!");
                            break;
                        case 2:
                            Store selectedStore = new Store();
                            boolean storeNotExist = true;
                            System.out.println("In which store are you adding the new candy?");
                            String storeName = scanner.nextLine();
                            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                                if (seller.getStoreManager().getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                    selectedStore = seller.getStoreManager().getStores().get(i);
                                    storeNotExist = false;
                                }
                            }
                            if (storeNotExist) {
                                System.out.println("You do not have a store by that name!");
                                break;
                            } else {
                                System.out.println("What is the name of the new candy you would like to add?");
                                String newCandyName = scanner.nextLine();
                                System.out.println("Give a quick description of what the candy would be.");
                                String description = scanner.nextLine();
                                System.out.println("Set the price of the candy");
                                double price = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Please specify the quantity of candy you wish to offer for sale.");
                                int newQuantity = scanner.nextInt();
                                Candy newCandy = new Candy(newCandyName, selectedStore, description,
                                        CandyManager.prodCounter, newQuantity, price);
                                selectedStore.addCandy(newCandy, newQuantity, CandyManager.prodCounter);
                                System.out.println(newCandy.toString() + " created!");
                                seller.writeToFile("Users.txt", seller.getStoreManager().toString());
                                break;
                            }

                        case 3:
                            selectedStore = new Store();
                            storeNotExist = true;
                            System.out.println("In which store are you adding the new candy?");
                            storeName = scanner.nextLine();
                            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                                if (seller.getStoreManager().getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                    selectedStore = seller.getStoreManager().getStores().get(i);
                                    storeNotExist = false;
                                }
                            }
                            if (storeNotExist) {
                                System.out.println("You do not have a store by that name!");
                                break;
                            } else {
                                System.out.println("Please provide the ID of the candy you wish to modify.");
                                int newID = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("What is the name of the new candy you would like to add?");
                                String newCandyName = scanner.nextLine();
                                System.out.println("Give a quick description of what the candy would be.");
                                String description = scanner.nextLine();
                                System.out.println("Set the price of the candy");
                                double price = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Please specify the quantity of candy you wish to offer for sale.");
                                int newQuantity = scanner.nextInt();
                                Candy newCandy = new Candy(newCandyName, selectedStore, description,
                                        CandyManager.prodCounter, newQuantity, price);
                                selectedStore.editCandy(CandyManager.prodCounter, newCandy, newQuantity);
                                seller.writeToFile("Users.txt", seller.getStoreManager().toString());
                                System.out.println(newCandy.toString() + " modified!");
                            }


                            break;
                        case 4:
                            selectedStore = new Store();
                            storeNotExist = true;
                            System.out.println("In which store are you adding the new candy?");
                            storeName = scanner.nextLine();
                            for (int i = 0; i < seller.getStoreManager().getStores().size(); i++) {
                                if (seller.getStoreManager().getStores().get(i).getName().equalsIgnoreCase(storeName)) {
                                    selectedStore = seller.getStoreManager().getStores().get(i);
                                    storeNotExist = false;
                                }
                            }
                            if (storeNotExist) {
                                System.out.println("You do not have a store by that name!");
                                break;
                            } else {
                                System.out.println("Please provide the ID of the candy you wish to delete");
                                int deleteCandyID = scanner.nextInt();
                                scanner.nextLine();
                                selectedStore.deleteCandy(deleteCandyID);
                                seller.writeToFile("Users.txt", seller.getStoreManager().toString());
                                break;
                            }
                        case 5:
                            System.out.println(seller.listSales());
                            break;
                        case 6:
                            //TODO Statistics method
                            System.out.println(seller.listStatistics());
                            break;
                        case 7:
                            System.out.println("What file do you want to import?");
                            String fileImport = scanner.nextLine();
                            try {
                                seller.importCSV(fileImport);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            System.out.println("What file do you want to export to?");
                            String fileExport = scanner.nextLine();
                            try {
                                seller.exportToCSV(fileExport);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 9:
                            System.out.println("Enter the choice you would like to sort the candy.");
                            boolean sortContinue = true;
                            do {
                                System.out.println("1. Sort the candy from least to greatest sales.\n" +
                                        "2. Sort the candy from greatest to least sales.\n" +
                                        "3. Sort the customers from least to greatest purchases made.\n" +
                                        "4. Sort the customers from greatest to least purchases made\n");
                                int sortChoice = scanner.nextInt();
                                scanner.nextLine();
                                switch (sortChoice) {
                                    case 1:
                                        seller.sortSellerStatistics(1);
                                        sortContinue = false;
                                        break;
                                    case 2:
                                        seller.sortSellerStatistics(2);
                                        sortContinue = false;
                                        break;
                                    case 3:
                                        seller.sortSellerStatistics(3);
                                        sortContinue = false;
                                        break;
                                    case 4:
                                        seller.sortSellerStatistics(4);
                                        sortContinue = false;
                                        break;
                                    default:
                                        System.out.println("Invalid Entry! Please try again.");
                                        break;
                                }
                            } while (sortContinue);
                        case 10:
                            System.out.println("Thank you for using our Candy Marketplace!");
                            seller.writeToFile("Users.txt", seller.getStoreManager().toString());
                            CandyManager.writeCandy();
                            cont = false;
                            break;
                        default:
                            System.out.println("Invalid entry! Please try again");
                            redoSeller = true;
                            break;
                    }
                } while (redoSeller);
            } else {
                System.out.println("Invalid entry! Please try again");
            }
        } while (cont);
    }
    

    public static ArrayList<User> readFile(String fileName) throws IOException {
        // ShoppingCart<Purchase<popsicle$1243$3>;Purchase<ice cream$3241$2>>
        ArrayList<User> users = new ArrayList<>();
        File f = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        while (line != null) {
            line = line.replace('&', ',');
            String[] data = line.split(",", 3);
            User user = new User(data[0], data[1]);
            if (data[2].contains("ShoppingCart<")) {
                int index = data[2].indexOf("ShoppingCart<");
                String curr = data[2].substring(index, data[2].length() - 1);
                curr = curr.replace("Purchase", "");
                curr = curr.replace("<", "");
                curr = curr.replace(">", "");
                String[] curData = curr.split(";", -5);
                ShoppingCart shoppingCart = new ShoppingCart();
                for (int i = 0; i < curData.length; i++) {
                    curData[i] = curData[i].replace('$', ',');
                    String[] currentData = curData[i].split(",", 3);
                    int candyIndex = CandyManager.getIndex(Integer.parseInt(currentData[1]));
                    if (candyIndex != -1) {
                        Candy currCandy = CandyManager.candies.get(candyIndex);
                        Purchase currPurchase = new Purchase(currCandy, Integer.parseInt(currentData[1]));
                        shoppingCart.addItem(currPurchase);
                    }
                }
                user = new Buyer(data[0], data[1], shoppingCart);
            } else if (data[2].contains("Stores<")) {
                int index = data[2].indexOf("Stores<");
                String curr = data[2].substring(index, data[2].length() - 1);
                curr = curr.replace("Stores", "");
                curr = curr.replace("<", "");
                curr = curr.replace(">", "");
                String[] currStores = curr.split("#", -5);
                ArrayList<Store> stores = new ArrayList<>();
                // looping through all the stores
                for (int i = 0; i < currStores.length; i++) {
                    // splitting store name and index+sales info
                    String[] nameCandyData = currStores[i].split(",", 2);
                    String storeName = nameCandyData[0];
                    // splitting index and sales info
                    String[] indSales = nameCandyData[1].split("-", 2);
                    String[] candyIDs = indSales[0].split("}", -5);
                    String[] sales = indSales[1].split(";", -5);
                    Store currStore = new Store(storeName);
                    for (int j = 0; j < candyIDs.length; j++) {
                        if (!candyIDs[j].equals("") && CandyManager.getIndex(Integer.parseInt(candyIDs[j])) != -1) {
                            Candy currCandy = CandyManager.candies.get(CandyManager.getIndex(Integer.parseInt(candyIDs[j])));
                            currStore.addCandy(currCandy, currCandy.getTotalQuantity(), currCandy.getCandyID());
                        }
                    }
                    for (int j = 0; j < sales.length; j++) {
                        if (!sales[0].equals("Sale$")) {
                            sales[j].replace('$', '_');
                            String currSale = sales[j].substring(sales[j].indexOf("Sale<"), sales[j].length());
                            String[] saleData = currSale.split("_", 4);
                            if (CandyManager.getIndex(Integer.parseInt(saleData[0])) != -1) {
                                Candy currCandy = CandyManager.candies.get(CandyManager.getIndex(Integer.parseInt(saleData[0])));
                                Sale sale = new Sale(currCandy, Integer.parseInt(saleData[1]), (Buyer) user);
                                currStore.addSale(sale);
                            }
                        }
                    }
                    stores.add(currStore);
                }
                user = new Seller(data[0], data[1], new StoreManager(stores));
            }
            users.add(user);
            line = br.readLine();
        }
        return users;
    }
    public static User getUser(String username, String password) throws IOException {
        ArrayList<User> users = readFile("Users.txt");
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername()) && password.equals(users.get(i).getPassword())) {
                return users.get(i);
            }
        }
        return new User(username, password);
    }
}