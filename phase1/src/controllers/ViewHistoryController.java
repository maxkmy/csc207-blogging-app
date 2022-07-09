package controllers;

import useCases.IAccountManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gateway.ISleeper;
import gateway.Sleeper;

public class ViewHistoryController extends RequestController {
    public ViewHistoryController(IAccountManager accountManager){
        this.accountManager = accountManager;
    }

    @Override
    public String getRequestDescription() {return "View history of your logins";}

    @Override
    public boolean handleRequest(String requester) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Here is the login history formatted like \"day-month-year hour:minute:second\"");
        for (LocalDateTime d : accountManager.getUserHistory(requester)) {
            System.out.println(formatter.format(d));
        }
        sleeper.sleep(200);
        return false;
    }
}
