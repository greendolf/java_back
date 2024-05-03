package app.services;

import app.api.IAuthService;
import app.api.IStorage;
import app.api.IStorageUsing;
import app.api.ITokenManagerUsing;
import app.dtos.DTO;
import infrastructure.security.ITokenManager;

public class AuthService implements IAuthService, IStorageUsing, ITokenManagerUsing {
    private IStorage storage;
    private ITokenManager tokenManager;

    @Override
    public String login(DTO user) throws Exception {
        user.id = storage.findUser(user);
        if (user.id != -1) {
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

    @Override
    public boolean validateToken(String token) {
        DTO user = tokenManager.getTokenInfo(token);
        return user.id != -1;
    }

    @Override
    public DTO getUserInfo(String token) {
        return tokenManager.getTokenInfo(token);
    }
}
