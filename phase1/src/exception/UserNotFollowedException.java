package exception;

public class UserNotFollowedException extends Exception{

    public UserNotFollowedException() {
        super();
    }

    public UserNotFollowedException(String message) {
        super(message);
    }
}
