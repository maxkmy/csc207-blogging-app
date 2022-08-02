import handlers.RoutingHandlerFactory;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Headers;
import useCases.ManagerData;

public class Server {
    HttpHandler ROUTES;
    Undertow server;

    public Server(ManagerData managerData) {

        RoutingHandlerFactory routingHandlerFactory = new RoutingHandlerFactory();

        ROUTES = new RoutingHandler()
                .get("/login", routingHandlerFactory.getHandler("login", managerData))
                .post("/login", routingHandlerFactory.getHandler("loginRedirect", managerData))
                .get("/", routingHandlerFactory.getHandler("home", managerData))
                .post("/", routingHandlerFactory.getHandler("homeRedirect", managerData))
                .get("/signUp", routingHandlerFactory.getHandler("signUp", managerData))
                .post("/signUp", routingHandlerFactory.getHandler("signUpRedirect", managerData))
                .get("/history", routingHandlerFactory.getHandler("viewHistory", managerData))
                .get("/logout", routingHandlerFactory.getHandler("logout", managerData))
                .get("/deleteSelf", routingHandlerFactory.getHandler("deleteSelf", managerData))
                .get("/addPost", routingHandlerFactory.getHandler("addPost", managerData))
                .post("/addPost", routingHandlerFactory.getHandler("addPostRedirect", managerData))
                .get("/viewSelfProfile", routingHandlerFactory.getHandler("viewSelfProfile", managerData))
                .delete("/deletePost/{postId}", routingHandlerFactory.getHandler("deletePost", managerData))
                .post("/addComment/{postId}", routingHandlerFactory.getHandler("addComment", managerData))
                .get("/viewComments/{postId}", routingHandlerFactory.getHandler("viewComments", managerData))
                .get("/viewPost/{postId}", routingHandlerFactory.getHandler("viewPost", managerData))
                .get("/viewProfile/{username}", routingHandlerFactory.getHandler("viewProfile", managerData))
                .delete("/follow/{username}", routingHandlerFactory.getHandler("follow", managerData))
                .setFallbackHandler(exchange -> {
                    exchange.setStatusCode(404);
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Page Not Found");
                });

        server = Undertow.builder()
                .addHttpListener(8080, "localhost", ROUTES)
                .build();
    }

    public void start() {
        server.start();
    }
}
