# CS-180-Project-5 - Candy Marketplace ( continued)

## How to run this program:
1. Run MainServer.java
2. Run Application.java

## Student Submission List:
1. Pablo Yague - Submitted Report on Brightspace.
2. Pablo Yague - Submitted Vocareum workspace.

## Class Description List:
### Application.java:
**Overview:**
The `Application` class serves as the entry point for the Candy Marketplace Application, orchestrating user interactions, authentication processes, and launching specific functionalities based on user actions. It  establishes communication with the server via the `UserClient` class.

**Dependencies:**
- `javax.swing.*`: Java's GUI components for creating the graphical user interface.
- `java.awt.*`: Abstract Window Toolkit for basic GUI operations.
- `java.awt.event.*`: Event handling classes for GUI interactions.
- `java.io.IOException`: Exception handling for Input/Output operations.

**Class Structure:**
- **Fields:**
   - `UserClient client`: Manages communication with the server.
   - `JFrame signUpDialog, loginDialog`: Frames for displaying sign-up and login dialogs.
   - `JLabel userTypeLabel`: Label indicating the user type during sign-up.
   - `JButton signUpButton, loginButton`: Buttons triggering sign-up and login actions.
   - `JTextField usernameTextField, passwordTextField`: Text fields for entering username and password.
   - `ActionListener actionListener`: Handles button click events.

- **Constructor:**
   - `public Application() throws IOException`: Initializes the `UserClient` instance.

- **Main Method:**
   - `public static void main(String[] args) throws IOException`: Entry point for running the application.

- **Run Method:**
   - `public void run()`: Initiates the application, displaying welcome messages and starting dialogs.

- **Dialog Handling Methods:**
   - `public void showWelcomeMessageDialog()`: Displays a welcome message.
   - `public void showStartingDialog()`: Asks the user to sign up or login.
   - `public void showUserTypeDialog()`: Prompts the user to choose their type (Buyer or Seller).
   - `public void showSignUpDialog()`: Displays the sign-up dialog.
   - `public void showLoginDialog()`: Displays the login dialog.

- **Application Flow Methods:**
   - `public void runMarketplace()`: Initiates the marketplace interface for successful sign-up or login.
   - `public void runControlCenter()`: Initiates the control center interface for successful seller login.

**Note:**
- Ensure proper termination of the application frames to avoid memory leaks.
- The application relies on the `UserClient` class for server communication.

### ControlCenter.java:
## Overview
`ControlCenter.java` is the main class of the Candy Marketplace Application responsible for the graphical user interface (GUI) and user interaction. It leverages Java's Swing library to create a visual representation of the candy marketplace for sellers.

## Features
- **Graphical User Interface (GUI):** Provides an intuitive interface for sellers to manage stores, add and edit candies, view sales, and perform various actions within the Candy Marketplace.

- **Concurrent Operation:** Utilizes multithreading to handle concurrent user interactions, enhancing the application's responsiveness and scalability.

- **Network I/O:** Establishes communication with the server using Java's Socket programming, enabling sellers to interact with the Candy Marketplace over a network.

## Dependencies
- **Java Swing:** The GUI components are built using Java's Swing library.
## Usage
- Upon running the application, sellers are presented with a GUI where they can create stores, add and edit candies, view sales, and perform other store management tasks.
- Interaction is facilitated through buttons, text fields, and dialogs, ensuring a user-friendly experience.
### Marketplace.java: 
Overview
`Marketplace.java` is a crucial part of the Candy Marketplace Application, delivering the graphical user interface (GUI) for buyers. This GUI empowers users to explore the candy marketplace, inspect products, and execute actions like purchasing candies, managing shopping carts, and reviewing purchase history.

## Features
- **Intuitive GUI:** The application showcases an intuitive GUI for user-friendly interactions.
- **Candy Display:** Dynamically exhibits candy products with essential details like store name, candy name, price, and quantity.
- **Sorting and Searching:** Users can sort candies based on various criteria and search for specific candies.
- **Shopping Cart:** Facilitates adding candies to the shopping cart, managing quantities, and completing purchases.
- **Purchase History:** Allows users to view their purchase history, containing details such as candy ID, store name, candy name, and quantity bought.

## How to Use
1. **Sort and Search:** Utilize the dropdown menu for sorting candies and the search bar for finding specific products.
2. **Explore Candies:** Click on candy buttons to access detailed information and make purchase decisions.
3. **Shopping Cart:** Access the shopping cart through the dedicated button to review, modify, and buy selected items.
4. **Purchase History:** View past purchases and export the purchase history using the respective buttons.
### Messages.java:
This Java class, `Messages.java`, serves as a centralized repository for managing and displaying various messages within our Candy Marketplace application. Here's a brief overview:

### **Key Features:**
1. **User Feedback:**
    - Provides visual feedback to users through dialog boxes after specific actions, such as sign-up, login, candy management, and more.

