package handlers;

import controllers.post.PostController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewSelfProfileHandler implements HttpHandler {
    ManagerData managerData;
    PostController postController;

    public ViewSelfProfileHandler(ManagerData managerData) {
        this.managerData = managerData;
        postController = new PostController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path
        String templatePath = "src/templates/posts.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        String username = managerData.getCurrentUser();
        ArrayList<HashMap<String, String>> posts = postController.getPostsWrittenBy(username);

        context.put("posts", posts);
        context.put("returnEndpoint", "/");

        context.put("username", managerData.getCurrentUser());
        context.put("isAdmin", managerData.getCurrentUserRole());

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
