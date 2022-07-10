package exception;

public class UserNotFollowedException extends Exception{
    /**
     * Exception thrown when an action is requested yet
     * invalid since a user is not being followed
     *
     * @param errorMessage Description of why error was thrown
     */
    public UserNotFollowedException(String errorMessage) {
        super(errorMessage);
    }
}
