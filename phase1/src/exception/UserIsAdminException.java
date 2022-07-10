package exception;

public class UserIsAdminException extends Exception{
    /**
     * Exception thrown when an action is requested yet
     * invalid since a user is admin
     *
     * @param errorMessage Description of why error was thrown
     */
    public UserIsAdminException(String errorMessage) {
        super(errorMessage);
    }
}