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

        Server server = new Server(new ManagerData(
                new AccountManager(reader1, writer1, new AccountSorter()),
                new PostManager(reader2, writer2, new PostTimeSorter()),
                new CommentManager(reader3, writer3, new CommentTimeSorter()))
        );
        server.start();
    }
}