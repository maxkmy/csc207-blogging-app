package gateway;

public interface IHash {
    /**
     * Return a hashed String based on the input string.
     *
     * @param inputString a string that needs to be hashed
     * @return            the hashed string of the input string
     */
    String hash(String inputString);
}
