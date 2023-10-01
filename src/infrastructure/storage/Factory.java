package infrastructure.storage;

import app.IStorage;
import infrastructure.storage.textStorage.TextStorage;

public class Factory {
    private static IStorage instance = null;

    public static IStorage createTextStorage() {
        if (instance == null) {
            instance = new TextStorage();
        }
        return instance;
    }
}
