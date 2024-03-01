package infrastructure.security;

import java.util.Map;

public interface ITokenManager {

    public String generateToken(String login, String password);

    public Map<String, String> getTokenInfo(String token);
}
