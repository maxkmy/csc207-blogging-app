package exception;

public class IncorrectPasswordException extends Exception{
    /**
     * Exception thrown when a user provides an incorrect password
     *
     * @param errorMessage Description of why error was thrown
     */
    public IncorrectPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