2. **Error Handling:**
    - Displays error messages in case of failed operations, guiding users with clarity on issues like unsuccessful login attempts or failed candy additions.

3. **Import/Export Paths:**
    - Gathers file paths from users when importing or exporting data, enhancing the interactive experience.

### **Usage Guide:**
1. **Successful Actions:**
    - Methods like `showSuccessfulSignUpDialog()` and `showSuccessfulAddCandyDialog()` confirm successful operations.

2. **Unsuccessful Actions:**
    - Methods like `showUnsuccessfulLoginDialog()` and `showUnsuccessfulDeleteCandyDialog()` inform users about unsuccessful attempts.

3. **File Path Input:**
    - `getImportPath()` and `getExportPath()` prompt users to input file paths for data import/export.

4. **Exception Handling:**
    - `showNumberFormatError()` and `showQuantityExceededError()` handle specific errors with clear messages.

### **Data Handling:**
1. **Export/Import Status:**
    - Notifies users about the success or failure of import and export operations.

2. **Purchase Feedback:**
    - Acknowledges users with a thank-you message after a successful purchase.

### **How to Use:**
- Simply call the relevant method based on the desired message or user interaction scenario within the Candy Marketplace application.
### Action.java:

**Overview:**
The `Action.java` file defines an enumeration called `Action` that serves as a comprehensive list of actions and commands for the Candy Marketplace Application. This enumeration encapsulates various functionalities for both buyers and sellers, facilitating communication between the client and server components of the application.

**Enumerated Actions:**

1. **User Authentication:**
    - `LOGIN`: Initiates the login process.
    - `USERNAME` and `PASSWORD`: Represents the username and password for authentication.
    - `INVALID_CREDENTIALS`: Indicates invalid login credentials.
    - `VALID_CREDENTIALS_BUYER` and `VALID_CREDENTIALS_SELLER`: Signify successful login for buyers and sellers, respectively.

2. **Buyer Actions:**
    - `BUYER`: Specifies the user as a buyer.
    - `CANDY_PAGE`: Navigates to the candy page for browsing.
    - `SORT` and `SEARCH`: Commands for sorting and searching candies.
    - `SHOPPING_CART`: Accesses the shopping cart.
    - `PURCHASE_HISTORY`: Displays the buyer's purchase history.

3. **Seller Actions:**
    - `SELLER`: Specifies the user as a seller.
    - `CREATE_STORE`: Initiates the store creation process.
    - `STORE_STATS`: Retrieves store statistics.
    - `EXPORT_HISTORY`: Exports sales history.

4. **Candy Management:**
    - `ADD_CANDY`, `EDIT_CANDY`, `DELETE_CANDY`: Perform operations on candies.
    - `STORE_PAGE`: Navigates to the seller's store page.

5. **Shopping Cart:**
    - `ADD_TO_CART`, `REMOVE_FROM_CART`, `BUY_SHOPPING_CART`: Manages the shopping cart.
    - `BUY_SUCCESSFUL`, `BUY_QUANTITY_EXCEEDS`, `BUY_QUANTITY_INVALID`: Outcomes of the buying process.

6. **Store Management:**
    - `GET_CANDY_ID`: Retrieves candy ID for specific operations.
    - `SORT_STORE_STATS`: Sorts store statistics.

7. **File Operations:**
    - `IMPORT_CSV`, `EXPORT_CSV`: Import and export operations for data.
    - `IMPORT_SUCCESSFUL`, `IMPORT_UNSUCCESSFUL`, `EXPORT_SUCCESSFUL`, `EXPORT_UNSUCCESSFUL`: Outcomes of import/export operations.

8. **Server Communication:**
    - `CLOSE_CONNECTION`: Signal to close the server connection.

**Usage:**
This enumeration provides a structured set of actions that streamline the communication flow between the client and server components of the Candy Marketplace Application. Developers can use these actions to implement specific functionalities seamlessly.
### BuyerThread.java:

This Java class, `BuyerThread`, is an integral component of the Candy Marketplace Application. It extends the `Buyer` class and implements the `Runnable` interface to handle concurrent interactions with buyers.

**Functionality:**
- **View Product Page:**
    - Allows buyers to view detailed information about a specific candy product identified by its unique ID.

- **Sort Store Statistics:**
    - Enables sorting of store statistics based on specified criteria. The results are sent back to the buyer for display.

- **Sort Products:**
    - Facilitates the sorting of available candies based on user-defined preferences. The sorted candy list is transmitted to the buyer.

- **Buy Shopping Cart:**
    - Handles the process of buying items from the shopping cart. The outcome of the transaction, whether successful or exceeding the available quantity, is communicated to the buyer.

**Initialization:**
- Establishes communication channels using `ObjectInputStream` and `ObjectOutputStream` over the provided `Socket`.

