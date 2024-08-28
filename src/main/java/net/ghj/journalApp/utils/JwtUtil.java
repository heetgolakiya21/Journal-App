package net.ghj.journalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 50)) // 5 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

}

/*
    > JWT (JSON Web Token)
    :- JWT is a way to securely transmit information between parties as a JSON(Javascript Object Notation) object.
    :- JWT is a compact, URL-safe token that can carry information between two parties.
    :- JWT is a string consisting of three parts, separated by dots. HEADED -- PAYLOAD -- SIGNATURE

    > HEADER :- It consists of two parts: the type of token and the signing algorithm being used such as HMAC SHA256 or RSA.
    {
        "alg": "HS256",
        "typ": "JWT"
    }

    > PAYLOAD :- It contains the claims. Claims are statement about an entity(typically, the user) and additional metadata.
    {
        "email": "email@gmail.com",
        "name": "John Doe"
    }

    > SIGNATURE :- It is used to verify that the sender of the JWT is aho it says it is and to ensure that the message wasn't changed along the way.
    :- To create the signature part, you have to take the encoded header, the encoded payload, a secret, the algorithm specified in the header, the sign that.
    HMACSHA256(
        secret,
        base64UrlEncode(header) + "." + base64UrlEncode(payload)
    )
*/