package controllers;

import dataMapper.DataMapper;
import exception.UserFollowedException;
import exception.UserNotFollowedException;
import exception.UsernameNotFoundException;
import useCases.AccountManager;
import useCases.ManagerData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountController {
    /**
     * a use case responsible for managing accounts
     */
    private AccountManager accountManager;
    /**
     * an object that groups use cases in 1 class
     */
    private ManagerData managerData;

    /**
     * Constructor of a controller for accounts
     *
     * @param managerData an object that groups use cases together
     */
    public AccountController(ManagerData managerData) {
        accountManager = managerData.getAccountManager();
        this.managerData = managerData;
    }

    /**
     * Returns a list of dates for login history
     *
     * @return a list of dates
     */
    public List<String> viewHistory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<String> dates = new ArrayList<>();
        for (LocalDateTime date : accountManager.getUserHistory(managerData.getCurrentUser())) {
            dates.add(formatter.format(date));
        }
        return dates;
    }

    /**
     * Logs the current user out
     */
    public void logout() {
        managerData.save();
        managerData.setCurrentUser(null);
    }

    /**
     * Deletes the account of the current user
     */
    public void deleteSelf() {
        accountManager.deleteSelf(managerData.getCurrentUser());
        managerData.setCurrentUser(null);
    }

    /**
     * Returns whether a user follows another user
     *
     * @param user1 a username
     * @param user2 a username
     * @return whether user1 follows user2
     */
    public boolean isFollowing(String user1, String user2) {
        return accountManager.getFolloweesOf(user1).contains(user2);
    }

    /**
     * Makes user1 follow user2
     *
     * @param user1 a username
     * @param user2 a username
     */
    public void follow(String user1, String user2) {
        try {
            accountManager.follow(user1, user2);
        } catch (UsernameNotFoundException | UserFollowedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Makes user1 unfollow user2
     *
     * @param user1 a username
     * @param user2 a username
     */
    public void unfollow(String user1, String user2) {
        try {
            accountManager.unfollow(user1, user2);
        } catch (UsernameNotFoundException | UserNotFollowedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns a list of users whose username most closely matches a search query
     *
     * @param targetUsername a username search query
     * @param limit the maximum number of accounts to be displayed
     * @return a list of users
     */
    public List<Map<String, String>> search(String targetUsername, int limit) {
        DataMapper accountModel = new DataMapper();
        accountModel.addItems(
                accountManager.search(targetUsername, limit),
                new String[] { "username", "isAdmin" }
        );
        return putFolloweeStatus(accountModel.getModel());
    }

    /**
     * Returns a list of followers of a user
     *
     * @param user a username
     * @return a list of users that follow user
     */
    public List<Map<String, String>> getFollowers(String user) {
        DataMapper accountModel = new DataMapper();
        accountModel.addItems(
                accountManager.getFollowerListOf(user),
                new String[] { "username", "isAdmin"}
        );
        return putFolloweeStatus(accountModel.getModel());
    }

    /**
     * Returns a list of followees of a user
     *
     * @param user a username
     * @return a list of users followed by user
     */
    public List<Map<String, String>> getFollowing(String user) {
        DataMapper accountModel = new DataMapper();
        accountModel.addItems(
                accountManager.getFolloweeListOf(user),
                new String[] { "username", "isAdmin"}
        );
        return putFolloweeStatus(accountModel.getModel());
    }

    /**
     * Returns a list of users
     *
     * @param accounts a list of accounts without followee status
     * @return a list of accounts with followee status added
     */
    private List<Map<String, String>> putFolloweeStatus(List<Map<String, String>> accounts) {
        for (Map<String, String> account: accounts) {
            if (isFollowing(managerData.getCurrentUser(), account.get("username"))) {
                account.put("followeeStatus", "followee");
            } else if (account.get("username").equals(managerData.getCurrentUser())) {
                account.put("followeeStatus", "self");
            } else {
                account.put("followeeStatus", "not followee");
            }
        }
        return accounts;
    }

    /**
     * Returns whether an account is an admin
     *
     * @param username a username
     * @return whether username is an admin
     */
    public boolean isAdmin(String username) {
        return accountManager.isAdmin(username);
    }

    /**
     * Returns whether an account is an banned
     *
     * @param username a username
     * @return whether username is an banned
     */
    public boolean isBanned(String username) {
        return accountManager.isBanned(username);
    }
}
