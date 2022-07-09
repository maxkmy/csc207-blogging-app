package controllers;

import gateway.ISleeper;
import gateway.Sleeper;
import useCases.IAccountManager;

abstract public class RequestController {
    protected ISleeper sleeper = new Sleeper();
    protected IAccountManager accountManager;

    // returns a description of what the request does
    abstract String getRequestDescription();

    // requester is the username of the user, null if n/a
    // return boolean is whether to exit user and return to sign in
    abstract boolean handleRequest(String requester);
}
