package controllers.post;

import controllers.appWide.RequestController;
import useCases.IPostManager;
import dataMapper.DataMapper;

import java.util.Scanner;
import java.util.UUID;

public class AddPostController extends RequestController {
    DataMapper postModel;

    /**
     * Constructor for a controller responsible for reading input to sign users up.
     *
     * @param postModel    a data mapper that helps map posts into a data structure usable by presenters
     * @param postManager  a use case responsible for managing posts
     */
    public AddPostController(DataMapper postModel, IPostManager postManager) {
        this.postModel = postModel;
        this.postManager = postManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Create a post";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Title: ");
        String title = scanner.nextLine();
        sleeper.sleep(200);
        System.out.print("Content: ");
        String content = scanner.nextLine();
        sleeper.sleep(200);
        UUID postId = postManager.addPost(title, content, requester);
        String[] attributes = new String[]{"author", "title", "content", "id"};
        postModel.addItem(postManager.getPost(postId), attributes);
        System.out.println("Post successfully posted");
        return false;
    }
}
