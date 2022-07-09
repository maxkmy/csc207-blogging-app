package gateway;

public class Sleeper implements ISleeper {
    /**
     * @inheritDoc
     */
    @Override
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("An error has occurred with the program. Please try again.");
        }
    }
}
