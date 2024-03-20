package com.xi9d.contest_back.Security.MechanicalSecurity;

import com.xi9d.contest_back.Model.Location;
import com.xi9d.contest_back.Model.Mechanic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MechanicalRegisterRequest{
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String companyName;
}
