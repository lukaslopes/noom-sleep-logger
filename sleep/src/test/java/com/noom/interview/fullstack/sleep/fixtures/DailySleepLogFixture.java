package com.noom.interview.fullstack.sleep.fixtures;

import com.noom.interview.fullstack.sleep.entity.*;

import java.time.*;

public class DailySleepLogFixture {
    public static DailySleepLog defaultValues() {
        return DailySleepLog.builder()
            .id(1L)
            .userId(1L)
            .sleepStart(LocalDateTime.of(2024, 1, 1, 10, 0))
            .sleepEnd(LocalDateTime.of(2024, 1, 2, 8, 0))
            .sleepDuration(600L)
            .sleepQuality(SleepQuality.GOOD)
            .build();
    }

    public static DailySleepLog defaultValuesNoId() {
        return DailySleepLog.builder()
            .userId(1L)
            .sleepStart(LocalDateTime.of(2024, 1, 1, 10, 0))
            .sleepEnd(LocalDateTime.of(2024, 1, 2, 8, 0))
            .sleepDuration(600L)
            .sleepQuality(SleepQuality.GOOD)
            .build();
    }
}
