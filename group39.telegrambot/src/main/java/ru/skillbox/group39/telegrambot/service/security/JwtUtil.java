package ru.skillbox.group39.telegrambot.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:/secrets.properties")
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Long getUserId(String token){
        return getAllClaimsFromToken(token).get("userId", Long.class);
    }
}
