package com.xi9d.contest_back.Service;

import com.xi9d.contest_back.DTOs.MechanicDTO;
import com.xi9d.contest_back.DTOs.MotoristDTO;
import com.xi9d.contest_back.Model.Location;
import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Model.Motorist;

import java.util.List;

public interface AppService {
    List<Mechanic> getMechanicByCurrentLocation(Location currentLocation);

    Mechanic addMechanic(MechanicDTO mechanicDTO);

    Motorist addMotorist(MotoristDTO motoristDTO);
    Double calculateDistance(double lat1, double lon1, double lat2, double lon2);

    Mechanic getMechanicById(Long id);

    List<Mechanic> getAllMechanics();
}
