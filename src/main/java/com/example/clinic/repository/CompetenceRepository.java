package com.example.clinic.repository;

import com.example.clinic.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    boolean existsByNameCompetence(String nameCompetence);

    Competence findByNameCompetence(String nameCompetence);
}
