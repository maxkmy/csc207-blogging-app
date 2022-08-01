package handlers;

import controllers.account.AccountController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import useCases.ManagerData;

public class DeleteSelfHandler implements HttpHandler {
    ManagerData managerData;
    AccountController accountController;

    public DeleteSelfHandler(ManagerData managerData) {
        this.managerData = managerData;
        accountController = new AccountController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        accountController.deleteSelf();
        new LoginHandler().handleRequest(exchange);
    }
}
