package handlers;

import controllers.comment.CommentController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.*;

public class ViewCommentHandler implements HttpHandler {
    ManagerData managerData;
    CommentController commentController;

    public ViewCommentHandler(ManagerData managerData) {
        this.managerData = managerData;
        commentController = new CommentController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String postIdString = props.get("postId").getFirst();
        UUID postId = UUID.fromString(postIdString);

        Map<String, Object> context = new HashMap<>();
        List<Map<String, String>> comments = commentController.getCommentsUnder(postId);
        context.put("comments", comments);
        context.put("postId", postIdString);
        context.put("returnEndpoint", "/viewPost/" + postIdString);

        String templatePath = "src/templates/comments.jinja";

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
