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
import java.util.List;
import java.util.Map;

public class AddPostHandler implements HttpHandler {
    ManagerData managerData;
    PostController postController;

    public AddPostHandler(ManagerData managerData) {
        this.managerData = managerData;
        postController = new PostController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path
        String templatePath = "src/templates/form.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        context.put("submitBtnName", "add post");

        Map<String, String> title = new HashMap<>();
        title.put("id", "title");
        title.put("label", "title");
        title.put("type", "text");

        Map<String, String> content = new HashMap<>();
        content.put("id", "content");
        content.put("label", "content");
        content.put("type", "text");

        List<Map<String, String>> fields = new ArrayList<>();
        fields.add(title);
        fields.add(content);
        context.put("fields", fields);

        context.put("action", "/addPost");
        context.put("method", "post");

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
