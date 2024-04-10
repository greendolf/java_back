package infrastructure.security;

import infrastructure.dtos.UserDTO;

import java.util.Map;

public interface ITokenManager {

    public String generateToken(UserDTO user);

    public Map<String, String> getTokenInfo(String token);
}
