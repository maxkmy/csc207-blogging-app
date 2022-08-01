package handlers;

import controllers.landing.LandingController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import useCases.ManagerData;

import java.util.Deque;
import java.util.Map;

public class SignUpRedirectHandler implements HttpHandler {

    ManagerData managerData;
    LandingController landingController;

    public SignUpRedirectHandler(ManagerData managerData) {
        this.managerData = managerData;
        landingController = new LandingController(managerData);
    }


    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
                @Override
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
                        new SignUpHandler().handleRequest(exchange);
                    }
                }
            }
        );
    }
}
