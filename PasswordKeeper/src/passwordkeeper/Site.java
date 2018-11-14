/* 
 * Password Keeper
 * Team Couds of Paradise
 * Henry O'Connor, Bernard Heres, John Diggins
 * 04 / 13 / 2017
 */
package passwordkeeper;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
 *
 * @author Bernard Heres, Henry O'Connor, John Diggins
 *
 * Holds website data. Uses a SimplePassword member variable because these
 * passwords never need to be validated by this program.
 *
 */
public class Site {

    public String website;
    public String username;
    public SimplePassword password;
    public String notes;

    // Default constructor
    public Site() {
        website = "";
        username = "";
        password = new SimplePassword("");
        notes = "";
    }

    public Site(String website, String username,
            SimplePassword password, String notes) throws MalformedURLException {
        try {
            setWebsite(website);
        } catch (java.net.MalformedURLException ex) {
            throw ex;
        }
        setUsername(username);
        setPassword(password);
        setNotes(notes);
    }

    // Makes sure the website link is valid.
    // Makes a URL object. If it throws a MalformedURLException, it's invalid.
    // "INVALID URL" acts as a flag so the user can be prompted again.
    public void setWebsite(String website) throws MalformedURLException {
        try {
            java.net.URL url = new java.net.URL(website);
        } catch (java.net.MalformedURLException ex) {
            throw ex;
        }
        this.website = website;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(SimplePassword password) {
        this.password = password;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Checks if the file already holds website data
    // Compares each line to the website. If there's a match, the data exists.
    public boolean doesDataExist(java.io.File file) {
        try (java.util.Scanner scanner = new java.util.Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(website)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.getStackTrace();
        }
        return false;
    }

    // Saves the data to the user's file by appending
    public void saveToFile(String fileName) {
        if (!doesDataExist(new java.io.File(fileName))) {
            try (java.io.PrintWriter output
                    = new java.io.PrintWriter(
                            new java.io.FileWriter(fileName, true))) {
                output.println("Website: " + website);
                output.println("Username: " + username);
                output.println(password.toString());
                output.println("Notes: " + notes);
                output.println();
            } catch (java.io.IOException ex) {
                ex.getStackTrace();
            }
        }
    }
}
