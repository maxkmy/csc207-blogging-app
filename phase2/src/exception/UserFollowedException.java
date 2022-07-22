package exception;

public class UserFollowedException extends Exception{
    /**
     * Exception thrown when an action is requested yet
     * invalid since a user is being followed
     *
     * @param errorMessage Description of why error was thrown
     */
    public UserFollowedException(String errorMessage) {
        super(errorMessage);
    }
}
