package app.services.register;

import app.IRegisterService;
import app.IStorage;
import app.IStorageUsing;
import app.ITokenManagerUsing;
import infrastructure.security.ITokenManager;

public class RegisterService implements IRegisterService, IStorageUsing, ITokenManagerUsing {
    private IStorage storage;
    private ITokenManager tokenManager;

    @Override
    public String register(String login, String password) {
        if (storage.addUser(login, password)) {
            return tokenManager.generateToken(login, password);
        } else {
            return null;
        }
    }

    @Override
    public void useStorage(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public void useTokenManager(ITokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }
}