- Initializes the `CandyManager` to manage candy-related operations.

- Retrieves a list of stores from the `CandyManager` for reference during operations.

**Execution:**
- The class operates within an infinite loop, constantly listening for buyer actions.

- Actions are received as a `HashMap<Action, Object>` from the client.

- Depending on the action, relevant operations are performed, and responses are sent back to the buyer.

- Exception handling ensures robust communication and error reporting.


This class plays a crucial role in enabling seamless and concurrent buyer interactions within the Candy Marketplace, contributing to the overall functionality and user experience of the application.
### SellerThread.java:

### Overview:

The SellerThread class extends the functionality of a Seller and implements the Runnable interface to manage concurrent threads. It facilitates communication between sellers and the Candy Marketplace through sockets, handling various actions such as retrieving store statistics, sorting statistics, listing sales, importing/exporting CSV files, and more.

### Initialization:

- **Socket Connection:** The class initializes a socket connection for communication and sets up ObjectInputStream and ObjectOutputStream for handling object-based communication.

`public SellerThread(Socket socket, CandyManager cm)`

### Implemented Actions:

- **Store Statistics:** Retrieves and sends seller statistics to the client.

- **Sort Store Statistics:** Sorts seller statistics based on a chosen criterion and sends the updated CandyManager object to the client.

- **List Sales:** Retrieves and sends sales information to the client.

- **Import/Export CSV:** Handles the import and export of data from/to CSV files, providing feedback on the success or failure of the operation.

- **Total Purchase Quantities:** Retrieves and sends the total purchase quantities of candies by a specific buyer.

### Buyer Interaction:

- **Buy Instantly:** Facilitates the immediate purchase of candies by a buyer.

- **Buy Shopping Cart:** Manages the purchase of candies stored in a buyer's shopping cart.

- **Search:** Performs a search for candies based on a keyword and returns the results.

### Additional Methods:

- **Index Retrieval:** Retrieves the index of a candy based on its ID.

### Thread Execution:

The run method executes actions based on the received HashMap<Action, Object>. After processing, it awaits further action from the client.

### Note:

- **Exception Handling:** Proper exception handling is implemented for robust communication.

### How to Use:

1. Create an instance of SellerThread by providing a Socket and a CandyManager.

2. Start the thread using Thread.start().

3. Communicate with the SellerThread by sending appropriate HashMap<Action, Object> objects for desired actions.

4. Receive responses based on the executed actions.
### UserThread.java:

**UserThread Class ReadMe**

This Java class, `UserThread`, represents a threaded user session in a Candy Marketplace application. It extends the `User` class and implements the `Runnable` interface to handle concurrent user interactions.

**TCP Components:**
- `Socket`: Manages the TCP connection.
- `ObjectInputStream` and `ObjectOutputStream`: Handle object-based input and output streams.

**Threads:**
- `thread`: Manages the main user thread.
- `buyerThread` and `sellerThread`: Separate threads for handling buyer and seller actions.

**Attributes:**
- `action`: HashMap containing user actions.
- `cm`: Instance of `CandyManager` to manage candy-related operations.
- `users`: ArrayList of users in the marketplace.
- `isRunning`: Boolean flag to control the thread's execution.

**Constructor:**
- Initializes the socket, input/output streams, and starts the main user thread.

**Methods:**
- `run()`: Overrides the `Runnable` interface's `run` method, handling user actions received over the network.
- `handleSignUp(User user)`: Handles user sign-up, validates credentials, and starts buyer or seller threads accordingly.
- `handleLogin(String username, String password)`: Handles user login, validates credentials, and starts buyer or seller threads accordingly.
- `handleException(Exception e)`: Prints error messages for exception handling.
- `closeResources()`: Closes input/output streams.

**Additional Methods:**
- `registerUser()`: Writes the updated user list to a file (`Users.txt`) for persistent storage.

**Usage:**
1. Create an instance of `UserThread` for each user session.
2. Instantiate the class with a `Socket`, `CandyManager`, and a list of existing users.
3. The class manages user sign-up, login, and thread handling for buyers and sellers.
4. Ensure proper exception handling and resource closure.

**Note:**
- This class assumes the existence of `User`, `Buyer`, `Seller`, `CandyManager`, and `Action` classes.
- Persistent storage of user data is handled through a serialized file (`Users.txt`). Adjust file handling as needed.
### BuyerClient.java:
The `BuyerClient` class is an integral part of the Candy Marketplace Application, serving as the client-side representation for buyers. Here's a brief overview to help you navigate through the functionalities:

**Initialization:**
- The class establishes a connection with the server using a provided socket and links to the shared marketplace.

**Buyer Interactions:**
- The buyer can explore and interact with candies through the GUI, utilizing options such as sorting, searching, managing the shopping cart, and reviewing purchase history.

