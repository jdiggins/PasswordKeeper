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
 * Thrown if there is a space in the password.
 */
public class HasSpaceException extends Exception {

    public HasSpaceException(String message) {
        super(message);
    }
}
