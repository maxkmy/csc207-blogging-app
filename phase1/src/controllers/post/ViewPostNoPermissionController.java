package controllers.post;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.comment.AddCommentController;
import controllers.comment.ViewCommentController;
import controllers.like.AddLikeController;
import controllers.like.UnlikeController;
import dataMapper.DataMapper;
import presenters.PostPresenter;
import useCases.ICommentManager;
import useCases.ILikeManager;
import useCases.IPostManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ViewPostNoPermissionController extends RequestController {
    /**
     * a data mapper to store posts
     */
    DataMapper postModel;
    /**
     * a data mapper to store comments
     */
    DataMapper commentModel;
    /**
     * a use case responsible for managing comments
     */
    ICommentManager commentManager;
    /**
     * a use case responsible for managing posts
     */
    IPostManager postManager;
    /**
     * a dataMapper to Store likes
     */
    DataMapper likeModel;
    /**
     * a use case responsible for managing likes
     */
    ILikeManager likeManager;

    /**
     * Constructor for a controller responsible for reading input to view a post.
     *
     * @param postModel      a data mapper that helps map posts into a data structure usable by presenters
     * @param postManager    a use case responsible for managing posts
     * @param commentModel   a data mapper that helps map comments into a data structure usable by presenters
     * @param commentManager a use case responsible for managing comments
     * @param likeManager    a use case responsible for managing likes
     * @param likeModel      a data mapper that helps map likes into a data structures usable by presenters
     */
    public ViewPostNoPermissionController(DataMapper postModel, IPostManager postManager, DataMapper commentModel,
                                        ICommentManager commentManager, ILikeManager likeManager, DataMapper likeModel) {
        this.postManager = postManager;
        this.postModel = postModel;
        this.commentModel = commentModel;
        this.commentManager = commentManager;
        this.likeManager = likeManager;
        this.likeModel = likeModel;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected String getRequestDescription() {
        return "View post";
    }

    /**
     * @inheritDoc
     */
    @Override
    protected boolean handleRequest(String requester) {
        try {
            ArrayList<HashMap<String, String>> posts = postModel.getModel();
            PostPresenter postPresenter = new PostPresenter();
            postPresenter.printPosts(posts);
            Scanner scanner = new Scanner(System.in);
            presenter.inlinePrint("Enter the number of the post you wish to view or 0 to return to your profile: ");
            int request = Integer.parseInt(scanner.nextLine());
            if (request == 0) {
                return false;
            } else if (request  <= posts.size()) {
                int postNumber = request - 1;
                HashMap<String, String> post = posts.get(postNumber);
                postPresenter.printPost(post);
                RequestFacade postRequests = new RequestFacade(new RequestController[]{
                        new AddCommentController(commentModel, commentManager, requester),
                        new ViewCommentController(commentModel, commentManager),
                        new AddLikeController(likeModel,likeManager,requester),
                        new UnlikeController(likeModel,likeManager,requester),
                        new ReturnController()
                });
                postRequests.setRequester(posts.get(postNumber).get("id"));
                postRequests.presentRequest();
            } else {
                presenter.blockPrint("The number input is invalid.");
                handleRequest(requester);
            }
        } catch (NumberFormatException e) {
            presenter.blockPrint("The number input is invalid.");
            handleRequest(requester);
        }
        return false;
    }
}
