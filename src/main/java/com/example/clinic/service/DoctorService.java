package com.example.clinic.service;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.ScheduleDto;
import com.example.clinic.entity.Competence;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.User;
import com.example.clinic.exception.CommonBaseException;
import com.example.clinic.mapper.DoctorMapper;
import com.example.clinic.mapper.ScheduleMapper;
import com.example.clinic.text.ExceptionMessage;
import com.example.clinic.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorService {
    private DoctorRepository doctorRepository;
    private UserService userService;
    private CompetenceService competenceService;

    private DoctorMapper doctorMapper;

    private ScheduleMapper scheduleMapper;


    public Doctor getById(Long id) {
        existDoctorById(id);
        return doctorRepository.getById(id);
    }

    public DoctorDto createDoctor(DoctorDto dto) {
        User user = userService.getUserEntityById(dto.getUserId());
        Competence competence = competenceService.getCompetenceByName(dto.getCompetenceName());
        Doctor doctor = Doctor.builder()
                .user(user)
                .competence(competence)
                .build();
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }


    public List<ScheduleDto> getSchedulesByDoctorId(Long id) {
        existDoctorById(id);
        Doctor doctor = doctorRepository.getById(id);
        return doctor.getSchedules()
                .stream()
                .map(schedule -> scheduleMapper.toDto(schedule))
                .collect(Collectors.toList());
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }


    public void existDoctorById(Long id) {
        if (!doctorRepository.existsById(id))
            throw new CommonBaseException(String.format(ExceptionMessage.DOCTOR_NOT_EXIST_WITH_ID, id));
    }
}
