package handlers;

import controllers.post.PostController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViewPostHandler implements HttpHandler {
    private PostController postController;

    public ViewPostHandler(ManagerData managerData) {
        postController = new PostController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String postIdString = props.get("postId").getFirst();
        UUID postId = UUID.fromString(postIdString);
        Map<String, String> post = postController.getPost(postId);

        Map<String, Object> context = new HashMap<>();
        context.put("post", post);

        String templatePath = "src/templates/post.jinja";

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            // TODO: delete after testing
            System.out.println("html: " + htmlOutput);
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            System.out.println("error occured");
            System.out.println(e.getMessage());
        }
    }
}
