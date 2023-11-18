package com.example.clinic.mapper;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DoctorMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "competence.nameCompetence", target = "competenceName")
    DoctorDto toDto(Doctor doctor);
}
