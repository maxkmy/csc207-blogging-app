package handlers;

import io.undertow.server.HttpServerExchange;
import presenters.JinjaPresenter;

import java.io.IOException;
import java.util.Map;

public class Handlers {
    public void present(HttpServerExchange exchange, Map<String, Object> context, String template) {
        try {
            JinjaPresenter presenter = new JinjaPresenter(context, template);
            exchange.getResponseSender().send(presenter.present());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}