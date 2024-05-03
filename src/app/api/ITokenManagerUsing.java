package app.api;

import infrastructure.security.ITokenManager;

public interface ITokenManagerUsing {
    void useTokenManager(ITokenManager tokenManager);
}
