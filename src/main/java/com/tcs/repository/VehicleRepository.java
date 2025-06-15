package com.tcs.repository;

import com.tcs.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Vehicle} entities.
 * <p>
 * Provides CRUD operations and custom queries related to vehicle records.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * Retrieves all vehicles associated with a specific underwriter username.
     *
     * @param username the login username of the underwriter
     * @return a list of {@link Vehicle} registered by the given underwriter
     */
    List<Vehicle> findByUnderwriterLoginUsername(String username);
}
