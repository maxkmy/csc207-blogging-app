import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.landing.LoginController;
import controllers.landing.QuitController;
import controllers.landing.SignUpController;
import useCases.AccountManager;
import useCases.IAccountManager;
import useCases.PostManager;
import useCases.IPostManager;
import gateway.IReader;
import gateway.Reader;

import entities.Account;
import entities.Post;

import java.util.HashMap;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        final String userDataFileDirectory = "data/userData.txt";
        final String postDataFileDirectory = "data/postData.txt";
        IReader reader1 = new Reader(userDataFileDirectory);
        HashMap<String, Account> accountMap = (HashMap<String, Account>) reader1.read();
        IReader reader2 = new Reader(postDataFileDirectory);
        HashMap<UUID, Post> posts = (HashMap<UUID, Post>) reader2.read();
        IAccountManager accountManager = new AccountManager(accountMap);
        IPostManager postManager = new PostManager(posts);
        RequestFacade landingPageFacade = new RequestFacade(new RequestController[]{
                new LoginController(accountManager, postManager),
                new SignUpController(accountManager, postManager),
                new QuitController(accountManager, postManager)
        });
        landingPageFacade.presentRequest();
    }
}
