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
 */
public interface Validate {

    public abstract boolean isValid() throws ShortPasswordException, 
            LongPasswordException, 
            NoUppercaseException, 
            NoNumberException, 
            NoSpecialCharException, 
            HasSpaceException;
}
