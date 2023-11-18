package com.example.clinic.service;

import com.example.clinic.dto.ScheduleDto;
import com.example.clinic.dto.StatusProducerDto;
import com.example.clinic.entity.Doctor;
import com.example.clinic.entity.Schedule;
import com.example.clinic.entity.User;
import com.example.clinic.exception.CommonBaseException;
import com.example.clinic.handler.Handler;
import com.example.clinic.mapper.ScheduleMapper;
import com.example.clinic.producer.StatusProducer;
import com.example.clinic.repository.ScheduleRepository;
import com.example.clinic.text.ExceptionMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import static com.example.clinic.text.LoggingMessage.SEND_CANCELED_SCHEDULE_IN_KAFKA_WITH_DTO;
import static com.example.clinic.text.LoggingMessage.SEND_STATUS_CREATED_SCHEDULE_IN_KAFKA_WITH_DTO;
import static com.example.clinic.text.StatusText.ACCEPTED_STATUS;
import static com.example.clinic.text.StatusText.CANCELLED_STATUS;

@Service
@AllArgsConstructor
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private DoctorService doctorService;
    private UserService userService;
    private ScheduleMapper scheduleMapper;
    private StatusProducer statusProducer;


    public ScheduleDto createSchedule(ScheduleDto dto) throws ParseException, com.fasterxml.jackson.core.JsonProcessingException {
        Doctor doctor = doctorService.getById(dto.getDoctorId());
        User client = userService.getUserEntityById(dto.getClientUserId());

        Schedule schedule = scheduleRepository.save(Schedule.builder()
                .doctor(doctor)
                .client(client)
                .date(Handler.parseDate(dto.getDateSchedule()))
                .build());

        StatusProducerDto statusProducerDto = scheduleMapper.toStatusProducer(schedule, ACCEPTED_STATUS);
        statusProducer.sendStatus(statusProducerDto, SEND_STATUS_CREATED_SCHEDULE_IN_KAFKA_WITH_DTO);
        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    public void cancelSchedule(Long id) throws com.fasterxml.jackson.core.JsonProcessingException {
        existScheduleById(id);
        Schedule schedule = scheduleRepository.getById(id);
        StatusProducerDto statusProducerDto = scheduleMapper.toStatusProducer(schedule, CANCELLED_STATUS);
        scheduleRepository.deleteById(id);
        statusProducer.sendStatus(statusProducerDto, SEND_CANCELED_SCHEDULE_IN_KAFKA_WITH_DTO);
    }


    public ScheduleDto editSchedule(ScheduleDto dto) throws ParseException, com.fasterxml.jackson.core.JsonProcessingException {
        cancelSchedule(dto.getId());
        return createSchedule(dto);
    }


    public void existScheduleById(Long id) {
        if (!scheduleRepository.existsById(id))
            throw new CommonBaseException(String.format(ExceptionMessage.SCHEDULE_NOT_EXIST_WITH_ID, id));
    }
}
