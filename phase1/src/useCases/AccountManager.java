package useCases;

import exception.*;
import gateway.*;

import entities.Account;

import java.time.LocalDateTime;

import java.util.*;

public class AccountManager implements IAccountManager {
    /**
     * a mapping of username of the account to the account entity
     */
    HashMap<String, Account> accountMap = new HashMap<>();
    /**
     * a gateway responsible for reading objects
     */
    IReader reader;
    /**
     * a gateway responsible for writing objects
     */
    IWriter writer;

    /**
     * Constructor of a use case responsible for managing accounts.
     *
     * @param reader a gateway responsible for reading objects
     * @param writer a gateway responsible for writing objects
     */
    public AccountManager(IReader reader, IWriter writer) {
        this.reader = reader;
        this.writer = writer;
        accountMap = reader.read(accountMap.getClass());
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean containsUser(String username) {
        return accountMap.containsKey(username);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isAdmin(String username) {
        return accountMap.get(username).getIsAdmin();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Account getUser(String username) {
        return accountMap.get(username);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addUser(String username, Account account) {
        accountMap.put(username, account);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteUser(String username) throws UsernameNotFoundException, UserIsAdminException {
        if (!containsUser(username)) {
            throw new UsernameNotFoundException("Unsuccessful deletion, target user does not exist");
        } else if (getUser(username).getIsAdmin()) {
            throw new UserIsAdminException("Unsuccessful deletion, target user is an admin");
        } else {
            deleteSelf(username);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteSelf(String username){
        try {
            for (String followee : getUser(username).getFollowees()) {
                unfollow(username, followee);
            }
            for (String follower : getUser(username).getFollowers()) {
                unfollow(follower, username);
            }
        }
        catch (UsernameNotFoundException | UserNotFollowedException e) {
            System.out.println(e.getMessage());
        }
        accountMap.remove(username);
    }

    /**
     * @inheritDoc
     */
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

    /**
     * @inheritDoc
     */
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

    /**
     * @inheritDoc
     */
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

    /**
     * @inheritDoc
     */
    @Override
    public void signUp(String username, String password) throws UsernameExistsException, InvalidUsernameException {
        Set<String> invalidUsernames = new HashSet<>(List.of(""));
        if (containsUser(username)) {
            throw new UsernameExistsException("The provided username already exists. Please enter another username.");
        } else if (invalidUsernames.contains(username)) {
            throw new InvalidUsernameException("The provided username is invalid. Please enter another username.");
        } else {
            IHash hasher = new PasswordHash();
            String hashedPassword = hasher.hash(password);
            Account account = new Account(username, hashedPassword);
            account.updateHistory();
            addUser(username, account);
        }
    }

    /**
     * @inheritDoc
     */
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

    /**
     * @inheritDoc
     */
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

    /**
     * @inheritDoc
     */
    @Override
    public List<LocalDateTime> getUserHistory(String username) {
        return accountMap.get(username).getHistory();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void follow(String follower, String followee) throws UsernameNotFoundException, UserFollowedException {
        if (!containsUser(follower)) {
            throw new UsernameNotFoundException("Unsuccessful, " + follower + " does not exist.");
        } else if (!containsUser(followee)) {
            throw new UsernameNotFoundException("Unsuccessful, " + followee + " does not exist.");
        } else if (getFolloweesOf(follower).contains(followee)) {
            throw new UserFollowedException("Unsuccessful, " + followee + " is already followed");
        }
        Account followerAccount = accountMap.get(follower);
        Account followeeAccount = accountMap.get(followee);
        followerAccount.follow(followee);
        followeeAccount.addFollower(follower);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void unfollow(String follower, String followee) throws UsernameNotFoundException, UserNotFollowedException {
        if (!containsUser(follower)) {
            throw new UsernameNotFoundException("Unsuccessful, " + follower + " does not exist.");
        } else if (!containsUser(followee)) {
            throw new UsernameNotFoundException("Unsuccessful, " + followee + " does not exist.");
        } else if (!getFollowersOf(followee).contains(follower)) {
            throw new UserNotFollowedException("Unsuccessful, " + followee + " is already not followed");
        }
        Account followerAccount = accountMap.get(follower);
        Account followeeAccount = accountMap.get(followee);
        followerAccount.unfollow(followee);
        followeeAccount.removeFollower(follower);
    }

    /**
     * @inheritDoc
     */
    @Override
    public HashSet<String> getFollowersOf(String username) {
        return getUser(username).getFollowers();
    }

    /**
     * @inheritDoc
     */
    @Override
    public HashSet<String> getFolloweesOf(String username) {
        return getUser(username).getFollowees();
    }

    /**
     * @inheritDoc
     */
    @Override
    public HashMap<String, Account> getMap(){
        return accountMap;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void save() {
        writer.write(accountMap);
    }
}
