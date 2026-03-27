package com.example.userManagement.service;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String PRIVATEKEY="votre_cle_tres_longue_et_securisee_de_plus_de_32_caracteres_ici_123456";

    public Key getPrivateKey(){
        return Keys.hmacShaKeyFor(PRIVATEKEY.getBytes());
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+864000000))
                .signWith(getPrivateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getPrivateKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();

}
}