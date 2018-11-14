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
 * Holds a password. Has an isValid() function that checks if the password is of
 * correct length and contains the correct characters. Size = 8-10. At least 1
 * capital and at least 1 special character ( !@#$%^&*() ) NO SPACES.
 */
public class ComplexPassword extends AbstractPassword {

    public ComplexPassword(String password) throws ShortPasswordException,
            LongPasswordException,
            NoUppercaseException,
            NoNumberException,
            NoSpecialCharException,
            HasSpaceException {
        try {
            setPassword(password);
        } catch (ShortPasswordException | LongPasswordException
                | NoUppercaseException | NoNumberException
                | NoSpecialCharException | HasSpaceException ex) {
            throw ex;
        }
    }

    // Sets password first. Checks if it's valid. If isValid() throws no
    // exceptions, then the catch block isn't executed. If it's not valid,
    // isValid() throws an exception and password is set to "INVALID PASSWORD."
    // "INVALID PASSWORD" acts as a sort of flag so that the user can be
    // prompted for a new password entry
    @Override
    public void setPassword(String password) throws ShortPasswordException,
            LongPasswordException,
            NoUppercaseException,
            NoNumberException,
            NoSpecialCharException,
            HasSpaceException {
        this.password = password;
        try {
            isValid();
            // Catch the exception and print its message
        } catch (ShortPasswordException | LongPasswordException
                | NoUppercaseException | NoNumberException
                | NoSpecialCharException | HasSpaceException ex) {
            throw ex;
        }
    }

    @Override
    public boolean isValid() throws ShortPasswordException,
            LongPasswordException,
            NoUppercaseException,
            NoNumberException,
            NoSpecialCharException,
            HasSpaceException {

        // If the password is too short
        if (password.length() < MIN_SIZE) {
            throw new ShortPasswordException("Password must be at "
                    + "least eight characters long.");
        }
        if (password.length() > MAX_SIZE) {
            throw new LongPasswordException("Password cannot be longer "
                    + "than ten characters.");
        }
        // If there are no uppercase characters
        if (!hasUpper()) {
            throw new NoUppercaseException("Pasword must contain at "
                    + "least one uppercase character.");
        }
        // If it contains no number
        if (!hasNumber()) {
            throw new NoNumberException("Password must contain at "
                    + "least one number.");
        }
        // If it has no special character or has a space
        if (!hasSpecialChar()) {
            throw new NoSpecialCharException("Password must contain at "
                    + "least one special character ( !@#$%^&*() ). ");
        }
        if (hasSpaceChar()) {
            throw new HasSpaceException("Password must not contain a space.");
        }
        return true;
    }

    // If it finds one uppercase character, return true.
    // If no uppercase is found, return false.
    public boolean hasUpper() {
        // toCharArray lets for-each be possible to use on a string
        for (char upper : password.toCharArray()) {
            if (java.lang.Character.isUpperCase(upper)) {
                return true;
            }
        }
        return false;
    }

    // If there is even one number, return true.
    // Otherwise, return false.
    public boolean hasNumber() {
        for (char number : password.toCharArray()) {
            if (java.lang.Character.isDigit(number)) {
                return true;
            }
        }
        return false;
    }

    // Look at a character in the password and compare it to each of the 
    // chars in the specialChars list. If there is just one match, return true.
    // Otherwise, return false.
    public boolean hasSpecialChar() {
        String specialChars = "!@#$%^&*()";
        for (char stringChar : password.toCharArray()) {
            for (char specChar : specialChars.toCharArray()) {
                if (stringChar == specChar) {
                    return true;
                }
            }
        }
        return false;
    }

    // If there is one space, return true.
    // Otherwise, return false
    public boolean hasSpaceChar() {
        for (char space : password.toCharArray()) {
            if (space == ' ') {
                return true;
            }
        }
        return false;
    }

}
