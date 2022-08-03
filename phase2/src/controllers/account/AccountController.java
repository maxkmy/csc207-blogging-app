package controllers.account;

import dataMapper.DataMapper;
import entities.Account;
import exception.UserFollowedException;
import exception.UserNotFollowedException;
import exception.UsernameNotFoundException;
import useCases.AccountManager;
import useCases.ManagerData;

import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class AccountController {
    // NEW account controller for WEB
    // replaces the account package
    private AccountManager accountManager;
    private ManagerData managerData;

    public AccountController(ManagerData managerData) {
        accountManager = managerData.getAccountManager();
        this.managerData = managerData;
    }

    public List<String> viewHistory() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<String> dates = new ArrayList<>();
        for (LocalDateTime date : accountManager.getUserHistory(managerData.getCurrentUser())) {
            dates.add(formatter.format(date));
        }
        return dates;
    }

    public void logout() {
        managerData.setCurrentUser(null);
    }

    public void deleteSelf() {
        accountManager.deleteSelf(managerData.getCurrentUser());
        managerData.setCurrentUser(null);
    }

    public boolean isFollowing(String user1, String user2) {
        return accountManager.getFolloweesOf(user1).contains(user2);
    }

    public void follow(String user1, String user2) {
        try {
            accountManager.follow(user1, user2);
        } catch (UsernameNotFoundException | UserFollowedException e) {
            // TODO change this to handle error cleaner
            System.out.println(e.getMessage());
        }
    }

    public void unfollow(String user1, String user2) {
        try {
            accountManager.unfollow(user1, user2);
        } catch (UsernameNotFoundException | UserNotFollowedException e) {
            // TODO change this to handle error cleaner
            System.out.println(e.getMessage());
        }
    }

    public List<Map<String, String>> search(String targetUsername, int limit) {
        DataMapper accountModel = new DataMapper();
        accountModel.addItems(
                accountManager.search(targetUsername, limit),
                new String[] { "username", "isAdmin" }
        );
        return accountModel.getModel();
    }

    public List<Map<String, String>> getFollowers(String user) {
        DataMapper accountModel = new DataMapper();

        accountModel.addItems(
                accountManager.getFollowerListOf(user),
                new String[] { "username", "isAdmin"}
        );
        return accountModel.getModel();
    }

    public List<Map<String, String>> getFollowing(String user) {
        DataMapper accountModel = new DataMapper();

        accountModel.addItems(
                accountManager.getFolloweeListOf(user),
                new String[] { "username", "isAdmin"}
        );
        return accountModel.getModel();
    }

    public boolean isAdmin(String username) {
        return accountManager.isAdmin(username);
    }
}
