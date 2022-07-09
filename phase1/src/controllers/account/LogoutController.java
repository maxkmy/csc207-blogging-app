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
        sleeper.sleep(200);
        System.out.println("Successfully logged out of " + requester + ".");
        return true;
    }
}
