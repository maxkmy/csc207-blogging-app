package handlers;

import controllers.account.AccountController;
import io.undertow.server.HttpServerExchange;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.*;

public class AccountHandlers {
    private AccountController accountController;
    public AccountHandlers(ManagerData managerData) {
        accountController = new AccountController(managerData);
    }

    // endpoint for POST request
    public void searchUsernameResults(HttpServerExchange exchange) {
        int limit = 10;
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String targetUsername = props.get("targetUsername").getFirst();
        List<Map<String, String>> accounts = accountController.search(targetUsername, limit);

        Map<String, Object> context = new HashMap<>();
        context.put("accounts", accounts);

        String templatePath = "src/templates/accounts.jinja";

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            // TODO: redirect to appropriate status code
            System.out.println(e.getMessage());
        }
    }

    // endpoint for search bar (with no posts yet)
    public void searchUsername(HttpServerExchange exchange) {
        Map<String, Object> context = new HashMap<>();
        context.put("submitBtnName", "search");

        Map<String, String> targetUsername = new HashMap<>();
        targetUsername.put("id", "targetUsername");
        targetUsername.put("label", "username");
        targetUsername.put("type", "text");

        List<Map<String, String>> fields = new ArrayList<>();
        fields.add(targetUsername);
        context.put("fields", fields);

        String templatePath = "src/templates/accounts.jinja";

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            // TODO: redirect to appropriate status code
            System.out.println(e.getMessage());
        }
    }
}
