package useCases;

import entities.Account;
import exception.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IAccountManager {
    /**
     * Make the follower follow the followee.
     *
     * @param follower the username of the follower
     * @param followee the username of the followee
     * @throws UsernameNotFoundException if the username of the follower or followee does not exist
     * @throws UserFollowedException     if the follower already follows the followee
     */
    void follow(String follower, String followee) throws UsernameNotFoundException, UserFollowedException;

    /**
     * Make the follower unfollow the followee.
     *
     * @param follower                   the username of the follower
     * @param followee                   the username of the followee
     * @throws UsernameNotFoundException if the username of the follower or followee does not exist
     * @throws UserNotFollowedException  if the follower does not follow the followee
     */
    void unfollow(String follower, String followee) throws UsernameNotFoundException, UserNotFollowedException;

    /**
     * Returns a set of followers of an account
     *
     * @param username the username of the account whose follower list will be returned
     * @return         a set of followers of the account with the provided username
     */
    HashSet<String> getFollowersOf(String username);

    /**
     * Returns a set of followees of an account
     *
     * @param username the username of the account whose followee list will be returned
     * @return         a set of followees of the account with the provided username
     */
    HashSet<String> getFolloweesOf(String username);

    /**
     * Checks if a username exists.
     *
     * @param username a string representing a username of a user.
     * @return whether a username is already taken by another user.
     */
    boolean containsUser(String username);

    /**
     * Checks if a user is an admin.
     *
     * @param username a string representing a username of a user.
     * @return whether an account with a given username is an admin.
     */
    boolean isAdmin(String username);

    /**
     * Return the account entity with a given username.
     *
     * @param username a string representing a username of a user.
     * @return the account of the user with a given username.
     */
    Account getUser(String username);

    /**
     * Add a user into the mapping of all accounts' username to account entity.
     *
     * @param username a string representing a username of a user.
     * @param account  an account associated with username.
     */
    void addUser(String username, Account account);

    /**
     * Deletes a user with the provided username.
     *
     * @param username                   a string representing a username of a user.
     * @throws UsernameNotFoundException if the provided username is not taken by any user.
     * @throws UserIsAdminException      if the user associated with the provided username is an admin.
     */
    void deleteUser(String username) throws UsernameNotFoundException, UserIsAdminException;

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
    void login(String username, String password) throws
            IncorrectPasswordException,
            UsernameNotFoundException,
            AccountBannedException;

    /**
     * Ban an account based on a username
     *
     * @param username                    a string representing a username of a user.
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws UserIsAdminException       if the account with the provided username is an admin.
     */
    boolean ban(String username) throws UsernameNotFoundException, UserIsAdminException;

    /**
     * Unban an account based on a username
     *
     * @param username                    a string representing a username of a user.
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws UserIsAdminException       if the account with the provided username is an admin.
     */
    boolean unban(String username) throws UsernameNotFoundException, UserIsAdminException;

    /**
     * Creates a new account based on sign up credentials.
     *
     * @param username                  a string representing a username of a user.
     * @param password                  a string representing the password of a user.
     * @throws UsernameExistsException  if the username of is taken by some existing account.
     */
    void signUp(String username, String password) throws UsernameExistsException, InvalidUsernameException;

    /**
     * Promote a user to become an admin.
     *
     * @param username                    a string representing a username of a user.
     * @throws UsernameNotFoundException  if the provided username does not exist.
     * @throws UserIsAdminException       if the account with the provided username is an admin.
     */
    void promoteToAdmin(String username) throws UsernameNotFoundException, UserIsAdminException;

    /**
     * Return the login and sign up history of the user.
     *
     * @param username a string representing a username of a user.
     * @return a list of dates where the user logged in or signed up.
     */
    List<LocalDateTime> getUserHistory(String username);

    /**
     * Creates a new admin based on provided credentials
     *
     * @param username                  a string representing a username of the new admin.
     * @param password                  a string representing the password of the new admin.
     * @throws UsernameExistsException  if the username of is taken by some existing account.
     */
    void createAdmin(String username, String password) throws UsernameExistsException;

    /**
     * Deletes the account with the provided username.
     */
    void deleteSelf(String username);

    /**
     * Saves the current data.
     */
    void save();

    /**
     * Returns the mapping of username of all accounts to all account entities
     */
    HashMap<String, Account> getMap();
}
