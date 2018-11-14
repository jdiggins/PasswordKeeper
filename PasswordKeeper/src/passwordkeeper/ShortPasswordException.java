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
 * Thrown if the password is too short
 */
public class ShortPasswordException extends Exception {

    public ShortPasswordException(String message) {
        super(message);
    }
}
