package controllers;

import useCases.IAccountManager;

public class LandingPageFacade extends RequestFacade {
    public LandingPageFacade(IAccountManager accountManager) {
        requestControllers = new RequestController[]{
                new LoginController(accountManager),
                new SignUpController(accountManager),
                new QuitController(accountManager)
        };
        buildRequests();
    }
}
