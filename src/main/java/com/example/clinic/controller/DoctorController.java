package com.example.clinic.controller;

import com.example.clinic.dto.DoctorDto;
import com.example.clinic.dto.ScheduleDto;
import com.example.clinic.message.LoggingMessage;
import com.example.clinic.service.DoctorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@AllArgsConstructor
@Log4j2
public class DoctorController {

    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody DoctorDto dto) {
        log.info(LoggingMessage.CREATE_DOCTOR, dto);
        return ResponseEntity
                .ok(doctorService.createDoctor(dto));
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<List<ScheduleDto>> getAllSchedulesDoctor(@PathVariable Long id) {
        log.info(LoggingMessage.GET_ALL_SCHEDULES_BY_DOCTOR_ID, id);
        return ResponseEntity.ok(doctorService.getSchedulesByDoctorId(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDoctor(@PathVariable Long id) {
        log.info(LoggingMessage.DELETE_DOCTOR_BY_DOCTOR_ID, id);
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
