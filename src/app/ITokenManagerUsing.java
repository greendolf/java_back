package app;

import infrastructure.security.ITokenManager;

public interface ITokenManagerUsing {
    void useTokenManager(ITokenManager tokenManager);
}
