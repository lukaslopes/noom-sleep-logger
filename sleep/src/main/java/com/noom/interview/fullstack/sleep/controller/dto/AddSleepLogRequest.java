package com.noom.interview.fullstack.sleep.controller.dto;

import com.fasterxml.jackson.annotation.*;
import com.noom.interview.fullstack.sleep.domain.*;
import lombok.*;

import javax.validation.constraints.*;
import java.time.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddSleepLogRequest {

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate sleepDate;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "sleepStart is required")
    private LocalTime sleepStart;

    @JsonFormat(pattern = "HH:mm")
    @NotNull(message = "sleepStart is required")
    private LocalTime sleepEnd;

    @NotNull(message = "sleepStart is required")
    private SleepQuality sleepQuality;
}
