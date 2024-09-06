package com.noom.interview.fullstack.sleep.fixtures;

import com.noom.interview.fullstack.sleep.domain.*;

import java.time.*;
import java.util.*;

public class AvgSleepLogFixture {
    public static AvgSleepLog defaultValues() {
        Map<SleepQuality, Integer> qualityCount = new HashMap<>();
        qualityCount.put(SleepQuality.BAD, 3);
        qualityCount.put(SleepQuality.OK, 2);
        qualityCount.put(SleepQuality.GOOD, 1);

        return AvgSleepLog.builder()
            .userId(1L)
            .sleepStart(LocalTime.of(22, 0))
            .sleepEnd(LocalTime.of(8, 0))
            .sleepDuration(600)
            .sleepQualityCount(qualityCount)
            .startDate(LocalDate.of(2024, 1, 1))
            .endDate(LocalDate.of(2024, 1, 2))
            .build();
    }
}
