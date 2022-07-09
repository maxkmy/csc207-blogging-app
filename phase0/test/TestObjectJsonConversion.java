import entities.Account;
import gateway.ObjectToJson;
import gateway.JsonToObject;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestObjectJsonConversion {
    @Test
    public void testAccountToJson(){
        JsonToObject jsonToObject = new JsonToObject();
        ObjectToJson objectToJson = new ObjectToJson();
        Account account1 = new Account("Ron", "123");
        String jsonString = objectToJson.toJson(account1);
        Account account2 = jsonToObject.fromJson(jsonString, Account.class);
        assertEquals(account1, account2);
    }
}
