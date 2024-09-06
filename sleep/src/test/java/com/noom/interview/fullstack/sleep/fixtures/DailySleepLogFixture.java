package com.noom.interview.fullstack.sleep.fixtures;

import com.noom.interview.fullstack.sleep.domain.*;

import java.time.*;
import java.util.*;

public class DailySleepLogFixture {
    public static DailySleepLog defaultValues() {
        return DailySleepLog.builder()
            .id(1L)
            .userId(1L)
            .sleepStart(LocalDateTime.of(2024, 1, 1, 22, 0))
            .sleepEnd(LocalDateTime.of(2024, 1, 2, 8, 0))
            .sleepQuality(SleepQuality.GOOD)
            .build();
    }

    public static DailySleepLog defaultValuesNoId() {
        return DailySleepLog.builder()
            .userId(1L)
            .sleepStart(LocalDateTime.of(2024, 1, 1, 22, 0))
            .sleepEnd(LocalDateTime.of(2024, 1, 2, 8, 0))
            .sleepQuality(SleepQuality.GOOD)
            .build();
    }

    public static List<DailySleepLog> list() {
        return List.of(defaultValues(),
            DailySleepLog.builder()
                .id(2L)
                .userId(1L)
                .sleepStart(LocalDateTime.of(2024, 1, 2, 23, 0))
                .sleepEnd(LocalDateTime.of(2024, 1, 3, 6, 0))
                .sleepQuality(SleepQuality.BAD)
                .build(),
            DailySleepLog.builder()
                .id(3L)
                .userId(1L)
                .sleepStart(LocalDateTime.of(2024, 1, 3, 22, 40))
                .sleepEnd(LocalDateTime.of(2024, 1, 4, 6, 40))
                .sleepQuality(SleepQuality.GOOD)
                .build());
    }
}
