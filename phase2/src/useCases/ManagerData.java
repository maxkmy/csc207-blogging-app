package useCases;

public class ManagerData {

    private final AccountManager accountManager;
    private final PostManager postManager;
    private final CommentManager commentManager;
    private String currentUser;

    public ManagerData(AccountManager accountManager,
                       PostManager postManager,
                       CommentManager commentManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
        this.commentManager = commentManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public PostManager getPostManager() {
        return postManager;
    }

    public CommentManager getCommentManager() {
        return commentManager;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public boolean getCurrentUserRole() {
        return currentUser != null && accountManager.isAdmin(currentUser);
    }

    public void save() {
        accountManager.save();
        postManager.save();
        commentManager.save();
    }
}
