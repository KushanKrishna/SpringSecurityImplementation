package com.kushankrishna.SpringSecuriy.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Autowired
    UserDetailsService userDetailsService;
    private static final String SECRET = "lYsCMeIgXuoTOBcrqLvhXHN1yUk72zXF8Ct63NRiphc=";

    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) this.userDetailsService.loadUserByUsername(userName).getAuthorities();
        claims.put("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return creteToken(claims, userName);

    }

    private String creteToken(Map<String, Object> claims, String userName) {
        return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)).signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
