package exception;

public class UserIsAdminException extends Exception{

    public UserIsAdminException() {
        super();
    }

    public UserIsAdminException(String errorMessage) {
        super(errorMessage);
    }
}