package com.example.D_CLUTTER.securityConfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private final String secreteKey;
    public JwtService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHa256");
            SecretKey sk = keyGen.generateKey();
            secreteKey = Base64.getEncoder().encodeToString(sk.getEncoded());
            log.info("Secret Key:: {}" , secreteKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String GenerateToken(String username) {

        Map<String , Object> claims = new HashMap<>(); // at this point it is empty
        claims.put("username", username);
        return Jwts.builder()  //i use Jwts i import the token
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 20000 * 60 * 60 * 10 ))
                .and()
                .signWith(this.getkey()) // i generate a key here
                .compact();

    }

    private SecretKey getkey() {
        byte[] keyByte = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(keyByte);
    }


    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject);
    }
    private <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
           return Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    @Override
    public String toString() {
        return "JwtService{" +
                "secreteKey='" + secreteKey + '\'' +
                '}';
    }
}
