package com.xi9d.contest_back.Security.MotoristSecurity;


import com.xi9d.contest_back.Security.AuthReqResponse;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalAuthenticationRequest;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalRegisterRequest;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/motorist/auth")
public class MotoristSecurityController {
    private final MotoristSecurityService authenticationService;

    @Autowired
    public MotoristSecurityController(MotoristSecurityService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthReqResponse> register(@RequestBody MotoristRegisterRequest registerRequest) {
        AuthReqResponse authReqResponse = authenticationService.register(registerRequest);
        return ResponseEntity.ok(authReqResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthReqResponse> authenticate(@RequestBody MotoristAuthenticationRequest authenticationRequest) {
        AuthReqResponse authReqResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(authReqResponse);
    }
}
