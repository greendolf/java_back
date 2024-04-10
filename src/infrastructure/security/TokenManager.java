package infrastructure.security;

import infrastructure.dtos.UserDTO;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

class TokenManager implements ITokenManager {
    private static final byte[] secretBytes = "secret_secret_secret_secret_secret_key".getBytes();
    private static final SecretKey secretKey = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA256");

    @Override
    public String generateToken(UserDTO user) {
        String login = user.getLogin();
        String password = user.getPassword();
        return Jwts.builder().subject(login).claim("password", password).signWith(secretKey).compact();
    }

    @Override
    public Map<String, String> getTokenInfo(String token) {
        String login = (String) Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("sub");
        String password = (String) Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("password");

        Map<String, String> map = new HashMap<>();
        map.put("login", login);
        map.put("password", password);

        return map;
    }
}
