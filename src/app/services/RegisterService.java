package app.services;

import app.api.IRegisterService;
import app.api.IStorage;
import app.api.IStorageUsing;
import app.api.ITokenManagerUsing;
import app.dtos.DTO;
import infrastructure.security.ITokenManager;

public class RegisterService implements IRegisterService, IStorageUsing, ITokenManagerUsing {
    private IStorage storage;
    private ITokenManager tokenManager;

    @Override
    public String register(DTO user) throws Exception {
        if (storage.addUser(user)) {
            return tokenManager.generateToken(user);
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
