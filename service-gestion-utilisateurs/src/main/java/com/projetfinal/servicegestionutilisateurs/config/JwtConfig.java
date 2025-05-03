package com.projetfinal.servicegestionutilisateurs.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class JwtConfig {
    
    @Value("${jwt.secret:bibliothequeSecretKeyParDefautTresTresLongue123456789}")
    private String secret;
    
    @Value("${jwt.expiration:86400}") // 24 heures en secondes par défaut
    private Long expiration;
    
    @Bean
    public Key jwtKey() {
        // Utilisation de Keys.secretKeyFor pour générer une clé sécurisée pour HS512
        // au lieu d'utiliser directement la chaîne de caractères
        if (secret.length() < 64) {
            // Si la clé configurée est trop courte, générer une clé sécurisée
            return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        } else {
            // Si la clé est suffisamment longue, l'utiliser
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
    }
    
    public Long getExpiration() {
        return expiration;
    }
} 