package useCases;

import exception.*;
import gateway.*;
import org.json.simple.JSONObject;

import entities.Account;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;

public class AccountManager implements IAccountManager {
    HashMap<String, Account> accountMap;

    // called in all controllers
    public AccountManager(HashMap<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    @Override
    public HashMap<String, Account> getAccountMap() {
        return accountMap;
    }

    // call in SignUpController
    @Override
    public boolean containsUser(String username) {
        return accountMap.containsKey(username);
    }

    // call in LoginController
    @Override
    public boolean isAdmin(String username) {
        return accountMap.get(username).getIsAdmin();
    }

    // call in DeleteUserController, PromoteUserController
    @Override
    public Account getUser(String username) {
        return accountMap.get(username);
    }

    // call in SignUpController
    @Override
    public void addUser(String username, Account account) {
        accountMap.put(username, account);
    }

    // call in DeleteUserController
    @Override
    public void deleteUser(String username) throws UsernameNotFoundException, UserIsAdminException {
        if (!containsUser(username)) {
            throw new UsernameNotFoundException("Unsuccessful deletion, target user does not exist");
        } else if (getUser(username).getIsAdmin()) {
            throw new UserIsAdminException("Unsuccessful deletion, target user is an admin");
        } else {
            accountMap.remove(username);
        }
    }

    @Override
    public void deleteSelf(String username) {
        accountMap.remove(username);
    }

    // call in LoginController
    @Override
    public void login(String username, String password) throws
            IncorrectPasswordException,
            UsernameNotFoundException,
            AccountBannedException {
        if (accountMap.containsKey(username)) {
            IHash hasher = new PasswordHash();
            String hashedPassword = hasher.hash(password);
            Account account = accountMap.get(username);
            if (account.getIsBanned()) {
                throw new AccountBannedException("Your account is currently banned and cannot be accessed. \n");
            }
            if (hashedPassword.equals(account.getHashedPassword())) {
                account.updateHistory();
            } else {
                throw new IncorrectPasswordException("The provided password is incorrect.");
            }
        } else {
            throw new UsernameNotFoundException("The provided username does not exist.");
        }
    }

    @Override
    public boolean ban(String username) throws UsernameNotFoundException, UserIsAdminException {
        if (!containsUser(username)) {
            throw new UsernameNotFoundException("Unsuccessful ban, target user does not exist.");
        } else if (getUser(username).getIsAdmin()) {
            throw new UserIsAdminException("Unsuccessful ban, target user is an admin.");
        } else {
            return getUser(username).ban();
        }
    }

    @Override
    public boolean unban(String username) throws UsernameNotFoundException, UserIsAdminException {
        if (!containsUser(username)) {
            throw new UsernameNotFoundException("Unsuccessful unban, target account does not exist.");
        } else if (getUser(username).getIsAdmin()) {
            throw new UserIsAdminException("Unsuccessful unban, target account is an admin.");
        } else {
            return getUser(username).unban();
        }
    }

    @Override
    public void signUp(String username, String password) throws UsernameExistsException {
        if (containsUser(username)) {
            throw new UsernameExistsException("The provided username already exists. Please enter another username.");
        } else {
            IHash hasher = new PasswordHash();
            String hashedPassword = hasher.hash(password);
            Account account = new Account(username, hashedPassword);
            account.updateHistory();
            addUser(username, account);
        }
    }

    @Override
    public void createAdmin(String username, String password) throws UsernameExistsException {
        if (containsUser(username)) {
            throw new UsernameExistsException("The provided username already exists. Please enter another username.");
        } else {
            IHash hasher = new PasswordHash();
            String hashedPassword = hasher.hash(password);
            Account account = new Account(username, hashedPassword);
            account.promoteToAdmin();
            addUser(username, account);
        }
    }

    @Override
    public void promoteToAdmin(String username) throws UsernameNotFoundException, UserIsAdminException {
        if (!(containsUser(username))) {
            throw new UsernameNotFoundException("Unsuccessful, the target user does not exist.");
        } else if(getUser(username).getIsAdmin()) {
            throw new UserIsAdminException("Unsuccessful, the target user is already an admin.");
        } else {
            getUser(username).promoteToAdmin();
        }
    }

    @Override
    public List<LocalDateTime> getUserHistory(String username) {
        return accountMap.get(username).getHistory();
    }
}
