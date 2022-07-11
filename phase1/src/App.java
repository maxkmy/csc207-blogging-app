import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.landing.LoginController;
import controllers.landing.QuitController;
import controllers.landing.SignUpController;
import gateway.*;
import useCases.*;

public class App {
    public static void main(String[] args) {
        final String userDataFileDirectory = "data/userData.txt";
        final String postDataFileDirectory = "data/postData.txt";
        final String commentDataFileDirectory = "data/commentData.txt";
        IReader reader1 = new Reader(userDataFileDirectory);
        IReader reader2 = new Reader(postDataFileDirectory);
        IReader reader3 = new Reader(commentDataFileDirectory);
        IWriter writer1 = new Writer(userDataFileDirectory);
        IWriter writer2 = new Writer(postDataFileDirectory);
        IWriter writer3 = new Writer(commentDataFileDirectory);
        ICommentSorter commentSorter = new CommentOldestToNewestSorter();
        IAccountManager accountManager = new AccountManager(reader1, writer1);
        IPostManager postManager = new PostManager(reader2, writer2);
        ICommentManager commentManager = new CommentManager(reader3, writer3, commentSorter);
        RequestFacade landingPageFacade = new RequestFacade(new RequestController[]{
                new LoginController(accountManager, postManager, commentManager),
                new SignUpController(accountManager, postManager, commentManager),
                new QuitController(accountManager, postManager, commentManager)
        });
        landingPageFacade.presentRequest();
    }
}
