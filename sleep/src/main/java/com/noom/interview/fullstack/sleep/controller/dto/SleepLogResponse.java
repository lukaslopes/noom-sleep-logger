package com.noom.interview.fullstack.sleep.controller.dto;

import com.fasterxml.jackson.annotation.*;
import com.noom.interview.fullstack.sleep.domain.*;
import lombok.*;

import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SleepLogResponse {

    private Long id;
    private Long userId;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate sleepDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepStart;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepEnd;
    private Integer timeInBedInMinutes;
    private SleepQuality sleepQuality;

}
