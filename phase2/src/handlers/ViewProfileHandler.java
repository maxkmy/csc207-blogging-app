package handlers;

import controllers.account.AccountController;
import controllers.post.PostController;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class ViewProfileHandler implements HttpHandler {

    ManagerData managerData;
    PostController postController;
    AccountController accountController;

    public ViewProfileHandler(ManagerData managerData) {
        this.managerData = managerData;
        postController = new PostController(managerData);
        accountController = new AccountController(managerData);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String templatePath = "src/templates/profile.jinja";

        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String username = props.get("username").getFirst();
        String requester = managerData.getCurrentUser();
        ArrayList<HashMap<String, String>> posts = postController.getPostsWrittenBy(username);

        Map<String, Object> context = new HashMap<>();
        context.put("posts", posts);

        context.put("username", username);
        context.put("isAdmin", managerData.getCurrentUserRole());

        context.put("followStatus", accountController.isFollowing(requester, username));

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
