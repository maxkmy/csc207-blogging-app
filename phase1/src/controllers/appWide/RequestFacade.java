package controllers.appWide;

import controllers.appWide.RequestController;
import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

public class RequestFacade {
    /**
     * a Sleeper object which helps the program pause for a specified period of time
     */
    private final ISleeper sleeper = new Sleeper();
    /**
     * an array of RequestControllers that can be called by the request menu of the request facade
     */
    protected RequestController[] requestControllers;
    /**
     * a requester which calls the facade
     */
    protected String requester;
    /**
     * a string representing the menu of requests
     */
    protected String requests;
    /**
     * Constructor for a request facade responsible for reading user input for the request they would like to perform.
     *
     * @param requestControllers an array of RequestControllers that can be called by the request menu of the
     *                           request facade
     */
    public RequestFacade(RequestController[] requestControllers) {
        this.requestControllers = requestControllers;
        buildRequests();
    }

    /**
     * Validates user input for the request they wish to perform. If the request is valid, the appropriate controller
     * is called. Otherwise, an error message is printed to the user, and they are asked to choose a request again.
     */
    protected void handleRequest(String request) {
        try {
            int requestNumber = Integer.parseInt(request);
            if (requestNumber < 0 || requestNumber >= requestControllers.length) {
                System.out.println("The request entered is invalid");
            } else if (requestControllers[requestNumber].handleRequest(requester)) {
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("The request entered is invalid");
        }
        sleeper.sleep(200);
        presentRequest();
    }

    /**
     * Presents a menu of requests where the user can pick a request by inputting a number.
     */
    public void presentRequest() {
        System.out.println();
        System.out.println(requests);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your request: ");
        String request = scanner.nextLine();
        sleeper.sleep(200);
        System.out.println();
        handleRequest(request);
    }

    /**
     * Sets the requester of the request facade.
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

    /**
     * Builds a string representing a menu of requests that will be presented to the user.
     */
    protected void buildRequests() {
        int requestNumber = 0;
        StringBuilder requestsBuilder = new StringBuilder();
        for (RequestController controller : requestControllers) {
            requestsBuilder.append(requestNumber);
            requestsBuilder.append(" - ");
            requestsBuilder.append(controller.getRequestDescription());
            requestsBuilder.append("\n");
            requestNumber++;
        }
        requests = requestsBuilder.toString();
    }
}
