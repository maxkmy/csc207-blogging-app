package handlers;

import io.undertow.io.Receiver;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import useCases.ManagerData;

import java.util.Deque;
import java.util.Map;

public class HomeRedirectHandler implements HttpHandler {

    private ManagerData managerData;

    /**
     * Constructor for a HomeRedirectHandler
     *
     * @param managerData a class that contains data for all 3 managers
     */
    public HomeRedirectHandler(ManagerData managerData) {
        this.managerData = managerData;
    }

    @Override
    /*
     * @inheritdoc
     */
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
            @Override
            public void handle(HttpServerExchange exchange, String message) {
                Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");

                if(props.containsKey("SignOut")) {
                    managerData.setCurrentUser(null);
                    exchange.getResponseSender().send("<meta " +
                            "http-equiv=\"refresh\" " +
                            "content=\"0.05; " +
                            "url =\n /login\" />\n");
                }
            }
        });
    }
}
