package presenters;

public class Presenter {
    /**
     * Prints a message to the user. After printing, a new line is not created.
     *
     * @param message the message to be printed to the user.
     */
    public void inlinePrint(String message) {
        System.out.print(message);
    }

    /**
     * Prints a message to the user. After printing, a new line is created.
     *
     * @param message the message to be printed to the user.
     */
    public void blockPrint(String message) {
        System.out.println(message);
    }

    /**
     * Prints a list of messages to the user. Each message is separated by some separator.
     *
     * @param messages  the list of messages to be printed.
     * @param separator a string that separates each message with messages.
     */
    public void printMessages(Iterable<String> messages, String separator) {
        for (String message : messages) {
            System.out.print(separator);
            System.out.print(message);
        }
    }
}
