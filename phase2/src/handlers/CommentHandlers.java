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

public class CommentHandlers extends Handlers {
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
                    present(exchange, context, templatePath);
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
        present(exchange, context, templatePath);
    }
}
