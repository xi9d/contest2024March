package com.xi9d.contest_back.Security;

import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Model.Motorist;
import com.xi9d.contest_back.Repository.MechanicRepository;
import com.xi9d.contest_back.Repository.MotoristRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtTokenProvider {

    private final String secret = "5586CA247556F2B567A9CA718083323EEEE7E1F82C03DD2961B5CB2B2BF21CB4";
    private final MotoristRepository motoristRepository;
    private final MechanicRepository mechanicRepository;

    public JwtTokenProvider(MotoristRepository motoristRepository, MechanicRepository mechanicRepository) {
        this.motoristRepository = motoristRepository;
        this.mechanicRepository = mechanicRepository;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails userDetails){
        String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(UserDetails userDetails) {
        String name = null;
        Long id = null;
        String role = null;

        UserType userType = getUserType(userDetails.getUsername());
        if (userType != null) {
            switch (userType) {
                case MOTORIST:
                    Optional<Motorist> motoristOptional = motoristRepository.findByEmail(userDetails.getUsername());
                    if (motoristOptional.isPresent()) {
                        Motorist motorist = motoristOptional.get();
                        name = motorist.getFullName();
                        id = motorist.getId();
                        role = motorist.getRole();
                    }
                    break;
                case MECHANIC:
                    Optional<Mechanic> mechanicOptional = mechanicRepository.findByEmail(userDetails.getUsername());
                    if (mechanicOptional.isPresent()) {
                        Mechanic mechanic = mechanicOptional.get();
                        name = mechanic.getFullName();
                        id = mechanic.getId();
                        role = mechanic.getRole();
                    }
                    break;
                default:
                    break;
            }
        }

        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("id", id)
                .claim("role", role)
                .claim("name", name)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }

    private UserType getUserType(String username) {
        if (motoristRepository.existsMotoristByEmail(username)) {
            return UserType.MOTORIST;
        } else if (mechanicRepository.existsMechanicByEmail(username)) {
            return UserType.MECHANIC;
        }
        return null;
    }
    private enum UserType {
        MOTORIST,
        MECHANIC
    }
    private SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}