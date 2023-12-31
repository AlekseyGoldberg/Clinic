package com.example.clinic.mapper;

import com.example.clinic.dto.ScheduleDto;
import com.example.clinic.dto.StatusProducerDto;
import com.example.clinic.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ScheduleMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "client.id", target = "clientUserId")
    @Mapping(source = "date", target = "dateSchedule")
    ScheduleDto toDto(Schedule schedule);

    @Mapping(source = "schedule.client.id", target = "clientUserId")
    @Mapping(source = "schedule.doctor.id", target = "doctorId")
    @Mapping(source = "schedule.date", target = "dateCreateSchedule")
    @Mapping(source = "status", target = "status")
    StatusProducerDto toStatusProducer(Schedule schedule, String status);
}
