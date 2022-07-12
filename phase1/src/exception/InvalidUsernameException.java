package exception;

public class InvalidUsernameException extends Exception{
    /**
     * Exception thrown when an action is requested yet
     * invalid since username is invalid
     * see UsernameExistsException and UsernameNotFoundException
     * for more specific exceptions
     *
     * @param errorMessage Description of why error was thrown
     */
    public InvalidUsernameException(String errorMessage) {
        super(errorMessage);
    }
}
