package com.xi9d.contest_back.Repository;

import com.xi9d.contest_back.Model.Location;
import com.xi9d.contest_back.Model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
    @Query("SELECT m FROM Mechanic m WHERE FUNCTION('ST_Distance_Sphere', POINT(:latitude, :longitude), POINT(m.location.latitude, m.location.longitude)) < :distance")
    List<Mechanic> findMechanicCloseByCurrentLocation(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );

    boolean existsMechanicByEmail(String username);

    Optional<Mechanic> findByEmail(String username);
}
