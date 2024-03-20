package com.xi9d.contest_back.Security.MechanicalSecurity;

import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Repository.MechanicRepository;
import com.xi9d.contest_back.Security.AuthReqResponse;
import com.xi9d.contest_back.Security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MechanicalSecurityService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final MechanicRepository mechanicRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public MechanicalSecurityService(AuthenticationManager authenticationManager,
                                     MechanicRepository mechanicRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.mechanicRepository = mechanicRepository;
        this.jwtTokenProvider = jwtTokenProvider;

    }

    public AuthReqResponse register(MechanicalRegisterRequest registerRequest) {
        Mechanic client = new Mechanic();
        client.setFullName(registerRequest.getName());
        client.setEmail(registerRequest.getEmail());
        client.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        client.setTelephone(registerRequest.getTelephone());
        client.setRole("MECHANIC");
        mechanicRepository.save(client);
        String token = jwtTokenProvider.generateToken(client);
        return new AuthReqResponse(token);
    }

    public AuthReqResponse authenticate(MechanicalAuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Mechanic mechanic = mechanicRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtTokenProvider.generateToken(mechanic);
        return new AuthReqResponse(token);
    }




}
