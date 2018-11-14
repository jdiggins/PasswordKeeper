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
 * Holds unvalidated passwords
 */
public class SimplePassword extends AbstractPassword {
    public SimplePassword(String password){
        this.password = password;
    }
    
    // This class doesn't validate passwords
    @Override
    public boolean isValid(){
        return true;
    }
}
