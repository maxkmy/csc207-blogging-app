import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.landing.LoginController;
import controllers.landing.QuitController;
import controllers.landing.SignUpController;
import gateway.IWriter;
import gateway.Writer;
import useCases.AccountManager;
import useCases.IAccountManager;
import useCases.PostManager;
import useCases.IPostManager;
import gateway.IReader;
import gateway.Reader;

public class App {
    public static void main(String[] args) {
        final String userDataFileDirectory = "data/userData.txt";
        final String postDataFileDirectory = "data/postData.txt";
        IReader reader1 = new Reader(userDataFileDirectory);
        IReader reader2 = new Reader(postDataFileDirectory);
        IWriter writer1 = new Writer(userDataFileDirectory);
        IWriter writer2 = new Writer(postDataFileDirectory);
        IAccountManager accountManager = new AccountManager(reader1, writer1);
        IPostManager postManager = new PostManager(reader2, writer2);
        RequestFacade landingPageFacade = new RequestFacade(new RequestController[]{
                new LoginController(accountManager, postManager),
                new SignUpController(accountManager, postManager),
                new QuitController(accountManager, postManager)
        });
        landingPageFacade.presentRequest();
    }
}
