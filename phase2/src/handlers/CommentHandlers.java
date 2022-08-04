package handlers;

import controllers.CommentController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.*;

public class CommentHandlers {
    private ManagerData managerData;
    private CommentController commentController;
    public CommentHandlers(ManagerData managerData) {
        this.managerData = managerData;
        commentController = new CommentController(managerData);
    }

    public void addComment(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
                @Override
                public void handle(HttpServerExchange exchange, String message) {
                    Map<String, Deque<String>> props1 = exchange.getQueryParameters();
                    String postIdString = props1.get("postId").getFirst();
                    UUID postId = UUID.fromString(postIdString);
                    Map<String, Deque<String>> props2 = QueryParameterUtils.parseQueryString(message, "UTF_8");
                    String comment = props2.get("comment").getFirst();
                    comment = comment.replace('+', ' ');
                    String author = managerData.getCurrentUser();
                    commentController.addComment(postId, comment, author);

                    Map<String, Object> context = new HashMap<>();
                    context.put("endpoint", "viewComments/" + postIdString);
                    String templatePath = "src/templates/redirect.jinja";
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
        );
    }

    public void viewComments(HttpServerExchange exchange) {
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
