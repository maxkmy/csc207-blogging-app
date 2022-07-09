package controllers;

import gateway.ISleeper;
import gateway.Sleeper;

import java.util.Scanner;

abstract class RequestFacade {
    private final ISleeper sleeper = new Sleeper();
    protected RequestController[] requestControllers;
    protected String requester;
    protected String requests;

    // handles the request input from the user
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

    // presents data to user so they can understand options
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

    // sets requester to the username of current user
    protected void setRequester(String requester) {
        this.requester = requester;
    }
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
