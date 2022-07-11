package controllers.comment;

import dataMapper.DataMapper;
import gateway.ICommentSorter;
import presenters.Presenter;
import useCases.ICommentManager;

import java.util.Scanner;

public class CommentSorterFacade {
    /**
     * an array of RequestControllers that can be called by the request menu of the request facade
     */
    protected ICommentSorter[] commentSorters;
    /**
     * a requester which calls the facade
     */
    protected String requester;
    /**
     * a string representing the menu of requests
     */
    protected String requests;
    /**
     * a presenter object responsible for printing messages to CLI
     */
    protected Presenter presenter = new Presenter();
    protected DataMapper commentModel;
    protected ICommentManager commentManager;
    /**
     * Constructor for a comment sorter facade responsible for allowing the user to change how they would like to
     * sort comments
     *
     * @param commentSorters an array of ICommentSorter that can be called by the sorter menu of the
     *                           comment sorter facade
     */
    public CommentSorterFacade(DataMapper commentModel, ICommentManager commentManager, ICommentSorter[] commentSorters) {
        this.commentSorters = commentSorters;
        this.commentManager = commentManager;
        this.commentModel = commentModel;
        buildRequests();
    }

    /**
     * Validates user input for the request they wish to perform. If the request is valid, the appropriate controller
     * is called. Otherwise, an error message is printed to the user, and they are asked to choose a request again.
     */
    protected void handleRequest(String request) {
        try {
            int requestNumber = Integer.parseInt(request);
            if (requestNumber < 0 || requestNumber >= commentSorters.length) {
                presenter.blockPrint("The request entered is invalid. ");
            } else {
                presenter.blockPrint("Successfully changed comment sorting method to: " +
                        commentSorters[requestNumber].printType());
//                RequestFacade viewCommentFacade = new RequestFacade(
//                        new RequestController[] {
//                                new ViewCommentController(commentModel, commentManager, commentSorters[requestNumber])
//                        }
//                );
//                // TODO: Right now, the comment sorter is working, but it is in an infinite loop after as the ReturnController
//                // goes back to previous page. See if there is any way to fix
//                viewCommentFacade.setRequester(requester);
//                viewCommentFacade.presentRequest();
                this.commentManager.changeCommentSorter(commentSorters[requestNumber]);
                return;
            }
        } catch (NumberFormatException e) {
            presenter.blockPrint("The request entered is invalid. ");
        }
        presentRequest();
    }

    /**
     * Presents a menu of requests where the user can pick a request by inputting a number.
     */
    public void presentRequest() {
        presenter.blockPrint("");
        presenter.blockPrint(requests);
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Please enter your request: ");
        String request = scanner.nextLine();
        presenter.blockPrint("");
        handleRequest(request);
    }

    /**
     * Sets the requester of the comment sorter facade.
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

    /**
     * Builds a string representing a menu of sorting types that will be presented to the user.
     */
    protected void buildRequests() {
        int requestNumber = 0;
        StringBuilder requestsBuilder = new StringBuilder();
        for (ICommentSorter controller : commentSorters) {
            requestsBuilder.append(requestNumber);
            requestsBuilder.append(" - ");
            requestsBuilder.append(controller.printType());
            requestsBuilder.append("\n");
            requestNumber++;
        }
        requests = requestsBuilder.toString();
    }
}
