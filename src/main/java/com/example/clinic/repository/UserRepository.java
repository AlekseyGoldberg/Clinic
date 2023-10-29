package com.example.clinic.repository;

import com.example.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);

    boolean existsUserById(Long id);

    User findByUsername(String username);
}
