package app.api;

import app.dtos.DTO;

public interface IAuthService {
    public String login(DTO user) throws Exception;
    public boolean validateToken(String token) throws Exception;
    public DTO getUserInfo(String token);
}
