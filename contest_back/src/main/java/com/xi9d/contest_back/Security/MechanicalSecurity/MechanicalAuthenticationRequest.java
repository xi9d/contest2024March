package com.xi9d.contest_back.Security.MechanicalSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MechanicalAuthenticationRequest {
    private String email;
    private String password;
}
