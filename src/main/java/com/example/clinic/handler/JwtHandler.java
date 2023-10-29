package com.example.clinic.handler;

import com.example.clinic.dto.UserDto;
import com.example.clinic.message.SimpleMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static java.time.Instant.now;

@Component
public class JwtHandler {
    private static final String secret = "U2VjcmV0X2tleV9mb3JfZ2VuZXJhdGVfSldUX3Rva2Vu";
    private static final Key hmac = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());

    public static String createAuthToken(UserDto user) {
        return Jwts.builder()
                .claim(SimpleMessage.USERNAME, user.getUsername())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now()))
                .setExpiration(Date.from(now().plus(10, ChronoUnit.MINUTES)))
                .signWith(hmac)
                .compact();
    }

    public static Jws<Claims> parseJWT(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(hmac)
                .build()
                .parseClaimsJws(jwt);
    }

    public static String getUsernameFromJWT(String jwt) {
        jwt = jwt.replace(SimpleMessage.BEARER, SimpleMessage.EMPTY_STRING);
        Jws<Claims> jws = parseJWT(jwt);
        Claims claims = jws.getBody();
        return (String) claims.get(SimpleMessage.USERNAME);
    }

//    public boolean checkJwt(String jwt,User user) {
//
//        return user.getJwt().equals(jwt);
//    }


//    public void deactivateToken(HttpServletRequest request) {
//        User user = repository.getUserById(getIdFromJWT(request.getHeader("auth-token")));
//        user.setJwt("");
//        repository.saveUser(user);
//    }
}
