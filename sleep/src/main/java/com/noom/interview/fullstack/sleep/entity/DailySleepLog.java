package com.noom.interview.fullstack.sleep.entity;

import lombok.*;

import java.time.*;

@Data
@Builder
public class DailySleepLog {
    private Long id;
    private Long userId;
    private LocalDateTime sleepStart;
    private LocalDateTime sleepEnd;
    private Long sleepDuration;
    private SleepQuality sleepQuality;
}