**Candy Management:**
- The class manages a list of candies through the `CandyManager` instance, facilitating actions like instant purchases, adding to the shopping cart, and removing items.

**Communication with Server:**
- Various methods are implemented to send buyer decisions, candy information, and search queries to the server.

**Actions and Responses:**
- The class handles actions and responses from the server, such as sorting candies, receiving search results, and processing buyer decisions.

**Serialization:**
- Object streams are utilized for efficient serialization and communication between the client and the server.

**Readability and Extension:**
- The code is structured for readability, and the class can be extended to accommodate future enhancements.

**Note:** Ensure proper error handling and exception management during socket and stream operations to maintain the robustness of the buyer-client functionality.
### MainServer.java:
### Overview:

The `MainServer` class functions as the server-side backbone of the Candy Marketplace application. It manages user connections, initializes essential components, and facilitates communication between users and the CandyManager.

### How to Use:

1. **Initialization:**
    - Upon instantiation, a `CandyManager` and an empty list of users are initialized.
    - A default seller user ("jeff") is added for demonstration purposes. Modify the `initializeUsers` method to suit your user management needs.

2. **Start Server:**
    - The `startServer` method launches the server and listens for incoming client connections on the specified port (1234 by default).
    - Users, both buyers and sellers, can connect to this server to interact with the Candy Marketplace.

3. **User Management:**
    - User information is tracked using a `LinkedHashMap` named `userInfo`. Modify or extend user management functionalities as per project requirements.

4. **Read and Write Operations:**
    - The class includes methods for reading (`readFile`) and writing (`writeFile`) the state of the CandyManager to a file. Adjust these methods based on your specific file I/O needs.

### Notes:

- The server runs indefinitely, waiting for incoming client connections.
- Customize user initialization, file I/O, and other functionalities based on project requirements.
- For advanced features, consider extending and modifying the `UserThread` and `CandyManager` classes.

**Note:** *This README provides a basic understanding of the `MainServer` class. For in-depth implementation details, refer to the class documentation and associated methods.*
### SellerClient.java:

This Java class, `SellerClient`, serves as a client-side component for a Candy Marketplace application. It facilitates communication between sellers and the application's control center over a network. Below are key functionalities and methods provided by this class:

1. **Constructor:**
    - `SellerClient(Socket socket, ControlCenter controlCenter)`: Initializes the `SellerClient` with a socket connection and a control center.

2. **Communication Methods:**
    - `sendSellerDecision(String type)`: Sends a decision (string) made by the seller to the control center.
    - `createStore(String storeName, Candy candy, int quantity)`: Informs the control center about the seller's intent to create a store.

3. **Candy Management:**
    - `sendEditStore(String storeName, Candy candy, int quantity)`: Notifies the control center about the seller's intention to edit a store.
    - `sendToGetCandyID()`: Requests the control center for a unique Candy ID.
    - `receiveCandyID()`: Receives a Candy ID from the control center.

4. **Candy Actions:**
    - `sendAddCandy(Candy candy)`: Notifies the control center about the addition of a new candy.
    - `sendEditCandy(Candy candy)`: Informs the control center about edits made to an existing candy.
    - `sendDeleteCandy(Candy candy)`: Signals the control center to delete a specific candy.

5. **Store Operations:**
    - `sendViewSales(Store store)`: Requests sales information for a particular store.
    - `receiveUpdatedSales()`: Receives updated sales information from the control center.
    - `sendViewStoreStatistics(Store store)`: Requests statistics for a specific store.

6. **CSV Operations:**
    - `sendImportCSV(String fileName)`: Requests the control center to import data from a CSV file.
    - `sendExportCSV(String fileName)`: Requests the control center to export data to a CSV file.

7. **General Actions:**
    - `sendAction(Action action, Object object)`: Sends a general action and associated object to the control center.
    - `receiveAction()`: Receives an action from the control center.

This class is designed to facilitate seamless interaction between sellers and the Candy Marketplace application. It leverages object serialization for effective communication and supports various actions related to candy and store management.
### UserClient.java:
**Description:**
The `UserClient` Java class facilitates communication between the Candy Marketplace application and the server. It extends the base `User` class and is an integral part of the overall application architecture.

**Dependencies:**
- `javax.swing.*`: Java Swing for GUI components.
- `java.io.*`: Input/Output classes for handling streams.
- `java.net.*`: Networking classes for socket communication.
- `java.util.*`: Utility classes and data structures.

**Initialization:**
1. Creates instances of candies (e.g., Snickers, Twix) for demonstration purposes.
2. Establishes a connection to the server using a specified hostname and port number.
3. Initializes Object Input and Output Streams for communication with the server.

