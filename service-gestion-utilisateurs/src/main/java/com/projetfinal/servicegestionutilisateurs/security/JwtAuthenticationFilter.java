package com.projetfinal.servicegestionutilisateurs.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.ariani.servicegestionutilisateurs.service.JwtService;
import ma.ariani.servicegestionutilisateurs.service.UsagerDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsagerDetailsService usagerDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UsagerDetailsService usagerDetailsService) {
        this.jwtService = jwtService;
        this.usagerDetailsService = usagerDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userLogin;
        
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();
        
        logger.info("Requête reçue: " + requestMethod + " " + requestPath);
        
        // Routes qui ne nécessitent pas d'authentification
        // cette ligne permet de permettre l'accès à l'API pour la création d'un usager pour le test
        boolean isPublicRoute = requestPath.equals("/api/auth/login") || 
                                (requestPath.equals("/api/usagers") && requestMethod.equals("POST"));
        
        // Si l'en-tête Authorization est absent ou ne commence pas par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            if (!isPublicRoute) {
                logger.warn("Accès refusé pour la route protégée: " + requestMethod + " " + requestPath + " - Aucun token fourni");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                
                // Nous vérifions si la route concerne les groupes
                if (requestPath.startsWith("/api/groupes")) {
                    logger.error("Tentative d'accès non autorisée à l'API des groupes sans token!");
                }
                
                // La ligne suivante permet de continuer le filtre sans authentification
                // Mais elle est nécessaire pour permettre à Spring de gérer la réponse 401
            }
            filterChain.doFilter(request, response);
            return;
        }
        
        jwt = authHeader.substring(7);
        try {
            userLogin = jwtService.extractUsername(jwt);
            
            if (userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.usagerDetailsService.loadUserByUsername(userLogin);
                
                if (jwtService.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentification réussie pour l'utilisateur: " + userLogin + " - Accès à " + requestPath);
                } else {
                    logger.warn("Token JWT invalide pour l'utilisateur: " + userLogin);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        } catch (Exception e) {
            logger.error("JWT validation error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        
        filterChain.doFilter(request, response);
    }
} 