package handlers;

import io.undertow.server.HttpServerExchange;
import presenters.JinjaPresenter;

import java.io.IOException;
import java.util.Map;

public class Handlers {
    /**
     * Calls a JinjaPresenter to present a view model to frontend.
     *
     * @param exchange An HTTP server request/response exchange
     * @param context A mapping of strings to the objects for reference in Jinja templates
     * @param template a path to a Jinja template
     */
    public void present(HttpServerExchange exchange, Map<String, Object> context, String template) {
        try {
            JinjaPresenter presenter = new JinjaPresenter(context, template);
            exchange.getResponseSender().send(presenter.present());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}