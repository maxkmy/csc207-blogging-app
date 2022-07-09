package controllers.post;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import useCases.IPostManager;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class ViewPostNoPermissionController extends RequestController {
    /**
     * A data structure usable by presenters that represents posts.
     */
    ArrayList<HashMap<String, String>> posts;

    /**
     * Constructor for a controller responsible for reading input to view a post.
     *
     * @param posts        a data structure usable by presenters that represents posts.
     * @param postManager  a use case responsible for managing posts
     */
    public ViewPostNoPermissionController(ArrayList<HashMap<String, String>> posts, IPostManager postManager) {
        this.posts = posts;
        this.postManager = postManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "View post";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the number of the post you wish to view: ");
            int postNumber = Integer.parseInt(scanner.nextLine());
            if (0 < postNumber || postNumber >= posts.size()) {
                throw new NumberFormatException();
            }
            HashMap<String, String> post = posts.get(postNumber);
            System.out.println("Written by: " + post.get("author"));
            System.out.println("Title: " + post.get("title"));
            System.out.println("Content: " + post.get("content"));
        } catch (NumberFormatException e) {
            System.out.println("The number input is invalid.");
            handleRequest(requester);
        }
        RequestFacade postRequests = new RequestFacade(new RequestController[]{
            new ReturnController()
        });
        postRequests.presentRequest();
        return false;
    }
}
