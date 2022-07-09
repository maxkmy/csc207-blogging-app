package useCases;

import exception.*;
import gateway.*;

import entities.Account;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AccountManager implements IAccountManager,ISearchAlgorithm {
    HashMap<String, Account> accountMap;

    // called in all controllers
    public AccountManager(HashMap<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    @Override
    public HashMap<String, Account> getMap(){
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
            deleteSelf(username);
        }
    }

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

    @Override
    public HashSet<String> getFollowersOf(String username) {
        return getUser(username).getFollowers();
    }

    @Override
    public HashSet<String> getFolloweesOf(String username) {
        return getUser(username).getFollowees();
    }

    @Override
    public HashMap<String, Double> doSearch(String stri, String Query) {
        HashMap<String, Account> database = getMap();
        HashMap<String, Double> map = new HashMap<>();
        for (String key: database.keySet()) {
            SimilarityScore curr = new SimilarityScore();
            map.put(key,curr.getSimilarityScore(stri,key));
        }
        return map;
    }
}
