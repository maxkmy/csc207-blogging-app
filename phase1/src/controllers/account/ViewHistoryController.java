package controllers.account;

import controllers.appWide.RequestController;
import useCases.IAccountManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewHistoryController extends RequestController {
    /**
     * Constructor for a controller responsible for handling input related to viewing a list of log in dates.
     *
     * @param accountManager  a use case responsible for managing accounts
     */
    public ViewHistoryController(IAccountManager accountManager){
        this.accountManager = accountManager;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getRequestDescription() {
        return "View history of your logins";
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean handleRequest(String requester) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        presenter.blockPrint("Here is the login history formatted like \"day-month-year hour:minute\": ");
        for (LocalDateTime d : accountManager.getUserHistory(requester)) {
            presenter.blockPrint(formatter.format(d));
        }
        return false;
    }
}
