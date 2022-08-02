package handlers;

import controllers.account.AccountController;
import controllers.post.PostController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import useCases.ManagerData;

import java.util.UUID;

public class followHandler implements HttpHandler {

    ManagerData managerData;
    AccountController accountController;

    public followHandler(ManagerData managerData) {
        this.managerData = managerData;
        accountController = new AccountController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String userToFollow = exchange.getQueryParameters().get("username").getFirst();
        accountController.follow(managerData.getCurrentUser(), userToFollow);
    }
}
