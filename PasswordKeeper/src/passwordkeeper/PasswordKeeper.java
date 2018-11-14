/* 
 * Password Keeper
 * Team Couds of Paradise
 * Henry O'Connor, Bernard Heres, John Diggins
 * 04 / 13 / 2017
 *
 *
 * Create a login screen, allow user to register new account
 * Verify Password
 *      - 8 to 10 alphanumeric characters, 1 capital letter, 1 number
 *      - 1 special character, NO spaces
 * Once logged in, user can:
 *   - add new site login info (site, login, password, notes)
 *   - list all site login info
 *   - search for a login based on site
 * Required Classes & Methods:
 *   - Abstract Password Class - contains print method for passwords and
 *       validation interface
 *   - Simple Password Class - allows for unvalidated passwords
 *   - Complex Password Class - has validation method that throws exception
 *   - Site class that includes website, username, password, and notes
 *        - should validate website URL and throw exception if invalid
 *   - A method to save usernames and passwords for Password Keeper
 *   - A method to save Site class data as text
 *
 * This project demonstrates:
 *   - Objects & Classes
 *   - Arrays
 *   - Inheritance
 *   - Polymorphism
 *   - JavaFX
 *   - Exception Handling
 *   - Text I/O
 *   - Abstract classes
 *   - Interfaces
 */
package passwordkeeper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 *
 * @author Bernard Heres, Henry O'Connor, John Diggins
 */
public class PasswordKeeper extends Application {

