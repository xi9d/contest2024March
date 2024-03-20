package com.xi9d.contest_back.DTOs;

import com.xi9d.contest_back.Model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoristDTO {
    private String name;
    private String email;
    private String telephone;
    private String problemDef;
    private Location location;
}
