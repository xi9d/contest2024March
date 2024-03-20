package com.xi9d.contest_back.Security.MotoristSecurity;

import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Model.Motorist;
import com.xi9d.contest_back.Repository.MotoristRepository;
import com.xi9d.contest_back.Security.AuthReqResponse;
import com.xi9d.contest_back.Security.JwtTokenProvider;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalAuthenticationRequest;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalRegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MotoristSecurityService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final MotoristRepository motoristRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MotoristSecurityService(AuthenticationManager authenticationManager,
                                   MotoristRepository motoristRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.motoristRepository = motoristRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthReqResponse register(MotoristRegisterRequest registerRequest) {
        Motorist client = new Motorist();
        System.out.println("registering ");
        client.setFullName(registerRequest.getName());
        client.setEmail(registerRequest.getEmail());
        client.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        client.setTelephone(registerRequest.getTelephone());
        client.setRole("MOTORIST");
        motoristRepository.save(client);
        String token = jwtTokenProvider.generateToken(client);
        return new AuthReqResponse(token);
    }

    public AuthReqResponse authenticate(MotoristAuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Motorist motorist = motoristRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtTokenProvider.generateToken(motorist);
        return new AuthReqResponse(token);
    }

}
