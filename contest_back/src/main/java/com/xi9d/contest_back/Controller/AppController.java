package com.xi9d.contest_back.Controller;

import com.xi9d.contest_back.DTOs.MechanicDTO;
import com.xi9d.contest_back.DTOs.MotoristDTO;
import com.xi9d.contest_back.Model.Location;
import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Model.Motorist;
import com.xi9d.contest_back.Repository.MechanicRepository;
import com.xi9d.contest_back.Repository.MotoristRepository;
import com.xi9d.contest_back.Service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/main")
public class AppController {
private final AppService appService;
private final MotoristRepository motoristRepository;
private final MechanicRepository mechanicRepository;

@Autowired
public AppController(AppService appService,
                         MotoristRepository motoristRepository,
                         MechanicRepository mechanicRepository) {
        this.appService = appService;
        this.motoristRepository = motoristRepository;
        this.mechanicRepository = mechanicRepository;
    }

    @GetMapping("/mechanics")
    public ResponseEntity<List<Mechanic>> getAllNearMechanics(
            @RequestParam("currentLocation") Location currentLocation){
        List<Mechanic> mechanics = appService.getMechanicByCurrentLocation(currentLocation);
        return ResponseEntity.ok(mechanics);
    }
    @GetMapping("/mechanics/all")
    public ResponseEntity<List<Mechanic>> getAllMechanics(){
    return ResponseEntity.ok(appService.getAllMechanics());
    }
    @GetMapping("/mechanic")
    public ResponseEntity<Mechanic> getMechanicById(@RequestParam("id")Long id){
        Mechanic mechanic = appService.getMechanicById(id);
        return ResponseEntity.ok(mechanic);
    }
    @PostMapping("/mechanic/add")
    public ResponseEntity<Mechanic> addMechanic(@RequestBody MechanicDTO mechanicDTO){
        Mechanic mechanic = appService.addMechanic(mechanicDTO);
        return ResponseEntity.ok(mechanic);
    }
    @PostMapping("/motorist/add")
    public ResponseEntity<Motorist> addMotorist(@RequestBody MotoristDTO motoristDTO){
        Motorist motorist = appService.addMotorist(motoristDTO);
        return ResponseEntity.ok(motorist);
    }
    @GetMapping("/distance")
    public ResponseEntity<Double> getDistance(@RequestParam("motorist_id")Long motorist_id,
                                              @RequestParam("mechanic_id") Long mechanic_id){
        Motorist motorist = new Motorist();       
        Mechanic mechanic = new Mechanic();
        
        Optional<Motorist> optionalMotorist = motoristRepository.findById(motorist_id);
        if (optionalMotorist.isPresent()){
            motorist= optionalMotorist.get();
            
        }
        Optional<Mechanic> optionalMechanic = mechanicRepository.findById(mechanic_id);
        if (optionalMechanic.isPresent()){
            mechanic = optionalMechanic.get();
        }
        Double distance = null;
        if (mechanic != null&& motorist!=null){
            distance = appService.calculateDistance(
                    motorist.getLocation().getLatitude(), 
                    motorist.getLocation().getLongitude(),
                    mechanic.getLocation().getLatitude(),
                    mechanic.getLocation().getLongitude());
                    
        }
       
        return ResponseEntity.ok(distance);
    }

}
