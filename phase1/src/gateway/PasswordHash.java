package gateway;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash implements IHash {
    /**
     * Returns a hashed String for the input string parameter
     *
     * @param inputString  a string that needs to be hashed
     * @return the a SHA-512 hash of the input string
     */
    @Override
    public String hash(String inputString) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(inputString.getBytes());
            byte[] bytes = md.digest();
            StringBuilder out = new StringBuilder();
            for (byte aByte : bytes) {
                out.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return out.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

