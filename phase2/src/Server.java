import handlers.*;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Headers;
import useCases.ManagerData;

public class Server {
    private HttpHandler ROUTES;
    private Undertow server;

    public Server(ManagerData managerData) {
        LandingHandlers landingHandlers = new LandingHandlers(managerData);
        AccountHandlers accountHandlers = new AccountHandlers(managerData);
        AdminHandlers adminHandlers = new AdminHandlers(managerData);
        CommentHandlers commentHandlers = new CommentHandlers(managerData);
        PostHandlers postHandlers = new PostHandlers(managerData);

        ROUTES = new RoutingHandler()
                .get("/login", landingHandlers::login)
                .post("/login", landingHandlers::loginRedirect)
                .get("/", new HomeHandler(managerData))
                .post("/", new HomeRedirectHandler(managerData))
                .get("/signUp", landingHandlers::signUp)
                .post("/signUp", landingHandlers::signUpRedirect)
                .get("/history", accountHandlers::viewHistory)
                .get("/logout", accountHandlers::logout)
                .get("/deleteSelf", accountHandlers::deleteSelf)
                .get("/addPost", postHandlers::addPost)
                .post("/addPost", postHandlers::addPostRedirect)
                .get("/viewSelfProfile", postHandlers::viewSelfProfile)
                .delete("/deletePost/{postId}", postHandlers::deletePost)
                .post("/addComment/{postId}", commentHandlers::addComment)
                .get("/viewComments/{postId}", commentHandlers::viewComments)
                .get("/viewPost/{postId}", postHandlers::viewPost)
                .get("/viewProfile/{username}", postHandlers::viewProfile)
                .delete("/follow/{username}", accountHandlers::follow)
                .delete("/unfollow/{username}", accountHandlers::unfollow)
                .get("/searchUsername", accountHandlers::searchUsername)
                .get("/searchUsernameResults", accountHandlers::searchUsernameResults)
                .get("/followers/{username}", accountHandlers::followers)
                .get("/following/{username}", accountHandlers::following)
                .delete("/promote/{username}", adminHandlers::promote)
                .delete("/deleteUser/{username}", adminHandlers::deleteUser)
                .delete("/ban/{username}", adminHandlers::ban)
                .delete("/unban/{username}", adminHandlers::unban)
                .get("/feed", postHandlers::getFeed)
                .setFallbackHandler(exchange -> {
                    exchange.setStatusCode(404);
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Page Not Found");
                });

        server = Undertow.builder()
                .addHttpListener(8080, "localhost", ROUTES)
                .build();
    }

    /**
     * Starts the server
     */
    public void start() {
        server.start();
    }
}
