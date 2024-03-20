package com.xi9d.contest_back.Config;

import com.xi9d.contest_back.Model.Location;
import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Repository.MechanicRepository;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalRegisterRequest;
import com.xi9d.contest_back.Security.MechanicalSecurity.MechanicalSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SeedData implements CommandLineRunner {


    private final MechanicalSecurityService mechanicalSecurityService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public SeedData(MechanicalSecurityService mechanicalSecurityService) {
        this.mechanicalSecurityService = mechanicalSecurityService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Center location for generating mechanics
        Location centerLocation = new Location(-1.0529969917109456, 37.09467882518596);

        int numberOfMechanics = 20;

        for (int i = 0; i < numberOfMechanics; i++) {
            Location randomLocation = generateRandomLocation(centerLocation.getLatitude(), centerLocation.getLongitude());
            MechanicalRegisterRequest mechanic = new MechanicalRegisterRequest();
            mechanic.setName("Mechanic " + (i + 1));
            mechanic.setEmail("mechanic" + (i + 1) + "@example.com");
            mechanic.setTelephone("+123456789" + i);
            mechanic.setCompanyName("Company " + (i + 1));
            mechanic.setPassword(passwordEncoder.encode("password"));

            mechanicalSecurityService.register(mechanic);
        }
    }

    private Location generateRandomLocation(double centerLat, double centerLng) {
        double radiusInKm = 20;
        double radiusInDeg = radiusInKm / 111;
        double randomLat = centerLat + (Math.random() * 2 - 1) * radiusInDeg;
        double randomLng = centerLng + (Math.random() * 2 - 1) * radiusInDeg;

        return new Location(randomLat, randomLng);
    }
}
