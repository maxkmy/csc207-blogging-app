package handlers;

import controllers.comment.CommentController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddCommentHandler implements HttpHandler {
    ManagerData managerData;
    CommentController commentController;

    public AddCommentHandler(ManagerData managerData) {
        this.managerData = managerData;
        commentController = new CommentController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
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
}
