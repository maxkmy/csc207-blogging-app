package useCases;

public class ManagerData {
    private final AccountManager accountManager;
    private final PostManager postManager;
    private final CommentManager commentManager;
    private String currentUser;

    /**
     * Constructor for ManagerData
     *
     * @param accountManager a use case for accounts
     * @param postManager a use case for posts
     * @param commentManager a use case for comments
     */
    public ManagerData(AccountManager accountManager,
                       PostManager postManager,
                       CommentManager commentManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
        this.commentManager = commentManager;
    }

    /**
     * Gets the account manager stored in manager data
     *
     * @return the account manager stored in manager data
     */
    public AccountManager getAccountManager() {
        return accountManager;
    }

    /**
     * Gets the post manager stored in manager data
     *
     * @return the post manager stored in manager data
     */
    public PostManager getPostManager() {
        return postManager;
    }

    /**
     * Gets the comment manager stored in manager data
     *
     * @return the comment manager stored in manager data
     */
    public CommentManager getCommentManager() {
        return commentManager;
    }

    /**
     * Gets the username of the current user who's logged in
     *
     * @return the username of the current user who's logged in
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the username of the current user who's logged in
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets the role of the current user who's logged in
     *
     * @return whether the current user is an admin
     */
    public boolean getCurrentUserRole() {
        return currentUser != null && accountManager.isAdmin(currentUser);
    }

    /**
     * Saves data in all 3 use cases.
     */
    public void save() {
        accountManager.save();
        postManager.save();
        commentManager.save();
    }
}
