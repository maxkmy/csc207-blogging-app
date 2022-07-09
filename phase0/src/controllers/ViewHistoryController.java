package controllers;

import useCases.IAccountManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import gateway.ISleeper;
import gateway.Sleeper;

public class ViewHistoryController {
    private IAccountManager accountManager;
    private ISleeper sleeper = new Sleeper();

    public ViewHistoryController(IAccountManager accountManager){
        this.accountManager = accountManager;
    }

    public void presentViewHistory(String requester) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Here is the login history formatted like \"day-month-year hour:minute:second\"");
        for (LocalDateTime d : accountManager.getUserHistory(requester)) {
            System.out.println(formatter.format(d));
        }
        sleeper.sleep(200);
    }
}
