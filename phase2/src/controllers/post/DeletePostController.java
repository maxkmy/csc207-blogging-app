package controllers.post;

import controllers.appWide.RequestController;
import useCases.IPostManager;
import dataMapper.DataMapper;

import java.util.UUID;

public class DeletePostController  extends RequestController {
    /**
     * a data mapper that helps map posts into a data structure usable by presenters
     */
    DataMapper postModel;

    /**
     * Constructor for a controller responsible for reading input to sign users up.
     *
     * @param postModel    a data mapper that helps map posts into a data structure usable by presenters
     * @param postManager  a use case responsible for managing posts
     */
    public DeletePostController(DataMapper postModel, IPostManager postManager) {
        this.postModel = postModel;
        this.postManager = postManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Delete post";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        postManager.deletePost(UUID.fromString(requester));
        postModel.deleteItem("id", requester);
        presenter.blockPrint("Successfully deleted post");
        return true;
    }
}
