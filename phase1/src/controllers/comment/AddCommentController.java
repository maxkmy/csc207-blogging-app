package controllers.comment;

import controllers.appWide.RequestController;
import dataMapper.DataMapper;
import useCases.ICommentManager;

import java.util.Scanner;
import java.util.UUID;

public class AddCommentController extends RequestController {

    /**
     * a data mapper responsible for mapping comments into a data structure usable by the presenters
     */
    DataMapper commentModel;
    /**
     * a use case responsible for managing comments
     */
    ICommentManager commentManager;
    /**
     * a string representing the author of the comment
     */
    String author;

    /**
     * Constructor for a controller responsible for reading input to create a new comment
     *
     * @param commentManager    use case responsible for managing comments
     */
    public AddCommentController(DataMapper commentModel, ICommentManager commentManager, String author) {
        this.commentModel = commentModel;
        this.commentManager = commentManager;
        this.author = author;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "Create a comment"; }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Comment: ");
        String content = scanner.nextLine();
        sleeper.sleep(200);
        UUID commentId = commentManager.addComment(UUID.fromString(requester), content, author);
        String[] attributes = new String[]{"postId", "content", "author", "id"};
        commentModel.addItem(commentManager.getComment(commentId), attributes);
        presenter.blockPrint("Comment successfully posted");
        return false;
    }
}
