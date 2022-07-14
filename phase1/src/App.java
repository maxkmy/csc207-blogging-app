import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.landing.LoginController;
import controllers.landing.QuitController;
import controllers.landing.SignUpController;
import gateway.IWriter;
import gateway.Writer;
import useCases.*;
import gateway.IReader;
import gateway.Reader;

public class App {
    public static void main(String[] args) {
        final String userDataFileDirectory = "data/userData.txt";
        final String postDataFileDirectory = "data/postData.txt";
        final String commentDataFileDirectory = "data/commentData.txt";
        final String likeDataFileDirectory = "data/likeData.txt";
        IReader reader1 = new Reader(userDataFileDirectory);
        IReader reader2 = new Reader(postDataFileDirectory);
        IReader reader3 = new Reader(commentDataFileDirectory);
        IReader reader4 = new Reader(likeDataFileDirectory);
        IWriter writer1 = new Writer(userDataFileDirectory);
        IWriter writer2 = new Writer(postDataFileDirectory);
        IWriter writer3 = new Writer(commentDataFileDirectory);
        IWriter writer4 = new Writer(likeDataFileDirectory);
        IAccountManager accountManager = new AccountManager(reader1, writer1);
        IPostManager postManager = new PostManager(reader2, writer2);
        ILikeManager likeManager = new LikeManager(reader4, writer4);
        ICommentManager commentManager = new CommentManager(reader3, writer3);
        RequestFacade landingPageFacade = new RequestFacade(new RequestController[]{
                new LoginController(accountManager, postManager, commentManager, likeManager),
                new SignUpController(accountManager, postManager, commentManager, likeManager),
                new QuitController(accountManager, postManager, commentManager, likeManager)
        });
        landingPageFacade.presentRequest();
    }
}
