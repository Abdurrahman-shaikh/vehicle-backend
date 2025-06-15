package com.tcs.repository;

import com.tcs.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link Login} entities.
 * <p>
 * Provides basic CRUD operations and custom query method to find a user by username.
 */
public interface LoginRepository extends JpaRepository<Login, Long> {

    /**
     * Finds a {@link Login} entity by its username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the found Login, or empty if not found
     */
    Optional<Login> findByUsername(String username);
}
