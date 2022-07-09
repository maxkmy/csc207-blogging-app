package controllers;

import useCases.IAccountManager;

public class AdminRequestFacade extends RequestFacade {
    public AdminRequestFacade(IAccountManager accountManager) {
        requestControllers = new RequestController[]{
                new BanUserController(accountManager),
                new UnbanUserController(accountManager),
                new PromoteUserController(accountManager),
                new CreateAdminController(accountManager),
                new DeleteUserController(accountManager),
                new ViewHistoryController(accountManager),
                new LogoutController()
        };
        buildRequests();
    }
}