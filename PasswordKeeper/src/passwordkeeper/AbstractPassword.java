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
 * 
 * Contains a print mechanism for a password and an
 * interface for validating passwords.
 */
public abstract class AbstractPassword implements Validate {

    protected String password;
    protected final int MIN_SIZE = 8;
    protected final int MAX_SIZE = 10;

    @Override
    public abstract boolean isValid() throws ShortPasswordException,
            LongPasswordException,
            NoUppercaseException,
            NoNumberException,
            NoSpecialCharException,
            HasSpaceException;
    
    public void setPassword(String password) throws ShortPasswordException,
            LongPasswordException,
            NoUppercaseException,
            NoNumberException,
            NoSpecialCharException,
            HasSpaceException{
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }

    public String toString() {
        return "Password: " + password;
    }
}
