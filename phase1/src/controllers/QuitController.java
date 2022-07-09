package controllers;

import useCases.IAccountManager;

import gateway.IWriter;
import gateway.Writer;

public class QuitController extends RequestController {
    final String dataFileDirectory = "data/userData.txt";
    IAccountManager accountManager;
    public QuitController(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public String getRequestDescription() {
        return "Quit the app";
    }

    public boolean handleRequest(String requester) {
        IWriter writer = new Writer(dataFileDirectory);
        writer.write(accountManager.getAccountMap());
//        JSONObject jsonObject = accountManager.toJson();
//        IJsonWriter jsonWriter = new JsonWriter();
//        jsonWriter.writeJsonObjectTo(dataFileDirectory, jsonObject);
        return true;
    }
}
