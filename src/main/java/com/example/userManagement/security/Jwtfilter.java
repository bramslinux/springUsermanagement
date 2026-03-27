package com.example.userManagement.security;
import com.example.userManagement.service.JwtService;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.ServletException;
import  jakarta.servlet.http.*;
import jakarta.servlet.FilterChain;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class Jwtfilter extends OncePerRequestFilter {
    private JwtService jwtService;
    public Jwtfilter(JwtService jwtService){
        this.jwtService=jwtService;
    }
    @Override
    protected  void  doFilterInternal(HttpServletRequest request , HttpServletResponse reponse, FilterChain filterchain ) throws ServletException, IOException {
        String authHeader = request.getHeader("authorization");
        if (authHeader !=null && authHeader.startsWith("Berear")){
            String token = authHeader.substring(7);
            try {
               String username = jwtService.extractUsername(token);
               if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                   UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
               }
            } catch (Exception e) {}
            }
        filterchain.doFilter(request,reponse);
        }

    }

