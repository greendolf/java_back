package app.services.auth;

import app.IAuthService;
import app.IStorage;
import app.IStorageUsing;
import app.ITokenManagerUsing;
import infrastructure.dtos.UserDTO;
import infrastructure.security.ITokenManager;

import java.util.Map;

public class AuthService implements IAuthService, IStorageUsing, ITokenManagerUsing {
    private IStorage storage;
    private ITokenManager tokenManager;

    @Override
    public String login(String login, String password) {
        UserDTO user = new UserDTO(login, password);
        if (storage.findUser(user)) {
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
        Map<String, String> tokenInfo = tokenManager.getTokenInfo(token);
        UserDTO user = new UserDTO(tokenInfo.get("login"), tokenInfo.get("password"));
        return storage.findUser(user);
    }

    @Override
    public Map<String, String> getUserInfo(String token) {
        return tokenManager.getTokenInfo(token);
    }
}
