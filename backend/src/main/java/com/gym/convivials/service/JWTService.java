package com.gym.convivials.service;

import com.gym.convivials.entities.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import javax.crypto.SecretKey;

@Service
public class JWTService {
    @Value("${jwt.key}")
    private String secretKey;


    public String generateToken(UserPrincipal userDetails){
        Map<String,Object> claims =new HashMap<>();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("userId",userDetails.getUser().getUserId());
        claims.put("roles",roles);
         return Jwts.builder()
                 .claims()
                 .add(claims)
                 .subject(userDetails.getUsername())
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis()+ 60L*60*24*30*1000))
                 .and()
                 .signWith(getKey())
                 .compact();
    }

    public SecretKey getKey(){
        System.out.println(secretKey);
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload() ;
    }
    public List extractRoles(String token) {
        return extractAllClaims(token).get("roles", List.class);
    }

    public boolean validateToken(String token, UserDetails user) {
        String userName = extractUsername(token);
        System.out.println("username "+userName+" is expired:"+!isTokenExpired(token));
        return (userName.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
