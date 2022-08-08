import exception.*;
import gateway.*;
import org.junit.Test;
import useCases.AccountManager;

import static org.junit.Assert.*;

public class AccountManagerTest {
    @Test
    public void testContainsUser1() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        assertTrue(accountManager.containsUser("admin"));
    }

    @Test
    public void testContainsUser2() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        assertFalse(accountManager.containsUser("Max"));
    }

    @Test
    public void testIsAdmin1() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        assertTrue(accountManager.isAdmin("admin"));
    }

    @Test
    public void testIsAdmin2() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user", "password");
        } catch (UsernameExistsException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
        assertFalse(accountManager.isAdmin("user"));
    }

    @Test
    public void testDeleteSelf() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user", "password");
        } catch (UsernameExistsException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
        accountManager.deleteSelf("admin");
        assertFalse(accountManager.containsUser("admin"));
    }

    @Test
    public void testBan() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user", "password");
        } catch (UsernameExistsException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
        try {
            accountManager.ban("user");
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
        assertTrue(accountManager.isBanned("user"));
    }

    @Test
    public void testUnban() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user", "password");
        } catch (UsernameExistsException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
        try {
            accountManager.ban("user");
            accountManager.unban("user");
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
        assertFalse(accountManager.isBanned("user"));
    }

    @Test
    public void testPromoteToAdmin() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user", "password");
            accountManager.promoteToAdmin("user");
            assertTrue(accountManager.isAdmin("user"));
        } catch (UsernameNotFoundException | UserIsAdminException |
                 UsernameExistsException | InvalidUsernameException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFollow() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user1", "password");
            accountManager.signUp("user2", "password");
            accountManager.follow("user1", "user2");
            assertTrue(accountManager.getFollowersOf("user2").contains("user1"));
        } catch (UsernameNotFoundException | UsernameExistsException |
                 InvalidUsernameException | UserFollowedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetFollowees() {
        IReader reader = new Reader("test/testData/testAccountData.txt");
        IWriter writer = new Writer("test/testData/testAccountData.txt");
        IAccountSorter sorter = new AccountSorter();
        AccountManager accountManager = new AccountManager(reader, writer, sorter);
        try {
            accountManager.signUp("user1", "password");
            accountManager.signUp("user2", "password");
            accountManager.follow("user1", "user2");
            assertTrue(accountManager.getFolloweesOf("user1").contains("user2"));
        } catch (UsernameNotFoundException | UsernameExistsException |
                 InvalidUsernameException | UserFollowedException e) {
            System.out.println(e.getMessage());
        }
    }
}
