package controllers.appWide;

import controllers.appWide.RequestController;

public class ReturnController extends RequestController {
    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "Return to the previous page";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        return true;
    }
}
