package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class LoginHandler implements HttpHandler {

    String errorMessage;

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String output = "<html><form action='login' " + "method='post'>" +
                "<input type=\"text\" id=\"username\" name=\"username\"><br><br> " +
                "<input type=\"text\" id=\"password\" name=\"password\"><br><br>" +
                "<input type=\"submit\" value=\"Login\">" +
                "</form></html>" +
                "Don't have an account? <a href=\"signUp\">Sign Up</a>";
        exchange.getResponseSender().send(output);
    }
}
