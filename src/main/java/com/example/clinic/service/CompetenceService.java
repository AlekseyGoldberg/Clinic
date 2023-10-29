package com.example.clinic.service;

import com.example.clinic.entity.Competence;
import com.example.clinic.exception.CommonBaseException;
import com.example.clinic.message.ExceptionMessage;
import com.example.clinic.repository.CompetenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompetenceService {
    private CompetenceRepository competenceRepository;

    public Competence getCompetenceByName(String competenceName) {
        existsByNameCompetence(competenceName);
        return competenceRepository.findByNameCompetence(competenceName);
    }


    public void existsByNameCompetence(String competenceName) {
        if (!competenceRepository.existsByNameCompetence(competenceName))
            throw new CommonBaseException(String.format(ExceptionMessage.COMPETENCE_NOT_EXIST, competenceName));
    }
}