**Methods:**
1. `getSocket()`: Returns the underlying socket for communication.
2. `getAction()`: Returns the current action associated with the user.
3. `initConnection()`: Initiates a connection to the server, prompting the user for the server's IP address or name.
4. `getHostname()`: Prompts the user to input the server's IP address or name.
5. `sendSignUp(User user)`: Sends a sign-up request along with user details to the server.
6. `sendLogin(User user)`: Sends a login request along with user details to the server.
7. `receiveAction()`: Receives the action from the server, indicating the response to the user's request.

**Usage:**
1. Create an instance of `UserClient` within the Candy Marketplace application.
2. Utilize methods such as `sendSignUp()` and `sendLogin()` to interact with the server.
3. Retrieve the action through `getAction()` to determine server responses.

**Note:**
- The class relies on user inputs for server connection and may continuously prompt until a successful connection is established.
- The user interface components use Swing for simplicity.

**Disclaimer:**
This readme assumes a basic understanding of Java programming and networking concepts. Ensure appropriate error handling and validation mechanisms are implemented for production use.
### Buyer.java:
The Buyer class extends from the User class. This class has two additional fields:
shoppingCart and history. Apart from basic getters and setters and a toString
method, this class determines the actions that a buyer-type user can perform in
the application. Buyers can add items to the shoppingCart field, view available
candy in the marketplace through the viewMarketplace method, search for candy via
the search method, sort products via the sortProducts method, view and sort store
statistics through the viewStoreStatistics and sortStoreStatistics methods.
### Candy.java:
The Candy class is a template for each product being sold in the marketplace.
Field names for the class include name, candyID, store, description, price
and quantity. The candy has basic functionality such as getters and setters
and constructors for each field. Other functionality includes returning the
total quantity of each candy, the toString method and a toCSV method.
### CandyManager.java:
The CandyManager class manages all candy products in the marketplace. The fields for
this class include an ArrayList storing all candy objects, an ArrayList storing all candy
IDs, and an ArrayList storing the quantities for each candy product. There is also another field
called prodCounter that records the number of candy products in the marketplace. The functionality
in this class includes the methods for reading a candy object from the candies.txt file, writing
to the candies.txt file when storing the information for a new candy product, and getting the index for
a specific candy.
### CandyQuantityComparator.java:
The CandyQuantityComparator class implements the Comparator interface for the Candy objects in our marketplace program. It enables the sorting of candies based on their total quantities. This README provides insights into the purpose, usage, and features of the CandyQuantityComparator class.

Constructor

CandyQuantityComparator(boolean ascending): Initializes the comparator with the desired sorting order (ascending or descending).

Method

compare(Candy candy1, Candy candy2): Implements the comparison logic based on the total quantity of candies.

Usage

Initialization:
Create an instance of the CandyQuantityComparator class, specifying the desired sorting order.

ex.
CandyQuantityComparator ascendingComparator = new CandyQuantityComparator(true);
Sorting:

Use the comparator to sort a list of candies.

ex.
Collections.sort(candyList, ascendingComparator);

Important Notes

Ensure that the Candy class has a getTotalQuantity() method to retrieve the total quantity of candies.

Use the comparator in conjunction with the Collections.sort() method for effective sorting.

ex.
List<Candy> candyList = // Populate the list with Candy objects
CandyQuantityComparator ascendingComparator = new CandyQuantityComparator(true);
Collections.sort(candyList, ascendingComparator);

Explore the CandyQuantityComparator class to streamline the sorting of candies based on their total quantities in your marketplace program.

### Purchase.java:

The `Purchase` class represents a purchase of a specific quantity of a particular candy in our marketplace program. This README provides an overview of the `Purchase` class, detailing its purpose, methods, and usage.

Class Overview

Constructor

- *Purchase(Candy candyBought, int quantityBought):* Initializes a purchase with the specified candy and quantity.

Methods

- *getCandyBought():* Returns the candy object associated with the purchase.
- *getQuantityBought():* Returns the quantity of candies bought.
- *setCandyBought(Candy candyBought):* Updates the candy associated with the purchase.
- *setQuantityBought(int quantityBought):* Updates the quantity of candies bought.
- *toString():* Generates a formatted string representation of the purchase for file writing.

Usage

1. **Initialization:**
    - Create an instance of the `Purchase` class, specifying the candy and quantity.

2. **Accessing Information:**
    - Retrieve information about the purchase, such as the associated candy and quantity.

3. **Updating Information:**
    - Modify the candy or quantity associated with the purchase.

4. **String Representation:**
    - Obtain a formatted string representation of the purchase for file writing.

Important Notes

- Ensure that the `Candy` class has appropriate methods for retrieving the candy's name, ID, etc.
- The `toString()` method generates a formatted string with a delimiting character ('$'). Adjust as needed.

Example

Explore the `Purchase` class to manage and retrieve information about candy purchases in your marketplace program. If you encounter any issues or have questions, refer to the documentation or contact the development team for assistance.
### PurchaseHistory.java:
**PurchaseHistory Class README**

