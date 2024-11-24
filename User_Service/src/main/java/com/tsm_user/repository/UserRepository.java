package com.tsm_user.repository;

import com.tsm_user.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Cacheable("users")
    @Transactional(readOnly = true)
    Optional<User> findByUsername(String username);
    @Cacheable("users")
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);
}
