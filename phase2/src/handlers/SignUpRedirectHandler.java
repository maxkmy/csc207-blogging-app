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

    public SignUpRedirectHandler(ManagerData managerData) {
        this.managerData = managerData;
    }


    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange exchange, String message) {
                Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
                // VERY IMPORTANT: "username" and "password" are based on labels of the form.
                // If the label changed to "username: " for example, props.get(...) should be changed too
                // otherwise, there will be null ptr errors.
                System.out.println(props.get("username").getFirst());
                System.out.println(props.get("password").getFirst());

                LandingController landingController = new LandingController(managerData);
                String result = landingController.signUp(props.get("username").getFirst(), props.get("password").getFirst());

                if (result.equals("Success")) {
                    managerData.setCurrentUser(props.get("username").getFirst());
                    exchange.getResponseSender().send("<meta " +
                            "http-equiv=\"refresh\" " +
                            "content=\"0.05; " +
                            "url =\n /\" />\n");
                }
                else {
                    exchange.getResponseSender().send("<html><form action='signUp' " + "method='post'>" +
                            "<input type=\"text\" id=\"username\" name=\"username\"><br><br> " +
                            "<input type=\"text\" id=\"password\" name=\"password\"><br><br>" +
                            "<input type=\"submit\" value=\"SignUp\">" +
                            "</form></html>" + "<p style=\"color:red\">" + result + "</p>");
                }
            }
        });
    }
}