The `PurchaseHistory` class is a Java implementation designed to manage and store the purchase history of users in a candy marketplace application. Below is a brief overview of its functionalities:

### Class Overview:
- **Serialization:** The class implements the `Serializable` interface, allowing objects of this class to be converted into a sequence of bytes, which can be easily stored or transmitted.

- **Constructor:**
    - `PurchaseHistory()`: Initializes an empty purchase history.
    - `PurchaseHistory(ArrayList<Purchase> purchases)`: Initializes the purchase history with a provided list of purchases.

### Methods:

1. **getPurchases(): ArrayList<Purchase>**
    - Returns the list of purchases stored in the history.

2. **setPurchases(ArrayList<Purchase> purchases)**
    - Sets the list of purchases in the history.

3. **getNumOfProductsBoughtAt(Store store): int**
    - Calculates and returns the total number of products bought from a specific store.

4. **addPurchase(Purchase purchase): String**
    - Adds a new purchase to the history and returns a confirmation message.

5. **viewHistory(): String**
    - Generates a formatted string displaying the purchase history.

6. **exportHistoryToFile(String username): boolean**
    - Exports the purchase history to a text file named `<username>PurchaseHistory.txt`.

7. **exportHistory(): void**
    - Exports the purchase history to a serialized object file named `purchaseHistory.txt`.

### Usage:
- Create an instance of `PurchaseHistory` to manage the purchase history of a user.
- Add purchases using `addPurchase(Purchase purchase)`.
- View the purchase history using `viewHistory()`.
- Export the purchase history to a text file or serialized object file using `exportHistoryToFile(String username)` or `exportHistory()`.

Note: Ensure proper exception handling when using file-related methods.

For example usage, refer to the provided methods and comments in the source code.
### PurchaseQuantityComparator.java:

The `PurchaseQuantityComparator` class serves as a comparator for sorting sales based on the total purchase quantity of buyers, particularly within the context of a specific store. This README provides a concise overview of the class.

Class Overview

Constructor

- *PurchaseQuantityComparator(Store selectedStore, boolean ascending):* Initializes a comparator for sorting sales based on purchase quantity.

Methods

- *compare(Sale o1, Sale o2):* Compares two sales based on the total purchase quantity of their associated buyer accounts within the selected store.
- *compare(Sale o1, Sale o2, Store selectedStore):* Overloaded method for explicit comparison with a specified store.

Usage

1. **Initialization:**
    - Create an instance of the `PurchaseQuantityComparator` class, specifying the selected store and sorting order.

2. **Comparing Sales:**
    - Use the `compare` method to compare two sales based on total purchase quantity within the selected store.

Important Notes

- The comparator focuses on comparing sales based on the total purchase quantity of the associated buyer accounts within the specified store.
- Ensure that the `Sale` class has appropriate methods for retrieving buyer accounts and their total purchase quantities.

Example

Utilize the `PurchaseQuantityComparator` class when sorting sales based on the total purchase quantity of buyers within a specific store. The comparator facilitates efficient sorting for improved data analysis in your marketplace program.

For any inquiries or issues, refer to the documentation or reach out to the development team for assistance.
### Sale.java:

The `Sale` class, authored by Jeffrey Wu on November 6, 2023, extends the `Purchase` class and represents a sale transaction within a marketplace. This README provides an overview of the class without including code blocks.

Class Overview

Constructors

- *Sale(Candy candyBought, int quantityBought, Buyer buyerAccount):* Initializes a `Sale` instance with information about the candy purchased, quantity bought, and the buyer account.

Methods

- *getBuyerAccount():* Retrieves the buyer account associated with the sale.
- *getTotalRevenue():* Retrieves the total revenue generated by the sale.
- *setBuyerAccount(Buyer buyerAccount):* Updates the buyer account associated with the sale.
- *setTotalRevenue(double totalRevenue):* Updates the total revenue generated by the sale.
- *equals(Object o):* Overrides the `equals` method to compare `Sale` objects based on additional attributes such as total revenue and the buyer account.

toString Method

- *toString():* Generates a formatted string representation of the `Sale` object suitable for file writing, using "$" as the delimiting character.

Usage

1. **Creating a Sale:**
    - Instantiate a `Sale` object by providing details about the candy purchased, quantity bought, and the buyer account.

2. **Accessing Sale Information:**
    - Utilize methods like `getBuyerAccount()` and `getTotalRevenue()` to retrieve information about the sale.

3. **Equality Comparison:**
    - Use the `equals` method to compare `Sale` objects based on their attributes, providing enhanced object equality checks.

4. **String Representation:**
    - Obtain a formatted string representation of the `Sale` object using the `toString` method, suitable for file writing.

Important Notes

