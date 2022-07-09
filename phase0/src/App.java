import java.util.Scanner;

import gateway.IJsonReader;
import gateway.JsonReader;
import gateway.IJsonWriter;
import gateway.JsonWriter;
import gateway.ISleeper;
import gateway.Sleeper;

import useCases.AccountManager;
import useCases.IAccountManager;
import controllers.LoginController;
import controllers.SignUpController;

import org.json.simple.JSONObject;

public class App {
    public static void main(String[] args) {
        final String dataFileDirectory = "data/userData.json";
        IJsonReader jsonReader = new JsonReader();
        JSONObject userData = jsonReader.getJsonObject(dataFileDirectory);
        IAccountManager accountManager = new AccountManager(userData);
        LoginController loginController = new LoginController(accountManager);
        SignUpController signUpController = new SignUpController(accountManager);
        ISleeper sleeper = new Sleeper();

        boolean userQuits = false;
        final String requests = "Please enter a request. \n" +
                "All valid requests are shown in quotation marks. \n" +
                "\"Log in\" - Log in to you account \n" +
                "\"Sign up\" - Sign up for an account \n" +
                "\"Quit\" - Quit the app \n" +
                "Request: ";
        do {
            sleeper.sleep(300);
            System.out.print(requests);
            Scanner scanner = new Scanner(System.in);
            String request = scanner.nextLine();
            sleeper.sleep(200);
            System.out.println();
            if (request.equals("Log in")) {
                loginController.presentLogin();
            } else if (request.equals("Sign up")) {
                signUpController.presentSignUp();
            } else if (request.equals("Quit")) {
                userQuits = true;
                JSONObject jsonObject = accountManager.toJson();
                IJsonWriter jsonWriter = new JsonWriter();
                jsonWriter.writeJsonObjectTo(dataFileDirectory, jsonObject);
            } else {
                System.out.println("The request is invalid. Please enter a request listed in quotations.");
            }
            sleeper.sleep(200);
        } while(!userQuits);
    }
}
