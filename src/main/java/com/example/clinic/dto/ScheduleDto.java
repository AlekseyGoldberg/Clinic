package com.example.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduleDto {
    private Long id;
    private Long doctorId;
    private Long clientUserId;

    private String dateSchedule;
}
