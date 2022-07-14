package controllers.like;

import controllers.appWide.RequestController;
import dataMapper.DataMapper;
import useCases.ILikeManager;

import java.util.*;


public class UnlikeController extends RequestController{
    /**
     * a data mapper responsible for mapping likes into a data structure usable by the presenters
     */
    DataMapper likeModel;
    /**
     * a use case responsible for managing likes
     */
    ILikeManager likeManager;
    /**
     * a string representing the user liking the post
     */
    String liker;
    public UnlikeController(DataMapper likeModel, ILikeManager likeManager, String liker) {
        this.likeModel = likeModel;
        this.likeManager = likeManager;
        this.liker = liker;
    }
    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "Unlike a liked post"; }
    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester){
        Scanner scanner = new Scanner(System.in);
        presenter.inlinePrint("Unlike?: ");
        String response = scanner.nextLine();
        if ((response.equals("yes") || response.equals("Yes") || response.equals("YES"))) {
            sleeper.sleep(200);
            UUID likeId = likeManager.getIdFromPostId(UUID.fromString(requester), liker);
            UUID nullId = new UUID(0, 0);
            if (!(likeId.equals(nullId))){
                likeManager.unlike(likeId);
                presenter.blockPrint("Successfully unliked");
            }
            else {
                presenter.blockPrint("Error: like was not created so cannot be unliked");
            }
            return false;
        }
        else {
            return false;
        }
    }
}