- This class extends the `Purchase` class, inheriting its functionality.
- The `equals` method provides a comprehensive comparison of `Sale` objects based on their unique attributes.
- The `toString` method formats sale information for storage or display, using "$" as a delimiting character.

Example

Integrate the `Sale` class within your marketplace program to represent and manage sales transactions efficiently. The class facilitates the storage and retrieval of essential sale details, contributing to a robust and organized marketplace system.

For further inquiries or assistance, refer to the documentation or contact the development team.
### Seller.java:
The Seller class in our marketplace program extends the User class and manages the creation and statistics of stores,
sales, and candies. Below are key functionalities provided by the Seller class:

Constructors

Default Constructor: Creates a seller without any specific details.
Parameterized Constructors: Allow the creation of sellers with specified usernames, passwords, and store managers.

Methods

listStatistics(): Displays statistics for each store, including total revenue and customer information.

listSales(): Lists customer and candy sales, showcasing purchase quantities.
sortSellerStatistics(int choice): Sorts candies and customers based on different criteria, providing flexibility in viewing statistics.

importCSV(String filename): Reads and imports candy data from a CSV file.

exportToCSV(String filename): Exports candy data to a CSV file.
Additional Features

toString(): Overrides the toString() method to represent the Seller object as a string.

Usage

The Seller class integrates seamlessly with the broader marketplace program, allowing sellers to manage stores, view statistics, and handle sales efficiently.

Important Note

Ensure proper file handling and exception management when using the importCSV and exportToCSV methods.
### ShoppingCart.java:

The `ShoppingCart` class, designed for user files and authored without read or write methods, provides functionality to manage and manipulate shopping cart items. This README offers an overview of the class without using code blocks.

Class Overview

Constructors

- *ShoppingCart(ArrayList<Purchase> purchases):* Initializes a `ShoppingCart` with an existing list of purchases.
- *ShoppingCart():* Creates an empty `ShoppingCart` with no initial purchases.

Methods

- *getPurchases():* Retrieves the list of purchases in the shopping cart.
- *setPurchases(ArrayList<Purchase> candiesBought):* Updates the list of purchases in the shopping cart.
- *addItem(Purchase purchase):* Adds a new item (purchase) to the shopping cart.
- *removeItem(Purchase purchase):* Removes a specific item (purchase) from the shopping cart.
- *findStores(Store store):* Finds purchases in the shopping cart that are associated with a specific store.

toString Method

- *toString():* Generates a formatted string representation of the `ShoppingCart` object suitable for file writing, using ";" as the delimiting character.

Usage

1. **Creating a ShoppingCart:**
    - Instantiate a `ShoppingCart` object, either empty or with an existing list of purchases.

2. **Accessing Purchases:**
    - Utilize methods like `getPurchases()` to retrieve the list of purchases in the shopping cart.

3. **Modifying the ShoppingCart:**
    - Add new purchases using `addItem(Purchase purchase)` or remove specific purchases with `removeItem(Purchase purchase)`.

4. **Finding Store-Associated Purchases:**
    - Use `findStores(Store store)` to identify purchases in the shopping cart associated with a particular store.

5. **String Representation:**
    - Obtain a formatted string representation of the `ShoppingCart` object using the `toString` method, suitable for file writing.

Important Notes

- The `ShoppingCart` class efficiently manages purchases within a user's shopping cart.
- Methods such as `addItem` and `removeItem` facilitate the dynamic modification of the shopping cart.
- The `findStores` method helps identify purchases associated with a specific store.

Example

Incorporate the `ShoppingCart` class into your marketplace program to empower users to manage their shopping carts effectively. The class supports operations such as adding, removing, and finding purchases, contributing to a seamless user experience.

For further inquiries or assistance, refer to the documentation or contact the development team.
### Store.java:

The `Store` class manages store-related information and operations, facilitating the organization of sales, products, and revenue. This README provides a concise overview of the class without using code blocks.

Class Overview

Constructors

- *Store():* Initializes a store with default values (empty name, indexes, and sales lists).
- *Store(String name):* Creates a store with the given name and empty lists for indexes and sales.
- *Store(String name, ArrayList<Integer> indexes, ArrayList<Sale> sales):* Creates a store with specified values for name, indexes, and sales.

### Getters and Setters

- *getName():* Retrieves the name of the store.
- *getSales():* Retrieves the list of sales associated with the store.
- *getIndexes():* Retrieves the list of indexes associated with the store.
- *setName(String name):* Sets the name of the store.
- *setIndexes(ArrayList<Integer> indexes):* Sets the list of indexes associated with the store.
- *setSales(ArrayList<Sale> sales):* Sets the list of sales associated with the store.

### Methods

