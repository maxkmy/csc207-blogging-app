import org.junit.*;

import static org.junit.Assert.*;
import entities.Account;

public class AccountTest{

    @Test
    public void testGetUsername(){
        Account account = new Account("Ron", "1234");
        assertEquals("Ron", account.getUsername());
    }

    @Test
    public void testSetUsername(){
        Account account = new Account("Ron", "1234");
        account.setUsername("Brian");
        assertEquals("Brian", account.getUsername());
    }

    @Test
    public void testGetIsBanned1() {
        Account account = new Account("Ron", "1234");
        account.unban();
        assertFalse(account.getIsBanned());
    }

    @Test
    public void testGetIsBanned2() {
        Account account = new Account("Ron", "1234");
        account.ban();
        assertTrue(account.getIsBanned());
    }

    @Test
    public void testGetIsAdmin1() {
        Account account = new Account("Ron", "1234");
        assertFalse(account.getIsAdmin());
    }

    @Test
    public void testGetIsAdmin2() {
        Account account = new Account("Ron", "1234");
        account.promoteToAdmin();
        assertTrue(account.getIsAdmin());
    }

    @Test
    public void testUnban1() {
        Account account = new Account("Ron", "1234");
        assertFalse("A new account is unbanned. Unbanning an unbanned account should return false.", account.unban());
    }

    @Test
    public void testUnban2() {
        Account account = new Account("Ron", "1234");
        account.ban();
        assertTrue("The account is banned. Unbanning a banned account should return true.", account.unban());
    }

    @Test
    public void testBan1() {
        Account account = new Account("Ron", "1234");
        assertTrue("A new account is unbanned. Banning a new account should return true.", account.ban());
    }

    @Test
    public void testBan2() {
        Account account = new Account("Ron", "1234");
        account.ban();
        assertFalse("The account is already banned. Banning an account that's already banned should return False", account.ban());
    }
}