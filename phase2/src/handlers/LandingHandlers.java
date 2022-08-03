package handlers;

import controllers.landing.LandingController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.*;

public class LandingHandlers {
    private ManagerData managerData;
    private LandingController landingController;

    public LandingHandlers(ManagerData managerData) {
        this.managerData = managerData;
        landingController = new LandingController(managerData);
    }

    public void login(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path
        String templatePath = "src/templates/form.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        context.put("errorMessage", "<p> Don't have an account? </p> <a href=\"signUp\"> Sign up </a>");
        context.put("submitBtnName", "log in");

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

    public void loginRedirect(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange exchange, String message) {
                Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
                // note "username" and "password" are labels in HTML forms
                String username = props.get("username").getFirst();
                String password = props.get("password").getFirst();

                String result = landingController.login(username, password);

                if (result.equals("Success")) {
                    managerData.setCurrentUser(username);
                    exchange.getResponseSender().send("<meta " +
                            "http-equiv=\"refresh\" " +
                            "content=\"0.05; " +
                            "url =\n /\" />\n");
                }
                else {
                    // TODO: SignUpHandler's constructor should be changed to take in a base context mapping
                    // TODO: this way we can pass in {"errorMessage": result}
                    login(exchange);
                }
            }
        });
    }

    public void signUp(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path (TODO: maybe this should be moved to SignUpHandler's constructor)
        String templatePath = "src/templates/form.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        context.put("errorMessage", "<p> Already have an account? </p> <a href=\"login\"> Log in </a>");
        context.put("submitBtnName", "sign up");

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

    public void signUpRedirect(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
                public void handle(HttpServerExchange exchange, String message) {
                    Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
                    // note "username" and "password" are labels in HTML forms
                    String username = props.get("username").getFirst();
                    String password = props.get("password").getFirst();

                    String result = landingController.signUp(username, password);

                    if (result.equals("Success")) {
                        managerData.setCurrentUser(username);
                        exchange.getResponseSender().send("<meta " +
                                "http-equiv=\"refresh\" " +
                                "content=\"0.05; " +
                                "url =\n /\" />\n");
                    }
                    else {
                        // TODO: SignUpHandler's constructor should be changed to take in a base context mapping
                        // TODO: this way we can pass in {"errorMessage": result}
                        new LandingHandlers(managerData).signUp(exchange);
                    }
                }
            }
        );
    }
}