- *addSale(Sale sale):* Adds a sale to the list of sales associated with the store.
- *totalRevenue():* Calculates the total revenue generated by the store based on sales and associated candies' prices.
- *deleteCandy(int candyID):* Removes a candy from the store's inventory based on the candy's ID.
- *addCandy(Candy newCandy, int newQuantity, int newID):* Adds a new candy to the store's inventory with the specified quantity and ID.
- *editCandy(int candyID, Candy updatedCandy, int newQuantity):* Modifies an existing candy in the store's inventory based on the candy's ID.
- *getNumberOfProductsSold():* Retrieves the total number of products sold by the store.

### toString Method

- *toString():* Generates a formatted string representation of the `Store` object. Delimiter characters include "," between fields, "}" between candy IDs, and ";" between sales.

## Usage

1. **Creating a Store:**
    - Instantiate a `Store` object using one of the available constructors.

2. **Accessing Store Information:**
    - Use getter methods (`getName()`, `getSales()`, `getIndexes()`) to retrieve information about the store.

3. **Managing Sales and Candies:**
    - Utilize methods such as `addSale`, `deleteCandy`, `addCandy`, and `editCandy` to manage sales and candies associated with the store.

4. **Calculating Revenue:**
    - Employ the `totalRevenue()` method to calculate the total revenue generated by the store.

5. **String Representation:**
    - Obtain a formatted string representation of the `Store` object using the `toString` method.

## Important Notes

- The `Store` class efficiently organizes information related to store operations.
- Methods such as `addSale` and `totalRevenue` contribute to tracking and analyzing store performance.
- The `toString` method ensures a standardized string representation for further processing or display.

## Example

Incorporate the `Store` class into your marketplace program to manage sales, track revenue, and efficiently handle store-related operations. The class provides essential functionality for maintaining an organized and dynamic store environment.

For further inquiries or assistance, refer to the documentation or contact the development team.
### StoreManager.java:
**StoreManager Class**

The `StoreManager` class functions as a container for managing a collection of stores within a marketplace. This README provides a concise overview of the class without using code blocks.

## Class Overview

### Constructors

- *StoreManager():* Initializes a `StoreManager` with an empty list of stores.
- *StoreManager(ArrayList<Store> stores):* Creates a `StoreManager` with a specified list of stores.

### Getters and Setters

- *getStores():* Retrieves the list of stores managed by the `StoreManager`.
- *setStores(ArrayList<Store> stores):* Sets the list of stores managed by the `StoreManager`.

### Methods

- *addStore(Store store):* Adds a store to the list of stores managed by the `StoreManager`.
- *removeStore(Store store):* Removes a store from the list of stores managed by the `StoreManager`.
- *getAllStores():* Retrieves a list of all unique stores associated with candies in the marketplace.

### toString Method

- *toString():* Generates a formatted string representation of the `StoreManager` object. Delimiter characters include "," between stores and "#" between different store representations.

## Usage

1. **Creating a StoreManager:**
    - Instantiate a `StoreManager` object using one of the available constructors.

2. **Accessing Managed Stores:**
    - Use getter methods (`getStores()`) to retrieve the list of stores managed by the `StoreManager`.

3. **Adding and Removing Stores:**
    - Utilize methods such as `addStore` and `removeStore` to manage the collection of stores.

4. **Retrieving All Stores:**
    - Utilize the `getAllStores` method to obtain a list of all unique stores associated with candies in the marketplace.

5. **String Representation:**
    - Obtain a formatted string representation of the `StoreManager` object using the `toString` method.

## Important Notes

- The `StoreManager` class serves as a central entity for managing and organizing stores within a marketplace.

- Methods such as `addStore` and `removeStore` provide flexibility in dynamically updating the list of managed stores.

- The `getAllStores` method efficiently retrieves a list of all unique stores associated with candies.

- The `toString` method ensures a standardized string representation for further processing or display.

## Example

Incorporate the `StoreManager` class into your marketplace program to efficiently manage a collection of stores. The class provides essential functionality for organizing and manipulating stores within the marketplace.

For further inquiries or assistance, refer to the documentation or contact the development team.
### TooManyAttemptsException.java:

The TooManyAttemptsException class represents a custom exception that extends the Java Exception class. It is designed to be thrown when a specific action encounters an excessive number of attempts, signaling an exceptional condition within the program. This README provides a concise overview of the class.

Class Overview

Constructor: TooManyAttemptsException(String message) - Initializes a TooManyAttemptsException object with a specified error message.

Inheritance: Inherits from the Java Exception class.

Usage:

Creating an Exception: Instantiate a TooManyAttemptsException object by providing a descriptive error message.
Throwing the Exception: Use the throw statement to raise a TooManyAttemptsException at a specific point in the code when an excessive number of attempts is detected.
Exception Handling: Implement a try-catch block to handle the thrown exception appropriately.

## Authors:
Pablo Garces

Nathan Park

Aadiv Reki

Jeffrey Wu

Jaden Ye
