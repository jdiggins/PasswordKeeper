/* 
 * Password Keeper
 * Team Couds of Paradise
 * Henry O'Connor, Bernard Heres, John Diggins
 * 04 / 13 / 2017
 */
package passwordkeeper;

/**
 *
 * @author Bernard Heres, Henry O'Connor, John Diggins
 * Thrown if there are no special characters in the password.
 */
public class NoSpecialCharException extends Exception {

    public NoSpecialCharException(String message) {
        super(message);
    }
}
