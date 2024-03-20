package com.xi9d.contest_back.Repository;

import com.xi9d.contest_back.Model.Motorist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotoristRepository extends JpaRepository<Motorist, Long> {
    boolean existsMotoristByEmail(String username);

    Optional<Motorist> findByEmail(String username);
}
