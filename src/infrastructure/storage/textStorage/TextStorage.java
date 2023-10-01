package infrastructure.storage.textStorage;

import app.IStorage;

public class TextStorage implements IStorage {
//    private final String path = ;
    @Override
    public String checkLogPas(String login, String password) {
        return "token";
    }

    @Override
    public String registerNewAcc(String login, String password) {
        return "token";
    }
}
