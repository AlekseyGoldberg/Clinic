package com.example.clinic.repository;

import com.example.clinic.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRole(String role);

    Role findByRole(String role);
}
