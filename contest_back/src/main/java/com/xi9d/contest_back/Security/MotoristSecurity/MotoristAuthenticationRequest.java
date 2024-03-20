package com.xi9d.contest_back.Security.MotoristSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoristAuthenticationRequest {
private String email;
private String password;
}
