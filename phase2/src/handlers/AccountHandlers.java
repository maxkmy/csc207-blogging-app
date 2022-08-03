package handlers;

import controllers.account.AccountController;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.*;

public class AccountHandlers {
    private ManagerData managerData;
    private AccountController accountController;
    public AccountHandlers(ManagerData managerData) {
        accountController = new AccountController(managerData);
        this.managerData = managerData;
    }

    // endpoint for POST request
    public void searchUsernameResults(HttpServerExchange exchange) {
        int limit = 10;
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String targetUsername = props.get("targetUsername").getFirst();
        List<Map<String, String>> accounts = accountController.search(targetUsername, limit);

        Map<String, Object> context = new HashMap<>();
        context.put("accounts", accounts);
        context.put("promote", managerData.getCurrentUserRole());

        Map<String, String> newTargetUsername = new HashMap<>();
        newTargetUsername.put("id", "targetUsername");
        newTargetUsername.put("label", "username");
        newTargetUsername.put("type", "text");

        List<Map<String, String>> fields = new ArrayList<>();
        fields.add(newTargetUsername);
        context.put("fields", fields);

        String templatePath = "src/templates/search.jinja";

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

        String templatePath = "src/templates/search.jinja";

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            // TODO: redirect to appropriate status code
            System.out.println(e.getMessage());
        }
    }

    public void deleteSelf(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        accountController.deleteSelf();
        new LandingHandlers(managerData).login(exchange);
    }

    public void follow(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String userToFollow = exchange.getQueryParameters().get("username").getFirst();
        accountController.follow(managerData.getCurrentUser(), userToFollow);
    }

    public void logout(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        accountController.logout();
        new LandingHandlers(managerData).login(exchange);
    }

    public void unfollow(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String userToFollow = exchange.getQueryParameters().get("username").getFirst();
        accountController.unfollow(managerData.getCurrentUser(), userToFollow);
    }

    public void followers(HttpServerExchange exchange) {
        String templatePath = "src/templates/followers.jinja";
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String user = exchange.getQueryParameters().get("username").getFirst();

        List<Map<String, String>> accounts = accountController.getFollowers(user);

        Map<String, Object> context = new HashMap<>();
        context.put("accounts", accounts);
        context.put("message", "Followers");
        context.put("promote", managerData.getCurrentUserRole());

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            // TODO: redirect to appropriate status code
            System.out.println(e.getMessage());
        }
    }

    public void following(HttpServerExchange exchange) {
        String templatePath = "src/templates/followers.jinja";
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String user = exchange.getQueryParameters().get("username").getFirst();

        List<Map<String, String>> accounts = accountController.getFollowing(user);

        Map<String, Object> context = new HashMap<>();
        context.put("accounts", accounts);
        context.put("message", "Following");
        context.put("promote", managerData.getCurrentUserRole());

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            // TODO: redirect to appropriate status code
            System.out.println(e.getMessage());
        }
    }

    public void viewHistory(HttpServerExchange exchange) {
        String templatePath = "src/templates/history.jinja";
        Map<String, Object> context = new HashMap<>();
        context.put("returnEndpoint", "/");

        List<String> dates = accountController.viewHistory();

        context.put("dates", dates);

        // get response from Jinja and send response back to client
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
