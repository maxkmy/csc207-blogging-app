package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class ErrorMessageHandler implements HttpHandler {

    private final String value;

    public ErrorMessageHandler(String value) {
        this.value = value;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send(value + "\n");
    }
}