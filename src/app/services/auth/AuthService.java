package app.services.auth;

import app.IAuthService;
import app.IStorage;
import app.IStorageUsing;

public class AuthService implements IAuthService, IStorageUsing {
    private IStorage storage;

    @Override
    public String login(String login, String password) {
        if (storage.findUser(login, password)) {
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
