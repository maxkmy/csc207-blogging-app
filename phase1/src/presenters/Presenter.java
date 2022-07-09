package presenters;

public class Presenter {
    public void inlinePrint(String message) {
        System.out.print(message);
    }

    public void blockPrint(String message) {
        System.out.println(message);
    }

    public void printMessages(Iterable<String> messages, String separator) {
        for (String message : messages) {
            System.out.println(separator);
            System.out.println(message);
        }
    }
}
