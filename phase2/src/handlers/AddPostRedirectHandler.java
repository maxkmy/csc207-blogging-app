package handlers;

import controllers.landing.LandingController;
import controllers.post.PostController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import useCases.ManagerData;

import java.util.Deque;
import java.util.Map;

public class AddPostRedirectHandler implements HttpHandler {

    ManagerData managerData;
    PostController postController;

    public AddPostRedirectHandler(ManagerData managerData) {
        this.managerData = managerData;
        postController = new PostController(managerData);
    }


    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange exchange, String message) {
                Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
                String author = managerData.getCurrentUser();
                // note "username" and "password" are labels in HTML forms
                String title = props.get("title").getFirst();
                String content = props.get("content").getFirst();

                postController.addPost(title, content, author);
                new HomeHandler(managerData).handleRequest(exchange);
            }
        });
    }
}
