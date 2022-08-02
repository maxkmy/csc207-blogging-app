package handlers;

import io.undertow.server.HttpHandler;
import useCases.ManagerData;

public class RoutingHandlerFactory {

    // TODO possibly move out of handlers package as is not appropriate
    public HttpHandler getHandler(String request, ManagerData managerData) {
        switch (request) {
            case "login":
                return new LoginHandler();
            case "loginRedirect":
                return new LoginRedirectHandler(managerData);
            case "home":
                return new HomeHandler(managerData);
            case "homeRedirect":
                return new HomeRedirectHandler(managerData);
            case "signUp":
                return new SignUpHandler();
            case "signUpRedirect":
                return new SignUpRedirectHandler(managerData);
            case "viewHistory":
                return new ViewHistoryHandler(managerData);
            case "logout":
                return new LogoutHandler(managerData);
            case "deleteSelf":
                return new DeleteSelfHandler(managerData);
            case "addPost":
                return new AddPostHandler(managerData);
            case "addPostRedirect":
                return new AddPostRedirectHandler(managerData);
            case "viewSelfProfile":
                return new ViewSelfProfileHandler(managerData);
            case "deletePost":
                return new DeletePostHandler(managerData);
            case "addComment":
                return new AddCommentHandler(managerData);
            case "viewComments":
                return new ViewCommentHandler(managerData);
            case "viewPost":
                return new ViewPostHandler(managerData);
            case "viewProfile":
                return new ViewProfileHandler(managerData);
                case "follow":
                return new followHandler(managerData);
        }
        return new ErrorMessageHandler("No handler exists in RoutingHandlers factory named: " + request);
    }
}
