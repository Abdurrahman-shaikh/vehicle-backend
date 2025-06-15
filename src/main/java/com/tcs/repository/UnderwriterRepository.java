package com.tcs.repository;

import com.tcs.entity.Underwriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Underwriter} entities.
 * <p>
 * Provides CRUD operations and custom query methods for finding underwriters.
 */
@Repository
public interface UnderwriterRepository extends JpaRepository<Underwriter, Long> {

    /**
     * Finds an underwriter entity by their name (used as username).
     *
     * @param username the underwriter's name or login username
     * @return the matching {@link Underwriter}, or {@code null} if none found
     */
    Underwriter findByName(String username);
}
