package com.xi9d.contest_back.Service;

import com.xi9d.contest_back.DTOs.MechanicDTO;
import com.xi9d.contest_back.DTOs.MotoristDTO;
import com.xi9d.contest_back.Model.Location;
import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Model.Motorist;
import com.xi9d.contest_back.Repository.MechanicRepository;
import com.xi9d.contest_back.Repository.MotoristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppServiceImplementation implements AppService{
    private final MechanicRepository mechanicRepository;
    private final MotoristRepository motoristRepository;

    @Autowired
    public AppServiceImplementation(MechanicRepository mechanicRepository, MotoristRepository motoristRepository) {
        this.mechanicRepository = mechanicRepository;
        this.motoristRepository = motoristRepository;
    }

    @Override
    public List<Mechanic> getMechanicByCurrentLocation(Location currentLocation) {
        List<Mechanic> mechanics = mechanicRepository.findMechanicCloseByCurrentLocation(currentLocation.getLatitude(),currentLocation.getLongitude());
        return mechanics;
    }

    @Override
    public Mechanic addMechanic(MechanicDTO mechanicDTO) {
       Mechanic mechanic = new Mechanic();
       if (mechanicDTO.getName()!= null){
           mechanic.setFullName(mechanicDTO.getName());
       }
       if (mechanicDTO.getEmail()!=null){
           mechanic.setEmail(mechanicDTO.getEmail());
       }
       if (mechanicDTO.getTelephone()!= null){
           mechanic.setTelephone(mechanicDTO.getTelephone());
       }
       if (mechanicDTO.getLocation()!= null){
           mechanic.setLocation(mechanicDTO.getLocation());
       }
       if (mechanicDTO.getCompanyName()!=null){
           mechanic.setCompanyName(mechanicDTO.getCompanyName());
       }
       mechanicRepository.save(mechanic);
        return mechanic;
    }

    @Override
    public Motorist addMotorist(MotoristDTO motoristDTO) {
        Motorist motorist = new Motorist();
        if (motoristDTO.getName()!=null){
            motorist.setFullName(motoristDTO.getName());
        }
        if (motoristDTO.getTelephone()!=null){
            motorist.setTelephone(motoristDTO.getTelephone());
        }
        if (motoristDTO.getEmail()!=null){
            motorist.setEmail(motoristDTO.getEmail());
        }
        if (motoristDTO.getLocation()!=null){
            motorist.setLocation(motoristDTO.getLocation());
        }
        if (motoristDTO.getProblemDef()!=null){
            motorist.setProblemDef(motoristDTO.getProblemDef());
        }
        motoristRepository.save(motorist);

        return motorist;
    }
    public Double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        return dist;
    }

    @Override
    public Mechanic getMechanicById(Long id) {
        Optional<Mechanic> optionalMechanic = mechanicRepository.findById(id);
        return optionalMechanic.orElse(null);
    }

    @Override
    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }
}
