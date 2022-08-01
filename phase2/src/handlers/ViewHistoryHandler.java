package handlers;

import controllers.account.AccountController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewHistoryHandler implements HttpHandler {
    ManagerData managerData;
    AccountController accountController;

    public ViewHistoryHandler(ManagerData managerData) {
        this.managerData = managerData;
        accountController = new AccountController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        String templatePath = "src/templates/history.jinja";
        Map<String, Object> context = new HashMap<>();
        context.put("returnEndpoint", "/");

        List<String> dates = accountController.viewHistory();

        context.put("dates", dates);

        // get response from Jinja and send response back to client
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
