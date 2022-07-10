package exception;

public class AccountBannedException extends Exception {
    /**
     * Exception thrown when a users account is banned and hence
     * has reduced permissions
     *
     * @param errorMessage Description of why error was thrown
     */
    public AccountBannedException(String errorMessage) {
        super(errorMessage);
    }
}
