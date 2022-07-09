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
    public String username;
    /**
     * the hashed password of the account
     */
    public String hashedPassword;
    /**
     * the login history of the account
     */
    public List<LocalDateTime> history = new ArrayList<>();
    /**
     * a boolean flag checking whether the account is currently banned
     */
    boolean isBanned;
    /**
     * a boolean flag checking whether the account is an admin
     */
    boolean isAdmin;
    /**
     * a set of usernames of the account's followers
     */
    HashSet<String> followers = new HashSet<>();
    /**
     * a set of usernames of the account's followees
     */
    HashSet<String> followees = new HashSet<>();

    public Account(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isBanned = false;
        this.isAdmin = false;
    }

    public void follow(String user) {
        followees.add(user);
    }

    public void unfollow(String user) {
        followees.remove(user);
    }

    public void addFollower(String user) {
        followers.add(user);
    }

    public void removeFollower(String user) {
        followers.remove(user);
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    public HashSet<String> getFollowees() {
        return followees;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void updateHistory() {
        history.add(LocalDateTime.now());
    }

    public List<LocalDateTime> getHistory() {
        return history;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void promoteToAdmin() {
        isAdmin = true;
    }

    public boolean ban() {
        if (isBanned) {
            return false;
        }
        isBanned = true;
        return true;
    }

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