    @Override
    public void start(Stage primaryStage) {

        final int height = 500;          // height of scene
        final int width = 500;           // with of scene
        final int textFieldWidth = 150;  // width of login text fields

        // Password Keeper file
        java.io.File keeperFile = new java.io.File("PasswordKeeper.txt");

        // Initial login screen
        // Login/Register buttons
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        loginButton.setMinWidth(textFieldWidth / 2);
        registerButton.setMinWidth(textFieldWidth / 2);
        HBox homeHBox = new HBox();
        homeHBox.getChildren().addAll(loginButton, registerButton);
        homeHBox.setAlignment(Pos.CENTER);

        // Username/password text boxes and labels
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        usernameField.setMaxWidth(textFieldWidth);
        passwordField.setMaxWidth(textFieldWidth);
        usernameField.setAlignment(Pos.BASELINE_RIGHT);
        passwordField.setAlignment(Pos.BASELINE_RIGHT);
        
        // VBox to contain TextFields, error text, buttons
        Label userPrompt = new Label();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(usernameField, passwordField, homeHBox, userPrompt);
        vBox.setAlignment(Pos.TOP_CENTER);
        
        // BorderPane to contain login screen, switch to user menu, 
        // and holds home / logout buttons at bottom once user logs in
        BorderPane root = new BorderPane();
        root.setCenter(vBox);
        root.setPadding(new Insets(100, 100, 100, 100));
        
        // Home and Logout button for user once logged in
        Button homeButton = new Button("Home");
        Button logoutButton = new Button("Logout");
        Button quitButton = new Button("Quit");
        Pane spacerLeft = new Pane(); // aligns buttons to left and right
        Pane spacerRight = new Pane();
        HBox homeLogoutMenu = new HBox();
        HBox.setHgrow(spacerLeft, Priority.SOMETIMES); // fill space
        HBox.setHgrow(spacerRight, Priority.SOMETIMES);
        
        // only add quit button until user logs in
        homeLogoutMenu.getChildren().addAll( spacerRight, quitButton);
        homeLogoutMenu.setPrefWidth(Double.MAX_VALUE);
        
        // quit button funcion
        quitButton.setOnMouseClicked(e -> System.exit(0));
        
        // set home, logout, quit menu to bottom
        root.setBottom(homeLogoutMenu);

        // When login is pressed, it takes the values in the boxes
        // and checks if the login info is valid
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Get the username and password from textboxes
                String username = usernameField.getText();
                String password = passwordField.getText();
                boolean valid = validateLogin(username, password, keeperFile);
                // If login is valid, move on to the next scene
                if (valid) {
                    
                    // user menu buttons
                    Button addButton = new Button("Add website");
                    Button listAllButton = new Button("List all websites");
                    Button searchButton = new Button("Search websites");

                    root.setCenter(addSearchListScene(addButton,
                            listAllButton, searchButton, height, width));
                    root.setBottom(homeLogoutMenu);
                    
                    // add home & logout button
                    homeLogoutMenu.getChildren().clear();
                    homeLogoutMenu.getChildren().addAll(homeButton, spacerLeft, 
                logoutButton, spacerRight, quitButton);
                    // Add website button is pressed
                    // Username is passed in as the file name because 
                    // username.txt is where the passwords are stored
                    addButton.setOnAction((ActionEvent e2) -> {
                        // New scene
                        root.setCenter(addWebsite(height, width, username));
                    });
                    listAllButton.setOnAction((ActionEvent e2) -> {
                        root.setCenter(listAll(height, width, username));
                    });
                    searchButton.setOnAction((ActionEvent e2) -> {
                        root.setCenter(searchFile(height, width, username));
                    });
                    homeButton.setOnAction((ActionEvent e2) -> {
                        root.setCenter(addSearchListScene(addButton,
                                listAllButton, searchButton, height, width));
                        userPrompt.setText("");
                    });
                    logoutButton.setOnAction((ActionEvent e2) -> {
                        root.setCenter(vBox);
                        usernameField.clear();
                        passwordField.clear();
                        
                        // get rid of home & logout button, leave quit button
                        homeLogoutMenu.getChildren().clear();
                        homeLogoutMenu.getChildren().addAll( spacerRight, quitButton);
                    });

                } else { // user login failed
                    userPrompt.setText("Invalid credentials");
                }
            }
        });
        // Similar to login button, but registers the user instead.
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Get the username and password from textboxes
                String username = usernameField.getText();
                try {
                    ComplexPassword cPass
                            = new ComplexPassword(passwordField.getText());
                    if (!registerUser(username, cPass.getPassword(), keeperFile)) {
                        userPrompt.setText("Username already taken.");
                        // If everything is valid, create the account
                    } else {
                        userPrompt.setText("Account made.");
                    }

                } catch (ShortPasswordException | LongPasswordException
                        | NoUppercaseException | NoNumberException
                        | NoSpecialCharException | HasSpaceException ex) {
                    userPrompt.setText(ex.getMessage());
                }
            }
        });

        Scene scene = new Scene(root, height, width);

        primaryStage.setTitle("Password Keeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

  

    // Gets information from text boxes and populates the member variables
    // of a Site object with that data. If the data is invalid, an exception
    // is caught. 
    public VBox addWebsite(int height, int width, String fileName) {
        TextField websiteField = new TextField();
        TextField usernameField = new TextField();
        TextField passwordField = new TextField();
        TextField notesField = new TextField();

        websiteField.setPromptText("Website");
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        notesField.setPromptText("Notes");
        
        Button saveButton = new Button("Save");
        Label userPrompt = new Label();

        saveButton.setOnAction((ActionEvent e) -> {
            String website = websiteField.getText();
            String username = usernameField.getText();
            SimplePassword password = new SimplePassword(passwordField.getText());
            String notes = notesField.getText();
            try {
                Site site = new Site(website, username, password, notes);
                site.saveToFile(fileName + ".txt");
                userPrompt.setText("Website data saved");
                websiteField.clear();
                usernameField.clear();
                passwordField.clear();
                notesField.clear();
            } catch (MalformedURLException ex) {
                userPrompt.setText("Invalid URL");
            }
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(websiteField, usernameField,
                passwordField, notesField, saveButton, userPrompt);

        return vBox;
    }

    // Saves all the data in a file to a ListView.
    // Adds the ListView to the center of a BorderPane, put that pane in a scene.
    // Returns the scene.
    public VBox listAll(int height, int width, String fileName) {
        fileName = fileName + ".txt";

        ListView<String> list = new ListView<>();
        File file = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                list.getItems().add(line);
            }
        } catch (java.io.IOException ex) {
        }

        VBox vBox = new VBox();
        vBox.getChildren().add(list);
        return vBox;
    }

    // Returns a scene with a TextField and a search button
    public VBox searchFile(int height, int width, String fileName) {

        fileName = fileName + ".txt";
        TextField searchBox = new TextField();
        searchBox.setPromptText("Enter a website");
        Button searchButton = new Button("Search");
        Label userPrompt = new Label();
        File file = new File(fileName);

        searchButton.setOnAction((ActionEvent e) -> {
            try {
                Scanner scanner = new Scanner(file);
                String siteToFind = searchBox.getText();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    // Only look at the website line
                    if (line.contains("Website:")) {
                        // See if line contains the input String
                        if (line.toLowerCase().contains(siteToFind.toLowerCase())) {
                            userPrompt.setText(line + "\n" + scanner.nextLine()
                                    + "\n" + scanner.nextLine());
                            break;      // Information found, leave the loop
                        }
                    }
                    // End of loop if break doesn't occur
                    userPrompt.setText("No login information found.");
                }

            } catch (java.io.IOException ex) {
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchBox, searchButton, userPrompt);
        return vBox;
    }

    public VBox addSearchListScene(Button btn1, Button btn2, Button btn3,
            int height, int width) {
        Button addButton = btn1;
        Button listAllButton = btn2;
        Button searchButton = btn3;

        VBox vBox = new VBox();
        vBox.getChildren().addAll(addButton, listAllButton, searchButton);

        return vBox;
    }

    // Creates a file to hold the user's usernames, passwords, websites, and notes.
    // The file is the user's name with .txt appended. 
    public void createUserFile(String username) {
        try {
            File file = new File(username + ".txt");
            if (file.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (java.io.IOException ex) {
        }
    }

    // The file will save records in a single line with a space
    // between the username and password
    // Because passwords cannot have spaces, the final space acts as a delimeter,
    // separating the username from the password.
    // Username: JohnSmith; Password: hunter2; Saves as: JohnSmith hunter2
    // Username: John Smith; Password: hunter2; Saves as: John Smith hunter2
    // Both cases are "findable" in the file
    public boolean validateLogin(String username, String password, File file) {
        String usernamePassword = username + " " + password;
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } // Means no users are registered because this file doesn't exist
        catch (java.io.FileNotFoundException ex) {
            return false;
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals(usernamePassword)) {
                return true;
            }
        }
        scanner.close();
        return false;
    }

    // If the username is taken, it will be in the file
    public boolean isUsernameTaken(String username, File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } // Means no users are registered because this file doesn't exist
        catch (java.io.FileNotFoundException ex) {
            return false;
        }
        // Look at the line, but only as much as the username is long.
        // Usernames cannot have any spaces (as is the case with many usernames)
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // Look for the last space in the line, 
            // which is the password delimeter
            for (int i = line.length() - 1; i >= 0; i--) {
                if (line.charAt(i) == ' ') {
                    // Set the substring from 0 to the last space to line
                    line = line.substring(0, i);
                    break;
                }
            }
            if (line.equals(username)) {
                return true;
            }
        }
        scanner.close();
        return false;
    }

    // Saves user's information to the keeperFile if it's not already in there.
    // Returns false if the user is already registered/username is taken
    public boolean registerUser(String username, String password, File file) {
        if (isUsernameTaken(username, file)) {
            return false;
        } else {
            try (
                    PrintWriter output
                    = new PrintWriter(
                            new java.io.FileOutputStream(file, true));) {
                output.println(username + " " + password);

            } catch (FileNotFoundException ex) {
                return false;
            }
        }
        return true;   // Writing was successful
    }

}
