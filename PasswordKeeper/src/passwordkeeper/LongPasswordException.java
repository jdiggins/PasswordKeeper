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
 * Thrown if password is too long
 */
public class LongPasswordException extends Exception {
    
    public LongPasswordException(String message) {
        super(message);
    }
}
