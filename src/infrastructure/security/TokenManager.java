package infrastructure.security;

import app.dtos.DTO;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

class TokenManager implements ITokenManager {
    private static final byte[] secretBytes = "secret_secret_secret_secret_secret_key".getBytes();
    private static final SecretKey secretKey = new SecretKeySpec(secretBytes, 0, secretBytes.length, "HmacSHA256");

    @Override
    public String generateToken(DTO user) {
        int id = user.id;
        String login = user.login;
        String password = user.password;
        System.out.println("TOKEN: " + id + " " + login + " " + password);
        return Jwts.builder().claim("id", id).subject(login).claim("password", password).signWith(secretKey).compact();
    }

    @Override
    public DTO getTokenInfo(String token) {
        System.out.println("TOKEN: " + token);
        int id = Integer.parseInt(String.valueOf(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id")));
        String login = (String) Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("sub");
        String password = (String) Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("password");

        DTO data = new DTO();
        data.id = id;
        data.login = login;
        data.password = password;


        return data;
    }
}
