package com.noom.interview.fullstack.sleep.controller.dto;

import com.fasterxml.jackson.annotation.*;
import com.noom.interview.fullstack.sleep.domain.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvgSleepLogResponse {

    private Long userId;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate sleepDateStart;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate sleepDateEnd;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepStart;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepEnd;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepTime;
    private Map<SleepQuality, Integer> sleepQualityCount;
}
