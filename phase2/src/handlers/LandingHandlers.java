package handlers;

import controllers.LandingController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import useCases.ManagerData;
import viewModel.ViewModel;

import java.util.*;

public class LandingHandlers extends Handlers {
    private ManagerData managerData;
    private LandingController landingController;

    public LandingHandlers(ManagerData managerData) {
        this.managerData = managerData;
        landingController = new LandingController(managerData);
    }

    public void login(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String templatePath = "src/templates/form.jinja";
        ViewModel viewModel = new ViewModel();
        viewModel.put("errorMessage", "<p> Don't have an account? </p> <a href=\"signUp\"> Sign up </a>");
        viewModel.put("submitBtnName", "log in");
        viewModel.addFormField("username", "username", "text");
        viewModel.addFormField("password", "password", "password");
        viewModel.put("action", "/login");
        viewModel.put("method", "post");
        // get response from Jinja and send response back to client
        present(exchange, viewModel.getContext(), templatePath);
    }

    public void loginRedirect(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange exchange, String message) {
                Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
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
                    login(exchange);
                }
            }
        });
    }

    public void signUp(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String templatePath = "src/templates/form.jinja";

        ViewModel viewModel = new ViewModel();
        viewModel.put("errorMessage", "<p> Already have an account? </p> <a href=\"login\"> Log in </a>");
        viewModel.put("submitBtnName", "sign up");
        viewModel.addFormField("username", "username", "text");
        viewModel.addFormField("password", "password", "password");
        viewModel.put("action", "/signUp");
        viewModel.put("method", "post");

        present(exchange, viewModel.getContext(), templatePath);
    }

    public void signUpRedirect(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
                public void handle(HttpServerExchange exchange, String message) {
                    Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
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
                        new LandingHandlers(managerData).signUp(exchange);
                    }
                }
            }
        );
    }
}
