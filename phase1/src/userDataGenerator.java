import entities.Account;
import gateway.IWriter;
import gateway.Writer;
import gateway.PasswordHash;

import java.util.HashMap;

public class userDataGenerator {
    public static void main(String[] args) {
        IWriter writer = new Writer("data/userData.txt");
        Account admin = new Account("admin", new PasswordHash().hash("password"));
        admin.promoteToAdmin();
        HashMap<String, Account> map = new HashMap<>();
        map.put("admin", admin);
        writer.write(map);
    }
}
