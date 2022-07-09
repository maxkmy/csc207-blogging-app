package controllers;

import gateway.ISleeper;
import gateway.Sleeper;
import useCases.IAccountManager;

public class DeleteSelfController extends RequestController {

    public DeleteSelfController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {
        return "Delete your account";
    }

    @Override
    public boolean handleRequest(String requester) {
        accountManager.deleteSelf(requester);
        System.out.println("Successfully deleted user " + requester);
        return true;
    }
}
