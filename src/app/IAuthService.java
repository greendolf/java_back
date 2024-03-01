package app;

import java.util.Map;

public interface IAuthService {
    public String login(String login, String password);
    public boolean validateToken(String token);
    public Map<String, String> getUserInfo(String token);
}
