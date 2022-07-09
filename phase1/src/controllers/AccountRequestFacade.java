package controllers;

import useCases.IAccountManager;

public class AccountRequestFacade extends RequestFacade{

    public AccountRequestFacade(IAccountManager accountManager) {
        requestControllers = new RequestController[]{
                new LogoutController(),
                new ViewHistoryController(accountManager),
                new DeleteSelfController(accountManager),
        };
        buildRequests();
    }
}