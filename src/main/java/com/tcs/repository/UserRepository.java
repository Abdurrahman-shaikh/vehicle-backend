package com.tcs.repository;

import com.tcs.entity.Underwriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Underwriter, Long> {
    Underwriter findByName(String username);
}
