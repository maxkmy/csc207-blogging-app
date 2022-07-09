package exception;

public class UsernameNotFoundException extends Exception{
    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }
}
