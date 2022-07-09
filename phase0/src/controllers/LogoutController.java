package controllers;

import gateway.ISleeper;
import gateway.Sleeper;

public class LogoutController {
    private ISleeper sleeper = new Sleeper();

    public void presentLogout(String requester) {
        sleeper.sleep(200);
        System.out.println("Successfully logged out of " + requester + ".");
    }
}
