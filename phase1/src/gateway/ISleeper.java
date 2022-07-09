package gateway;

public interface ISleeper {
    /**
     * Pauses the program based on a set input of time in milliseconds.
     *
     * @param milliseconds  the number of milliseconds for which the program should be paused
     */
    void sleep(int milliseconds);
}
