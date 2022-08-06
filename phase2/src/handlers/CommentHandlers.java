package handlers;

import controllers.CommentController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import useCases.ManagerData;
import viewModel.ViewModel;

import java.util.*;

public class CommentHandlers extends Handlers {
    private ManagerData managerData;
    private CommentController commentController;
    public CommentHandlers(ManagerData managerData) {
        this.managerData = managerData;
        commentController = new CommentController(managerData);
    }

    /**
     * Represents HttpHandler Class
     * Handles POST request for adding comments
     *
     * @param exchange An HTTP server request/response exchange
     */
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
                    ViewModel viewModel = new ViewModel();
                    viewModel.put("endpoint", "viewComments/" + postIdString);
                    String templatePath = "src/templates/redirect.jinja";
                    present(exchange, viewModel.getContext(), templatePath);
                }
            }
        );
    }

    /**
     * Represents HttpHandler Class
     * Handles GET request for viewing comments
     *
     * @param exchange An HTTP server request/response exchange
     */
    public void viewComments(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String postIdString = props.get("postId").getFirst();
        UUID postId = UUID.fromString(postIdString);

        ViewModel viewModel = new ViewModel();
        List<Map<String, String>> comments = commentController.getCommentsUnder(postId);
        viewModel.put("comments", comments);
        viewModel.put("postId", postIdString);
        viewModel.put("returnEndpoint", "/viewPost/" + postIdString);

        String templatePath = "src/templates/comments.jinja";
        present(exchange, viewModel.getContext(), templatePath);
    }
}
