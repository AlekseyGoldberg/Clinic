package com.example.clinic.controller;

import com.example.clinic.dto.ScheduleDto;
import com.example.clinic.service.ScheduleService;
import com.example.clinic.text.LoggingMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
@Log4j2
public class ScheduleController {
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto dto) throws ParseException, com.fasterxml.jackson.core.JsonProcessingException {
        log.info(LoggingMessage.CREATE_SCHEDULE_WITH_DTO, dto);
        return ResponseEntity
                .ok(scheduleService.createSchedule(dto));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<HttpStatus> cancelSchedule(@PathVariable Long id) throws com.fasterxml.jackson.core.JsonProcessingException {
        log.info(LoggingMessage.CANCEL_SCHEDULE_BY_ID, id);
        scheduleService.cancelSchedule(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping("/edit")
    public ResponseEntity<ScheduleDto> editSchedule(@RequestBody ScheduleDto dto) throws ParseException, com.fasterxml.jackson.core.JsonProcessingException {
        log.info(LoggingMessage.EDIT_SCHEDULE_WITH_DTO, dto);
        return ResponseEntity.ok(scheduleService.editSchedule(dto));
    }
}
