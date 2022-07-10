package exception;
public class UsernameExistsException extends Exception{
    /**
     * Exception thrown when an action is requested yet
     * invalid since a username already exists
     *
     * @param errorMessage Description of why error was thrown
     */
    public UsernameExistsException(String errorMessage) {
        super(errorMessage);
    }
}