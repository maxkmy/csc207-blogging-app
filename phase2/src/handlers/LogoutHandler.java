package handlers;

import controllers.account.AccountController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogoutHandler implements HttpHandler {
    ManagerData managerData;
    AccountController accountController;

    public LogoutHandler(ManagerData managerData) {
        this.managerData = managerData;
        accountController = new AccountController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        accountController.logout();
        new LoginHandler().handleRequest(exchange);
    }
}
