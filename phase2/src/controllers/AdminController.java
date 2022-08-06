package controllers;

import exception.UserFollowedException;
import exception.UserIsAdminException;
import exception.UsernameNotFoundException;
import useCases.AccountManager;
import useCases.CommentManager;
import useCases.ManagerData;
import useCases.PostManager;

public class AdminController {
    /**
     * a use case responsible for managing accounts
     */
    private AccountManager accountManager;
    /**
     * a use case responsible for managing posts
     */
    private PostManager postManager;
    /**
     * a use case responsible for managing comments
     */
    private CommentManager commentManager;

    /**
     * Constructor of a controller for admins
     *
     * @param managerData an object that groups use cases together
     */
    public AdminController(ManagerData managerData) {
        accountManager = managerData.getAccountManager();
        postManager = managerData.getPostManager();
        commentManager = managerData.getCommentManager();
    }


    public void promote(String user) {
        try {
            accountManager.promoteToAdmin(user);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ban(String user) {
        try {
            accountManager.ban(user);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    public void unban(String user) {
        try {
            accountManager.unban(user);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(String user) {
        try {
            accountManager.deleteUser(user);
            postManager.deletePostsWrittenBy(user);
            commentManager.deleteCommentsWrittenBy(user);
        } catch (UsernameNotFoundException | UserIsAdminException e) {
            System.out.println(e.getMessage());
        }
    }
}
