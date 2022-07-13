package controllers.like;

import controllers.appWide.RequestController;
import dataMapper.DataMapper;
import useCases.ILikeManager;

import java.util.*;

public class AddLikeController extends RequestController{

    /**
     * a data mapper responsible for mapping likes into a data structure usable by the presenters
     */
    DataMapper likeModel;
    /**
     * a use case responsible for managin likes
     */
    ILikeManager likeManager;
    /**
     * a string representing the user liking the post
     */
    String liker;

    public AddLikeController(DataMapper likeModel, ILikeManager likeManager, String liker) {
        this.likeModel = likeModel;
        this.likeManager = likeManager;
        this.liker = liker;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "Create a new like"; }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester){
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Like:");
        String title = scanner.nextLine();
        sleeper.sleep(200);
        UUID likeID = likeManager.addLike(UUID.fromString(requester), liker);
        UUID nullUUID = new UUID(0, 0);
        if (!(likeID.equals(nullUUID))){
            String[] attributes = new String[]{"postId", "user liking the post", "id"};
            likeModel.addItem(likeManager.getLike(likeID), attributes);
            presenter.blockPrint("Like successfully created");
            return false;
        }
        else{
            presenter.blockPrint("You cannot like a post twice");
            return false;
        }
    }

}
