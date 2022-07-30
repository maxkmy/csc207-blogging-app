package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class SignUpHandler implements HttpHandler {


    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String output = "<html><form action='signUp' " + "method='post'>" +
                "<input type=\"text\" id=\"username\" name=\"username\"><br><br> " +
                "<input type=\"text\" id=\"password\" name=\"password\"><br><br>" +
                "<input type=\"submit\" value=\"Sign Up\">" +
                "</form></html>";
        exchange.getResponseSender().send(output);
    }
}
