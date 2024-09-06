package com.noom.interview.fullstack.sleep.fixtures;

import com.noom.interview.fullstack.sleep.db.entity.*;
import com.noom.interview.fullstack.sleep.domain.*;

import java.time.*;

public class DailySleepLogEntityFixture {

    public static DailySleepLogEntity defaultValues() {
        DailySleepLog values = DailySleepLogFixture.defaultValues();
        return DailySleepLogEntity.builder()
            .id(values.getId())
            .sleepStart(values.getSleepStart())
            .sleepEnd(values.getSleepEnd())
            .userId(values.getUserId())
            .sleepQuality(values.getSleepQuality())
            .sleepDuration(600L)
            .createdAt(Instant.now())
            .modifiedAt(Instant.now())
            .build();
    }

    public static DailySleepLogEntity insertValues() {
        DailySleepLog values = DailySleepLogFixture.defaultValuesNoId();
        return DailySleepLogEntity.builder()
            .sleepStart(values.getSleepStart())
            .sleepEnd(values.getSleepEnd())
            .userId(values.getUserId())
            .sleepQuality(values.getSleepQuality())
            .sleepDuration(600L)
            .build();
    }
}
