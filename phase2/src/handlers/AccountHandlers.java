package handlers;

import controllers.AccountController;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import useCases.ManagerData;
import viewModel.ViewModel;

import java.util.*;

public class AccountHandlers extends Handlers {
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
        ViewModel viewModel = new ViewModel();
        viewModel.put("accounts", accounts);
        viewModel.addFormField("targetUsername", "username", "text");
        String templatePath = "src/templates/search.jinja";
        present(exchange, viewModel.getContext(), templatePath);
    }

    // endpoint for search bar (with no posts yet)
    public void searchUsername(HttpServerExchange exchange) {
        ViewModel viewModel = new ViewModel();
        viewModel.put("submitBtnName", "search");
        viewModel.addFormField("targetUsername", "username", "text");
        String templatePath = "src/templates/search.jinja";
        present(exchange, viewModel.getContext(), templatePath);
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
        ViewModel viewModel = new ViewModel();
        viewModel.put("accounts", accounts);
        viewModel.put("message", "Followers");
        viewModel.put("promote", managerData.getCurrentUserRole());
        present(exchange, viewModel.getContext(), templatePath);
    }

    public void following(HttpServerExchange exchange) {
        String templatePath = "src/templates/followers.jinja";
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String user = exchange.getQueryParameters().get("username").getFirst();
        List<Map<String, String>> accounts = accountController.getFollowing(user);
        ViewModel viewModel = new ViewModel();
        viewModel.put("accounts", accounts);
        viewModel.put("message", "Following");
        viewModel.put("promote", managerData.getCurrentUserRole());
        present(exchange, viewModel.getContext(), templatePath);
    }

    public void viewHistory(HttpServerExchange exchange) {
        String templatePath = "src/templates/history.jinja";
        ViewModel viewModel = new ViewModel();
        viewModel.put("returnEndpoint", "/");
        List<String> dates = accountController.viewHistory();
        viewModel.put("dates", dates);
        present(exchange, viewModel.getContext(), templatePath);
    }
}
