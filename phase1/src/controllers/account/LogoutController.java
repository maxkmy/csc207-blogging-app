package controllers.account;

import controllers.appWide.RequestController;

public class LogoutController extends RequestController {
    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription () {
        return "Log out of your account";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        presenter.inlinePrint("Successfully logged out of " + requester + ".");
        return true;
    }
}
