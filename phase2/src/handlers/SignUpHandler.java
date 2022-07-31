package handlers;

import com.hubspot.jinjava.Jinjava;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;

public class SignUpHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        Map<String, String> context = new HashMap<>();
        context.put("submitBtnName", "sign up");

        Jinjava jinja = new Jinjava();
        String fileName = "src/templates/loginSignUpForm.jinja";
        String template = "";
        try {
            template = Files.readString(Paths.get(fileName));
        } catch(IOException e) { // TODO: redirect to appropriate status code
            System.out.println(e.getMessage());
        }

        String htmlOutput = jinja.render(template, context);
        exchange.getResponseSender().send(htmlOutput);
    }
}
