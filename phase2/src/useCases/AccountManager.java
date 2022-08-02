package useCases;

import exception.*;
import gateway.*;

import entities.Account;

import java.time.LocalDateTime;

import java.util.*;

public class AccountManager {
    /**
     * a mapping of username of the account to the account entity
     */
    private HashMap<String, Account> accountMap = new HashMap<>();
    /**
     * a gateway responsible for reading objects
     */
    private IReader reader;
    /**
     * a gateway responsible for writing objects
     */
    private IWriter writer;
    /**
     * a sorter that sorts accounts
     */
    private IAccountSorter accountSorter;

    /**
     * Constructor of a use case responsible for managing accounts.
     *
     * @param reader a gateway responsible for reading objects
     * @param writer a gateway responsible for writing objects
     */
    public AccountManager(IReader reader, IWriter writer, IAccountSorter accountSorter) {
        this.reader = reader;
        this.writer = writer;
        accountMap = reader.read(accountMap.getClass());
        this.accountSorter = accountSorter;
    }

    /**
     * Checks if a username exists.
     *
     * @param username a string representing a username of a user.
     * @return whether a username is already taken by another user.
     */
    public boolean containsUser(String username) {
        return accountMap.containsKey(username);
    }

    /**
     * Checks if a user is an admin.
     *
     * @param username a string representing a username of a user.
     * @return whether an account with a given username is an admin.
     */
    public boolean isAdmin(String username) {
        return accountMap.get(username).getIsAdmin();
    }

    /**
     * Return the account entity with a given username.
     *
     * @param username a string representing a username of a user.
     * @return the account of the user with a given username.
     */
    public Account getUser(String username) {
        return accountMap.get(username);
    }

    /**
     * Add a user into the mapping of all accounts' username to account entity.
     *
     * @param username a string representing a username of a user.
     * @param account  an account associated with username.
     */
    public void addUser(String username, Account account) {
        accountMap.put(username, account);
    }

    /**
     * Deletes a user with the provided username.
     *
     * @param username                   a string representing a username of a user.
     * @throws UsernameNotFoundException if the provided username is not taken by any user.
     * @throws UserIsAdminException      if the user associated with the provided username is an admin.
     */
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
     * Deletes the account with the provided username.
     */
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
     * Checks whether the login credentials provided are valid.
     *
     * @param username                    a string representing a username of a user.
     * @param password                    a string representing the password of a user.
     * @throws IncorrectPasswordException if the provided password does not match the password of the account
     *                                    with the given username
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws AccountBannedException     if the account is currently being banned
     */
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
     * Ban an account based on a username
     *
     * @param username                    a string representing a username of a user.
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws UserIsAdminException       if the account with the provided username is an admin.
     */
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
     * Unban an account based on a username
     *
     * @param username                    a string representing a username of a user.
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws UserIsAdminException       if the account with the provided username is an admin.
     */
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
     * Creates a new account based on sign up credentials.
     *
     * @param username                  a string representing a username of a user.
     * @param password                  a string representing the password of a user.
     * @throws UsernameExistsException  if the username of is taken by some existing account.
     */
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
     * Creates a new admin based on provided credentials
     *
     * @param username                  a string representing a username of the new admin.
     * @param password                  a string representing the password of the new admin.
     * @throws UsernameExistsException  if the username of is taken by some existing account.
     */
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
     * Promote a user to become an admin.
     *
     * @param username                    a string representing a username of a user.
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws UserIsAdminException       if the account with the provided username is an admin.
     */
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
     * Return the login and sign up history of the user.
     *
     * @param username a string representing a username of a user.
     * @return a list of dates where the user logged in or signed up.
     */
    public List<LocalDateTime> getUserHistory(String username) {
        return accountMap.get(username).getHistory();
    }

    /**
     * Make the follower follow the followee.
     *
     * @param follower the username of the follower
     * @param followee the username of the followee
     * @throws UsernameNotFoundException if the username of the follower or followee does not exist
     * @throws UserFollowedException     if the follower already follows the followee
     */
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
     * Make the follower unfollow the followee.
     *
     * @param follower                   the username of the follower
     * @param followee                   the username of the followee
     * @throws UsernameNotFoundException if the username of the follower or followee does not exist
     * @throws UserNotFollowedException  if the follower does not follow the followee
     */
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
     * Returns a set of followers of an account
     *
     * @param username the username of the account whose follower list will be returned
     * @return         a set of followers of the account with the provided username
     */
    public HashSet<String> getFollowersOf(String username) {
        return getUser(username).getFollowers();
    }

    /**
     * Returns a set of followees of an account
     *
     * @param username the username of the account whose followee list will be returned
     * @return         a set of followees of the account with the provided username
     */
    public HashSet<String> getFolloweesOf(String username) {
        return getUser(username).getFollowees();
    }

    /**
     * Saves the current data.
     */
    public void save() {
        writer.write(accountMap);
    }
}
