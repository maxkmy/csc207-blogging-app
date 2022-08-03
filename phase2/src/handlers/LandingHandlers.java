package handlers;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandingHandlers {
    private ManagerData managerData;

    public LandingHandlers(ManagerData managerData) {
        this.managerData = managerData;
    }

    public void login(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path (TODO: maybe this should be moved to SignUpHandler's constructor)
        String templatePath = "src/templates/login.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        context.put("submitBtnName", "log in");

        Map<String, String> username = new HashMap<>();
        username.put("id", "username");
        username.put("label", "username");
        username.put("type", "text");

        Map<String, String> password = new HashMap<>();
        password.put("id", "password");
        password.put("label", "password");
        password.put("type", "text");

        List<Map<String, String>> fields = new ArrayList<>();
        fields.add(username);
        fields.add(password);
        context.put("fields", fields);

        context.put("action", "/login");
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
