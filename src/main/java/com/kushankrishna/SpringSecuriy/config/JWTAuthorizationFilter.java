package com.kushankrishna.SpringSecuriy.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final String SECRET = "lYsCMeIgXuoTOBcrqLvhXHN1yUk72zXF8Ct63NRiphc=";

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(checkJWTToken(request,response)){
                Claims claims = validateToken(request);
                if(claims.get("authorities")!=null){
                    setUpSpringAuthentication(claims);
                }
                else {
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
        filterChain.doFilter(request,response);
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            System.out.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN,e.getMessage());
        }
    }

    private void setUpSpringAuthentication(Claims claims) {
        List<String> authorities = (List) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken  = request.getHeader(HEADER).replace(PREFIX,"");
       return   Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET))).build().parseSignedClaims(jwtToken).getPayload();

    }

    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }
}
