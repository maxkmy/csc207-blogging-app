package controllers.appWide;

import gateway.ISleeper;
import gateway.Sleeper;
import presenters.CommentPresenter;
import presenters.PostPresenter;
import presenters.Presenter;
import useCases.IAccountManager;
import useCases.ICommentManager;
import useCases.IPostManager;

abstract public class RequestController {
    /**
     * a Sleeper object which helps the program pause for a specified period of time
     */
    protected ISleeper sleeper = new Sleeper();
    /**
     * a use case responsible for managing accounts
     */
    protected IAccountManager accountManager;
    /**
     * a use case responsible for managing posts
     */
    protected IPostManager postManager;
    /**
     * a use case responsible for managing comments
     */
    protected ICommentManager commentManager;
    /**
     * a presenter object responsible for printing messages to CLI
     */
    protected Presenter presenter = new Presenter();
    /**
     * a presenter object responsible for printing comments to CLI
     */
    protected CommentPresenter commentPresenter = new CommentPresenter();
    /**
     * a presenter object responsible for printing posts to CLI
     */
    protected PostPresenter postPresenter = new PostPresenter();

    /**
     * Returns a string describing what type of request is handled by the request controller
     *
     * @return a description of what request is handled by the request controller
     */
    protected abstract String getRequestDescription();

    /**
     * Handles the request based on implementation in subclasses and returns whether to return to previous page
     *
     * @param requester a unique identifier of the object that called this controller
     * @return          whether the user should be sent to the previous page after the request is handled
     */
    protected abstract boolean handleRequest(String requester);
}
