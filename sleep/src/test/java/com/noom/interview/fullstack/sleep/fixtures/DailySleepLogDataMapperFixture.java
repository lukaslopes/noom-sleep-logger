package com.noom.interview.fullstack.sleep.fixtures;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import com.noom.interview.fullstack.sleep.entity.*;

import java.time.*;

public class DailySleepLogDataMapperFixture {

    public static DailySleepLogDataMapper defaultValues() {
        DailySleepLog values = DailySleepLogFixture.defaultValues();
        return DailySleepLogDataMapper.builder()
            .id(values.getId())
            .sleepStart(values.getSleepStart().toInstant(ZoneOffset.UTC))
            .sleepEnd(values.getSleepEnd().toInstant(ZoneOffset.UTC))
            .userId(values.getUserId())
            .sleepQuality(values.getSleepQuality())
            .sleepDuration(values.getSleepDuration())
            .createdAt(Instant.now())
            .modifiedAt(Instant.now())
            .build();
    }

    public static DailySleepLogDataMapper insertValues() {
        DailySleepLog values = DailySleepLogFixture.defaultValuesNoId();
        return DailySleepLogDataMapper.builder()
            .sleepStart(values.getSleepStart().toInstant(ZoneOffset.UTC))
            .sleepEnd(values.getSleepEnd().toInstant(ZoneOffset.UTC))
            .userId(values.getUserId())
            .sleepQuality(values.getSleepQuality())
            .sleepDuration(values.getSleepDuration())
            .build();
    }
}
