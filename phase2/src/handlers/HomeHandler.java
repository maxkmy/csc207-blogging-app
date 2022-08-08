package handlers;

import controllers.AccountController;
import controllers.PostController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import useCases.ManagerData;
import viewModel.ViewModel;

public class HomeHandler extends Handlers implements HttpHandler {

    private ManagerData managerData;
    private PostController postController;

    private AccountController accountController;
    /**
     * Constructor for a HomeHandler
     *
     * @param managerData a class that contains data for all 3 managers
     */
    public HomeHandler(ManagerData managerData) {
        this.managerData = managerData;
        this.postController = new PostController(managerData);
        this.accountController = new AccountController(managerData);
    }

    @Override
    /*
     * @inheritdoc
     */
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String username = managerData.getCurrentUser();

        if (managerData.getCurrentUser() == null) {
            exchange.getResponseSender().send("<meta " +
                    "http-equiv=\"refresh\" " +
                    "content=\"0.05; " +
                    "url =\n /login\" />\n");
        } else {
            ViewModel viewModel = new ViewModel();
            viewModel.addEndpoint( "/history", "view login history");
            viewModel.addEndpoint("/logout", "logout");
            viewModel.addEndpoint("/deleteSelf",  "delete account");
            viewModel.addEndpoint("/addPost", "add post");

            viewModel.put("posts", postController.getPostsWrittenBy(managerData.getCurrentUser()));
            viewModel.put("isAdmin", managerData.getCurrentUserRole());
            viewModel.put("author", managerData.getCurrentUser());
            viewModel.put("followers", accountController.getFollowers(username).size());
            viewModel.put("following", accountController.getFollowing(username).size());
            viewModel.put("username", managerData.getCurrentUser());

            String templatePath = "src/templates/menu.jinja";
            present(exchange, viewModel.getContext(), templatePath);
        }
    }
}
