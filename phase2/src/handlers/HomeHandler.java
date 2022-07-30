package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import useCases.ManagerData;

public class HomeHandler implements HttpHandler {

    ManagerData managerData;

    public HomeHandler(ManagerData managerData) {
        this.managerData = managerData;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        if(managerData.getCurrentUser() == null) {
            exchange.getResponseSender().send("<meta " +
                    "http-equiv=\"refresh\" " +
                    "content=\"0.05; " +
                    "url =\n /login\" />\n");
        } else {
            exchange.getResponseSender().send("Hello " + managerData.getCurrentUser() + "<br><br>" +
                    "<form action=\"\" method=\"post\">\n" +
                    "    <input type=\"submit\" name=\"SignOut\" value=\"Sign Out\" />\n" +
                    "</form>");
        }
    }
}
