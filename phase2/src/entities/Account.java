package entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

import java.io.Serializable;

public class Account implements Serializable {
    /**
     * the username of the account
     */
    private String username;
    /**
     * the hashed password of the account
     */
    private String hashedPassword;
    /**
     * the login history of the account
     */
    private List<LocalDateTime> history = new ArrayList<>();
    /**
     * a boolean flag checking whether the account is currently banned
     */
    private boolean isBanned;
    /**
     * a boolean flag checking whether the account is an admin
     */
    private boolean isAdmin;
    /**
     * a set of usernames of the account's followers
     */
    private HashSet<String> followers = new HashSet<>();
    /**
     * a set of usernames of the account's followees
     */
    private HashSet<String> followees = new HashSet<>();

    public Account(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isBanned = false;
        this.isAdmin = false;
    }

    /**
     * adds a username to set of followed accounts to signify
     * this instance's user is following the user attached to that username
     *
     * @param user the unique username referencing an Account object
     */
    public void follow(String user) {
        followees.add(user);
    }

    /**
     * removes a username from the set of followed accounts to
     * signify this instance's user is no longer following the
     * user attached to that username
     *
     * @param user the unique username referencing an Account object
     */
    public void unfollow(String user) {
        followees.remove(user);
    }

    /**
     * adds a username to set of followed accounts to signify
     * this instance's user is being followed by the user
     * attached to that username
     *
     * @param user the unique username referencing an Account object
     */
    public void addFollower(String user) {
        followers.add(user);
    }

    /**
     * removes a username from the set of followed accounts to
     * signify this instance's user is no longer being followed
     * by the user attached to that username
     *
     * @param user the unique username referencing an Account object
     */
    public void removeFollower(String user) {
        followers.remove(user);
    }

    /**
     * returns a HashSet of the usernames that identify users which
     * follow this instance's user
     *
     * @return a HashSet of Strings of usernames
     */
    public HashSet<String> getFollowers() {
        return followers;
    }

    /**
     * returns a HashSet of the usernames that identify users which
     * are followed by this instance's user
     *
     * @return a HashSet of Strings of usernames
     */
    public HashSet<String> getFollowees() {
        return followees;
    }

    /**
     * returns a String representing this instances unique
     * custom identifier or username
     *
     * @return a String of a username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets a new unique username to identify this instance
     *
     * @param username the new unique identifier
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns this account's hashed password
     *
     * @return a String of a hashed password
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * updates the user's login history to include the current time
     */
    public void updateHistory() {
        history.add(LocalDateTime.now());
    }

    /**
     * returns the user's login history as a list with
     * LocalDateTime objects which signify the times logged in
     *
     * @return a List of LocalDateTime objects
     */
    public List<LocalDateTime> getHistory() {
        return history;
    }

    /**
     * returns true if the user is banned and false if the user is not
     *
     * @return boolean repsenting whether the current user is banned
     */
    public boolean getIsBanned() {
        return isBanned;
    }

    /**
     * returns true if the user is an admin and false if the user is not
     *
     * @return boolean representing whether the user is an admin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * promotes this instance of user to admin
     */
    public void promoteToAdmin() {
        isAdmin = true;
    }

    /**
     * bans the current user
     * if the user is already banned, returns false
     * otherwise true
     *
     * @return a boolean representing whether the user had not been banned prior to call
     */
    public boolean ban() {
        if (isBanned) {
            return false;
        }
        isBanned = true;
        return true;
    }

    /**
     * unbans the current user
     * if the user is already unbanned, returns false
     * otherwise true
     *
     * @return a boolean representing whether the user been banned prior to call
     */
    public boolean unban() {
        if (!isBanned) {
            return false;
        }
        isBanned = false;
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (!(obj instanceof Account)) {
            return false;
        }
        Account account = (Account) obj;
        return username.equals(account.getUsername()) &&
                hashedPassword.equals(account.getHashedPassword()) &&
                history.equals(account.getHistory()) &&
                isBanned == account.getIsBanned() &&
                isAdmin == account.getIsAdmin();
    }
}
