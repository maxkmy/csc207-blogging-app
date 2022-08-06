package handlers;

import controllers.PostController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import useCases.ManagerData;
import viewModel.ViewModel;

public class HomeHandler extends Handlers implements HttpHandler {

    ManagerData managerData;
    PostController postController;

    public HomeHandler(ManagerData managerData) {
        this.managerData = managerData;
        this.postController = new PostController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

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
            viewModel.addEndpoint("/viewSelfProfile", "profile");
            viewModel.put("posts", postController.getFollowingPosts(managerData.getCurrentUser()));
            String templatePath = "src/templates/menu.jinja";
            present(exchange, viewModel.getContext(), templatePath);
        }
    }
}
