package com.noom.interview.fullstack.sleep.domain;

import lombok.*;

import java.time.*;
import java.util.*;

@Data
@Builder
public class AvgSleepLog {
    private Long userId;
    private LocalTime sleepStart;
    private LocalTime sleepEnd;
    private Integer sleepDuration;
    private Map<SleepQuality, Integer> sleepQualityCount;
    private LocalDate startDate;
    private LocalDate endDate;
}
