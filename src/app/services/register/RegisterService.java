package app.services.register;

import app.IRegisterService;
import app.IStorage;
import app.IStorageUsing;
import app.ITokenManagerUsing;
import infrastructure.dtos.UserDTO;
import infrastructure.security.ITokenManager;

public class RegisterService implements IRegisterService, IStorageUsing, ITokenManagerUsing {
    private IStorage storage;
    private ITokenManager tokenManager;

    @Override
    public String register(String login, String password) throws Exception {
        UserDTO user = new UserDTO(login, password);
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
