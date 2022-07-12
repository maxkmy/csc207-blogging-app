package exception;

public class ResultNotFoundException extends Exception{
    /**
     * Exception thrown when a search query is performed yet
     * invalid since search query is not found
     *
     * @param errorMessage Description of why error was thrown
     */


    public ResultNotFoundException(String errorMessage) { super(errorMessage); }
}
