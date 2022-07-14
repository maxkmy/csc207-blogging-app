package controllers.like;

import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import dataMapper.DataMapper;
import useCases.ILikeManager;
import presenters.LikePresenter;

import java.util.UUID;

public class ViewLikeController extends RequestController{
    /**
     * a use case responsible for managing likes
     */
    ILikeManager likeManager;
    /**
     * a data mapper to store likes
     */
    DataMapper likeModel;

    /**
     * Constructor for a controller responsible for accessing likes
     *
     * @param likeModel a DataMapper model for modeling likes
     * @param likeManager use case responsible for managing likes
     */
    public ViewLikeController(DataMapper likeModel, ILikeManager likeManager) {
        this.likeModel = likeModel;
        this.likeManager = likeManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() { return "View likes"; }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        likeModel.reset();
        likeModel.addItems(
                likeManager.getLikesUnder(UUID.fromString(requester)),
                new String[]{"liker"}
        );

        LikePresenter likePresenter = new LikePresenter();
        likePresenter.printTotalLikes(likeManager.totalLikesUnder(UUID.fromString(requester)));
        likePresenter.printLikes(likeModel.getModel());


        RequestFacade viewLikeFacade = new RequestFacade(
                new RequestController[] {
                        new ReturnController()

                }
        );
        viewLikeFacade.setRequester(requester);
        viewLikeFacade.presentRequest();
        return false;
    }
}
