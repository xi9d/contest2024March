package com.xi9d.contest_back.Security;

import com.xi9d.contest_back.Model.Mechanic;
import com.xi9d.contest_back.Model.Motorist;
import com.xi9d.contest_back.Repository.MechanicRepository;
import com.xi9d.contest_back.Repository.MotoristRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MechanicRepository mechanicRepository;
    private final MotoristRepository motoristRepository;

    public CustomUserDetailsService(MechanicRepository mechanicRepository, MotoristRepository motoristRepository) {
        this.mechanicRepository = mechanicRepository;
        this.motoristRepository = motoristRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Mechanic> optionalMechanic = mechanicRepository.findByEmail(email);
        if (optionalMechanic.isPresent()) {

            return (UserDetails) optionalMechanic.get();
        }
        Optional<Motorist> optionalMotorist= motoristRepository.findByEmail(email);
        if (optionalMotorist.isPresent()) {

            return (UserDetails)optionalMotorist.get();
        }
        throw new UsernameNotFoundException("Could not find user with email " + email);
    }
}
