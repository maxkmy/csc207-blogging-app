package exception;

public class AccountBannedException extends Exception {
    public AccountBannedException(String errorMessage) {
        super(errorMessage);
    }
}
