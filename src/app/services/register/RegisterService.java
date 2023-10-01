package app.services.register;

import app.IRegisterService;
import app.IStorage;
import app.IStorageUsing;

public class RegisterService implements IRegisterService, IStorageUsing {
    private IStorage storage;

    @Override
    public String register(String login, String password) {
        return storage.registerNewAcc(login, password);
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }
}
