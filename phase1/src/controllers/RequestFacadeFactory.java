package controllers;

import controllers.account.*;
import controllers.admin.*;
import controllers.appWide.RequestController;
import controllers.appWide.RequestFacade;
import controllers.appWide.ReturnController;
import controllers.landing.LoginController;
import controllers.landing.QuitController;
import controllers.landing.SignUpController;
import controllers.post.AddPostController;
import controllers.post.DeletePostController;
import controllers.post.ViewPostPermissionController;
import dataMapper.DataMapper;
import useCases.IAccountManager;
import useCases.ICommentManager;
import useCases.IPostManager;

public class RequestFacadeFactory {
    /**
     * a use case responsible for managing accounts
     */
    IAccountManager accountManager;
    /**
     * a use case responsible for managing posts
     */
    IPostManager postManager;
    /**
     * a use case responsible for managing comments
     */
    ICommentManager commentManager;
    /**
     * a data mapper containing models
     */
    DataMapper postModel;
    /**
     * a data mapper containing comments
     */
    DataMapper commentModel;

    /**
     * Constructor responsible for setting up the use cases for managing accounts and post
     *
     * @param accountManager a use case responsible for managing accounts
     * @param postManager    a use case responsible for managing posts
     */
    public RequestFacadeFactory(IAccountManager accountManager, IPostManager postManager) {
        this.accountManager = accountManager;
        this.postManager = postManager;
    }

    /**
     * Method that returns an appropriate request controller based on the facade type that is required
     *
     * @param facadeType a string representing the facade type that is needed
     * @return the request facade based on facadeType
     */
    public RequestFacade create(String facadeType) {
        switch (facadeType) {
            // account and admin related facades
            case "accountRequestFacade":
                return new RequestFacade(new RequestController[]{
                    new ViewHistoryController(accountManager),
                    new DeleteSelfController(accountManager, postManager),
                    new FollowController(accountManager),
                    new UnfollowController(accountManager),
                    new ViewFollowerController(accountManager),
                    new ViewFollowingController(accountManager),
                    new ViewSelfProfileController(postManager, commentManager),
                    new LogoutController(),
                });
            case "adminRequestFacade":
                return new RequestFacade(new RequestController[]{
                    new BanUserController(accountManager),
                    new UnbanUserController(accountManager),
                    new PromoteUserController(accountManager),
                    new CreateAdminController(accountManager),
                    new DeleteUserController(accountManager, postManager),
                    new DeleteSelfController(accountManager, postManager),
                    new ViewHistoryController(accountManager),
                    new FollowController(accountManager),
                    new UnfollowController(accountManager),
                    new ViewFollowerController(accountManager),
                    new ViewFollowingController(accountManager),
                    new ViewSelfProfileController(postManager, commentManager),
                    new LogoutController(),
                });
            // landing page facade
            case "landingPageFacade":
                return new RequestFacade(new RequestController[]{
                    new LoginController(accountManager, postManager, commentManager),
                    new SignUpController(accountManager, postManager, commentManager),
                    new QuitController(accountManager, postManager, commentManager)
                });
            // post related facade
            case "profileFacade":
                return new RequestFacade(new RequestController[]{
                    new AddPostController(postModel, postManager),
                    new ViewPostPermissionController(postModel, postManager, commentModel, commentManager),
                    new ReturnController()
                });
            case "postRequestNoPermissionFacade":
                return new RequestFacade(new RequestController[]{
                    new ReturnController()
                });
            case "postRequestPermissionFacade":
                return new RequestFacade(new RequestController[]{
                    new DeletePostController(postModel, postManager),
                    new ReturnController()
                });
        }
        return null;
    }
}