package handlers;

import controllers.post.PostController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import useCases.ManagerData;

import java.util.Deque;
import java.util.Map;
import java.util.UUID;

public class DeletePostHandler implements HttpHandler {
    ManagerData managerData;
    PostController postController;

    public DeletePostHandler(ManagerData managerData) {
        this.managerData = managerData;
        postController = new PostController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        System.out.println("delete endpoint reached");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange exchange, String message) {
                Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
                System.out.println(props.toString());
                String postIdString = props.get("postId").getFirst();
                UUID postId = UUID.fromString(postIdString);
                postController.deletePost(postId);
            }
        });
    }
}
