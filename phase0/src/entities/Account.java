package entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Account {
    public String username;
    public String hashedPassword;
    public List<LocalDateTime> history = new ArrayList<>();
    boolean isBanned;
    boolean isAdmin;

    public Account(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.isBanned = false;
        this.isAdmin = false;
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
