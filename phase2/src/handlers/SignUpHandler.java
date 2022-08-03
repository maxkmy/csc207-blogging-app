package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path
        String templatePath = "src/templates/form.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        context.put("errorMessage", "<p> Already have an account? </p> <a href=\"login\"> Log in </a>");
        context.put("submitBtnName", "Sign up");

        Map<String, String> username = new HashMap<>();
        username.put("id", "username");
        username.put("label", "username");
        username.put("type", "text");

        Map<String, String> password = new HashMap<>();
        password.put("id", "password");
        password.put("label", "password");
        password.put("type", "password");

        List<Map<String, String>> fields = new ArrayList<>();
        fields.add(username);
        fields.add(password);
        context.put("fields", fields);

        context.put("action", "/signUp");
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
