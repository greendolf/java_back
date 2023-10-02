package app.services.register;

import app.IRegisterService;
import app.IStorage;
import app.IStorageUsing;

public class RegisterService implements IRegisterService, IStorageUsing {
    private IStorage storage;

    @Override
    public String register(String login, String password) {
        if (storage.addUser(login, password)) {
            return "token";
        } else {
            return null;
        }
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
