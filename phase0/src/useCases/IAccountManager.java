package useCases;

import entities.Account;
import exception.*;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.List;

public interface IAccountManager {
    boolean containsUser(String username);
    boolean isAdmin(String username);
    Account getUser(String username);
    void addUser(String username, Account account);
    void deleteUser(String username) throws UsernameNotFoundException, UserIsAdminException;
    void login(String username, String password) throws
            IncorrectPasswordException,
            UsernameNotFoundException,
            AccountBannedException;
    boolean ban(String username) throws UsernameNotFoundException, UserIsAdminException;
    boolean unban(String username) throws UsernameNotFoundException, UserIsAdminException;
    JSONObject toJson();
    void signUp(String username, String password) throws UsernameExistsException;
    void promoteToAdmin(String username) throws UsernameNotFoundException, UserIsAdminException;
    List<LocalDateTime> getUserHistory(String username);
    void createAdmin(String username, String password) throws UsernameExistsException;
    void deleteSelf(String username);
}
