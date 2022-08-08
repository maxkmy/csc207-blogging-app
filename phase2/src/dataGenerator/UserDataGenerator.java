package dataGenerator;

import entities.Account;
import gateway.IWriter;
import gateway.Writer;
import gateway.PasswordHash;

import java.util.HashMap;

public class UserDataGenerator {
    public static void main(String[] args) {
        IWriter writer1 = new Writer("data/userData.txt");
        IWriter writer2 = new Writer("test/testData/testAccountData.txt");
        Account admin = new Account("admin", new PasswordHash().hash("password"));
        admin.promoteToAdmin();
        HashMap<String, Account> map = new HashMap<>();
        map.put("admin", admin);
        writer1.write(map);
        writer2.write(map);
    }
}
