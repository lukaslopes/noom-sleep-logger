package com.noom.interview.fullstack.sleep.controller.dto;

import com.fasterxml.jackson.annotation.*;
import com.noom.interview.fullstack.sleep.entity.*;
import lombok.*;

import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSleepLogRequest {

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate sleepDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepStart;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime sleepEnd;
    private SleepQuality sleepQuality;
}
