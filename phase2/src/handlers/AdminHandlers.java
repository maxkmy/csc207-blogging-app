package handlers;

import controllers.AdminController;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import useCases.ManagerData;

public class AdminHandlers {

    private ManagerData managerData;
    private AdminController adminController;

    public AdminHandlers(ManagerData managerData) {
        this.managerData = managerData;
        adminController = new AdminController(managerData);
    }

    public void promote(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        if (managerData.getCurrentUserRole()) {
            String userToPromote = exchange.getQueryParameters().get("username").getFirst();
            adminController.promote(userToPromote);
        }
        else {
            exchange.getResponseSender().send("invalid permissions");
        }
    }

    public void ban(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        if (managerData.getCurrentUserRole()) {
            String user = exchange.getQueryParameters().get("username").getFirst();
            adminController.ban(user);
        }
        else {
            exchange.getResponseSender().send("invalid permissions");
        }
    }

    public void unban(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        if (managerData.getCurrentUserRole()) {
            String user = exchange.getQueryParameters().get("username").getFirst();
            adminController.unban(user);
        }
        else {
            exchange.getResponseSender().send("invalid permissions");
        }
    }

    public void deleteUser(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        if (managerData.getCurrentUserRole()) {
            String user = exchange.getQueryParameters().get("username").getFirst();
            adminController.deleteUser(user);
        }
        else {
            exchange.getResponseSender().send("invalid permissions");
        }
    }
}
