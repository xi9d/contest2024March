package com.xi9d.contest_back.Security.MotoristSecurity;

import com.xi9d.contest_back.Model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoristRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String problemDef;
}
