package handlers;

import controllers.account.AccountController;
import controllers.post.PostController;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.QueryParameterUtils;
import presenters.JinjaPresenter;
import useCases.ManagerData;

import java.io.IOException;
import java.util.*;

public class PostHandlers {
    private PostController postController;
    private AccountController accountController;
    private ManagerData managerData;
    public PostHandlers(ManagerData managerData) {
        this.managerData = managerData;
        postController = new PostController(managerData);
        accountController = new AccountController(managerData);
    }

    public void addPost(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path
        String templatePath = "src/templates/form.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        context.put("submitBtnName", "add post");

        Map<String, String> title = new HashMap<>();
        title.put("id", "title");
        title.put("label", "title");
        title.put("type", "text");

        Map<String, String> content = new HashMap<>();
        content.put("id", "content");
        content.put("label", "content");
        content.put("type", "text");

        List<Map<String, String>> fields = new ArrayList<>();
        fields.add(title);
        fields.add(content);
        context.put("fields", fields);

        context.put("action", "/addPost");
        context.put("method", "post");

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

    public void addPostRedirect(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        exchange.getRequestReceiver().receiveFullString(new Receiver.FullStringCallback() {
                public void handle(HttpServerExchange exchange, String message) {
                    Map<String, Deque<String>> props = QueryParameterUtils.parseQueryString(message, "UTF_8");
                    String author = managerData.getCurrentUser();
                    // note "username" and "password" are labels in HTML forms
                    String title = props.get("title").getFirst();
                    String content = props.get("content").getFirst();
                    title = title.replace('+', ' ');
                    content = content.replace('+', ' ');

                    postController.addPost(title, content, author);

                    new HomeHandler(managerData).handleRequest(exchange);
                }
            }
        );
    }

    public void deletePost(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        String postIdString = exchange.getQueryParameters().get("postId").getFirst();
        UUID postId = UUID.fromString(postIdString);
        postController.deletePost(postId);
    }

    public void viewPost(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");
        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String postIdString = props.get("postId").getFirst();
        UUID postId = UUID.fromString(postIdString);
        Map<String, String> post = postController.getPost(postId);

        Map<String, Object> context = new HashMap<>();
        context.put("post", post);

        String templatePath = "src/templates/post.jinja";

        try {
            JinjaPresenter presenter = new JinjaPresenter(context, templatePath);
            String htmlOutput = presenter.present();
            // TODO: delete after testing
            System.out.println("html: " + htmlOutput);
            exchange.getResponseSender().send(htmlOutput);
        } catch (IOException e) {
            System.out.println("error occured");
            System.out.println(e.getMessage());
        }
    }

    public void viewProfile(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        String templatePath = "src/templates/profile.jinja";

        Map<String, Deque<String>> props = exchange.getQueryParameters();
        String username = props.get("username").getFirst();
        String requester = managerData.getCurrentUser();
        List<Map<String, String>> posts = postController.getPostsWrittenBy(username);

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

    public void viewSelfProfile(HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html");

        // designate a template path
        String templatePath = "src/templates/profile.jinja";

        // Populate context map
        Map<String, Object> context = new HashMap<>();
        String username = managerData.getCurrentUser();
        List<Map<String, String>> posts = postController.getPostsWrittenBy(username);

        context.put("posts", posts);
        context.put("returnEndpoint", "/");

        context.put("username", managerData.getCurrentUser());
        context.put("isAdmin", managerData.getCurrentUserRole());

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
