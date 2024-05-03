package infrastructure.security;

import app.dtos.DTO;

public interface ITokenManager {

    public String generateToken(DTO user);

    public DTO getTokenInfo(String token);
}
