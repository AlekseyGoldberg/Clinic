package com.example.clinic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusProducerDto {
    private String status;
    private Long clientUserId;
    private Long doctorId;
    private Date dateCreateSchedule;
}
