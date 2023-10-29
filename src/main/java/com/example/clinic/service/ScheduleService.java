package com.example.clinic.service;

import com.example.clinic.dto.ScheduleDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Schedule;
import com.example.clinic.entity.User;
import com.example.clinic.exception.CommonBaseException;
import com.example.clinic.handler.Handler;
import com.example.clinic.mapper.ScheduleMapper;
import com.example.clinic.message.ExceptionMessage;
import com.example.clinic.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@AllArgsConstructor
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private DoctorService doctorService;
    private UserService userService;
    private ScheduleMapper scheduleMapper;


    public ScheduleDto createSchedule(ScheduleDto dto) throws ParseException {
        Doctor doctor = doctorService.getById(dto.getDoctorId());
        User client = userService.getUserEntityById(dto.getClientUserId());
        Schedule schedule = Schedule.builder()
                .doctor(doctor)
                .client(client)
                .date(Handler.parseDate(dto.getDateSchedule()))
                .build();
        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    public void cancelSchedule(Long id) {
        existScheduleById(id);
        scheduleRepository.deleteById(id);
    }

    public ScheduleDto editSchedule(ScheduleDto dto) throws ParseException {
        existScheduleById(dto.getId());
        Schedule schedule = scheduleRepository.getById(dto.getId());
        schedule.setDoctor(doctorService.getById(dto.getDoctorId()));
        schedule.setDate(Handler.parseDate(dto.getDateSchedule()));
        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }


    public void existScheduleById(Long id) {
        if (!scheduleRepository.existsById(id))
            throw new CommonBaseException(String.format(ExceptionMessage.SCHEDULE_NOT_EXIST_WITH_ID, id));
    }
}
