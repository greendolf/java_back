package app.services.login;

import app.ILoginService;
import app.IStorage;
import app.IStorageUsing;

public class LoginService implements ILoginService, IStorageUsing {
    private IStorage storage;

    @Override
    public String login(String login, String password) {
        return storage.checkLogPas(login, password);
    }
    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
