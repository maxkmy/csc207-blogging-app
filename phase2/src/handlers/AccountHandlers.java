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

    /**
     * Represents HttpHandler Class
     * Handles GET requests for searching username
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void searchUsernameResults(HttpServerExchange exchange) {
        int limit = 10;
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String targetUsername = props.get("targetUsername").getFirst();
        List<Map<String, String>> accounts = accountController.search(targetUsername, limit);
        ViewModel viewModel = new ViewModel();
        viewModel.put("accounts", accounts);
        viewModel.addFormField("targetUsername", "Username", "text");
        String templatePath = "src/templates/search.jinja";
        viewModel.put("message", "Search Results");
        present(exchange, viewModel.getContext(), templatePath);
    }

    /**
     * Represents HttpHandler Class
     * Handles GET request for displaying search page
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void searchUsername(HttpServerExchange exchange) {
        ViewModel viewModel = new ViewModel();
        viewModel.put("submitBtnName", "search");
        viewModel.addFormField("targetUsername", "username", "text");
        String templatePath = "src/templates/search.jinja";
        present(exchange, viewModel.getContext(), templatePath);
    }

    /**
     * Represents HttpHandler Class
     * Handles DELETE request for self deletion of account
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void deleteSelf(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        accountController.deleteSelf();
        new LandingHandlers(managerData).login(exchange);
    }

    /**
     * Represents HttpHandler Class
     * Handles DELETE request for following user
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void follow(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String userToFollow = exchange.getQueryParameters().get("username").getFirst();
        accountController.follow(managerData.getCurrentUser(), userToFollow);
    }

    /**
     * Represents HttpHandler Class
     * Handles GET request for logging user out, refers to login handler
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void logout(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        accountController.logout();
        new LandingHandlers(managerData).login(exchange);
    }

    /**
     * Represents HttpHandler Class
     * Handles DELETE request for unfollowing user
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void unfollow(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String userToFollow = exchange.getQueryParameters().get("username").getFirst();
        accountController.unfollow(managerData.getCurrentUser(), userToFollow);
    }

    /**
     * Represents HttpHandler Class
     * Handles GET request for displaying list of followers for
     * specified account
     *
     * @param exchange An HTTP server request/response exchange
     */
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

    /**
     * Represents HttpHandler Class
     * Handles GET request for page displaying list of accounts
     * following specified account
     *
     * @param exchange An HTTP server request/response exchange
     */
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

    /**
     * Represents HttpHandler Class
     * Handles GET request for displaying requesters login history
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void viewHistory(HttpServerExchange exchange) {
        String templatePath = "src/templates/history.jinja";
        ViewModel viewModel = new ViewModel();
        List<String> dates = accountController.viewHistory();
        viewModel.put("dates", dates);
        present(exchange, viewModel.getContext(), templatePath);
    }
}
