package com.example.garderieapi.Config;

import com.example.garderieapi.Repository.GarderieRepository;
import com.example.garderieapi.Service.GarderieService;
import com.example.garderieapi.Service.UserService;
import com.example.garderieapi.entity.Garderie;
import com.example.garderieapi.entity.Role;
import com.example.garderieapi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Autowired
    UserService userService;
    
    @Autowired
    GarderieService garderieService;
    
    @Autowired
    GarderieRepository garderieRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    @Autowired
    private UserDetailsService userDetailsService;



    // Méthode pour extraire le token JWT de la requête HTTP
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
//    public String createToken(String username, String role, String password)  {
//        Claims claims = Jwts.claims().setSubject(username);
//        claims.put("role", role); // Ajoute le rôle de l'utilisateur aux réclamations
//        claims.put("password",password);
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + validityInMilliseconds);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }


    public String createToken(User user)  {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        String role = userService.getRoleByEmail(user.getEmail());
        claims.put("role", role); // Ajoute le rôle de l'utilisateur aux réclamations
        claims.put("password", user.getPassword());

        Long gardId=0L;
        if (role.equals("ROLE_GARD")) {

            User gerant = userService.getUserById(user.getId()).get();
            Garderie garderie =garderieRepository.findByGerant(gerant).get();
            gardId = garderie.getId();

        } else if (role.equals("ROLE_RESPONSABLE")){
            gardId = user.getGarderieRespo().getId();
        }else if (role.equals("ROLE_PARENT")) {
            gardId = user.getGarderieParent().getId();
        }

        claims.put("garderieId",gardId);


        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }



    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

