package exception;

public class UsernameNotFoundException extends Exception{
    /**
     * Exception thrown when an action is requested yet
     * invalid since a username is not found
     *
     * @param errorMessage Description of why error was thrown
     */
    public UsernameNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
