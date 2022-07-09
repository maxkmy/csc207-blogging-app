package controllers;

import gateway.ISleeper;
import gateway.Sleeper;

public class LogoutController extends RequestController {

    @Override
    public String getRequestDescription () {
        return "Log out of your account";
    }

    @Override
    public boolean handleRequest(String requester) {
        sleeper.sleep(200);
        System.out.println("Successfully logged out of " + requester + ".");
        return true;
    }
}
