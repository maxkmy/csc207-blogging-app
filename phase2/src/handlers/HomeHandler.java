package handlers;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeHandler implements HttpHandler {

    ManagerData managerData;

    public HomeHandler(ManagerData managerData) {
        this.managerData = managerData;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        if (managerData.getCurrentUser() == null) {
            exchange.getResponseSender().send("<meta " +
                    "http-equiv=\"refresh\" " +
                    "content=\"0.05; " +
                    "url =\n /login\" />\n");
        } else {
            // TODO: have multiple buttons that allow users to delete self, logout and view history
            List<Map<String, String>> requests = new ArrayList<>();
            Map<String, String> viewHistory = new HashMap<>();
            viewHistory.put("endpoint", "/history");
            viewHistory.put("description", "view login history");
            requests.add(viewHistory);

            Map<String, String> logout = new HashMap<>();
            logout.put("endpoint", "/logout");
            logout.put("description", "logout");
            requests.add(logout);

            Map<String, String> deleteSelf = new HashMap<>();
            deleteSelf.put("endpoint", "/deleteSelf");
            deleteSelf.put("description", "delete account");
            requests.add(deleteSelf);

            Map<String, String> addPost = new HashMap<>();
            addPost.put("endpoint", "/addPost");
            addPost.put("description", "add post");
            requests.add(addPost);

            Map<String, String> viewSelfProfile = new HashMap<>();
            viewSelfProfile.put("endpoint", "/viewSelfProfile");
            viewSelfProfile.put("description", "profile");
            requests.add(viewSelfProfile);

            Map<String, Object> context = new HashMap<>();
            context.put("requests", requests);

            String templatePath = "src/templates/menu.jinja";
            try {
                JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
                String htmlOutput = presenter.present();
                exchange.getResponseSender().send(htmlOutput);
            } catch (IOException e) {
                // TODO: redirect to appropriate status code
                System.out.println(e.getMessage());
            }
        }
    }
}
