package com.xi9d.contest_back.Security.MechanicalSecurity;

import com.xi9d.contest_back.Security.AuthReqResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mechanic/auth")
public class MechanicalSecurityController {
    private final MechanicalSecurityService authenticationService;

    public MechanicalSecurityController(MechanicalSecurityService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthReqResponse> register(@RequestBody MechanicalRegisterRequest registerRequest) {
        AuthReqResponse authReqResponse = authenticationService.register(registerRequest);
        return ResponseEntity.ok(authReqResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthReqResponse> authenticate(@RequestBody MechanicalAuthenticationRequest authenticationRequest) {
        AuthReqResponse authReqResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(authReqResponse);
    }
}
