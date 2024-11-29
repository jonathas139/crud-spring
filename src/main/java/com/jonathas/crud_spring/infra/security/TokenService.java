/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jonathas.crud_spring.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jonathas.crud_spring.domain.user.User;
import com.jonathas.crud_spring.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
    
    UserRepository userRepository;
    
    @Value("${api.security.token.secret}")
    private String secret;
    
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            
            String token = JWT.create()
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch(JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
            
        }
    }

  public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
    
      private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getUserFromToken(String token) {
        try {
            String userId = validateToken(token); // Valida e extrai o subject do token
            if (userId == null) {
                throw new RuntimeException("Invalid token");
            }
            System.out.println("entrou:getUserFromToken");
            System.out.println(userId);
            return userId; // Converte para Long
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user ID from token", e);
        }
    }
}
