import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Headers;

public class Server {
    HttpHandler ROUTES;
    Undertow server;

    public Server() {
        ROUTES = new RoutingHandler()
                .setFallbackHandler(exchange -> {
                    exchange.setStatusCode(404);
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Page Not Found");
                });

        server = Undertow.builder()
                .addHttpListener(8080, "localhost", ROUTES)
                .build();
    }

    public void start() {
        server.start();
    }
}
