package com.example.bankapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /** Genera un token dopo che hai effettuato il login contenente le informazioni dell'account */
    public String generateToken(String username, String role) {

        long expiration = 86400000;
        return  Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    /** Estrae il corpo del token se valido */
    public Claims extractClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Estrae l'username dal token */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /** Estrae il ruolo personalizzato salvato nel token */
    public String extractRole(String token) {
        return (String) extractClaims(token).get("role");
    }

    /** Controlla se un token è valido */
    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException e) { return false; }
    }
}

