package com.noom.interview.fullstack.sleep.db;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import com.noom.interview.fullstack.sleep.db.repository.*;
import com.noom.interview.fullstack.sleep.entity.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
@AllArgsConstructor
public class DailySleepLogProviderImpl implements DailySleepLogProvider {

    private final DailySleepLogRepository repository;

    @Override
    public DailySleepLog save(DailySleepLog dailySleepLog) {
        DailySleepLogDataMapper data = repository.save(toDataMapper(dailySleepLog));

        dailySleepLog.setId(data.getId());
        return dailySleepLog;
    }

    @Override
    public List<DailySleepLog> findByUserIdAndInterval(Long userId, LocalDateTime start, LocalDateTime end) {
        List<DailySleepLogDataMapper> result = repository.findByUserIdAndSleepEndBetweenOrderBySleepEndDesc(userId,
            start , end);

        return result.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private static DailySleepLogDataMapper toDataMapper(DailySleepLog dailySleepLog) {
        return DailySleepLogDataMapper.builder()
            .userId(dailySleepLog.getUserId())
            .sleepStart(dailySleepLog.getSleepStart())
            .sleepEnd(dailySleepLog.getSleepEnd())
            .sleepDuration(dailySleepLog.getSleepDuration())
            .sleepQuality(dailySleepLog.getSleepQuality())
            .build();
    }

    private DailySleepLog toEntity(DailySleepLogDataMapper dailySleepLogDataMapper) {
        return DailySleepLog.builder()
            .id(dailySleepLogDataMapper.getId())
            .userId(dailySleepLogDataMapper.getUserId())
            .sleepDuration(dailySleepLogDataMapper.getSleepDuration())
            .sleepStart(dailySleepLogDataMapper.getSleepStart())
            .sleepEnd(dailySleepLogDataMapper.getSleepEnd())
            .sleepQuality(dailySleepLogDataMapper.getSleepQuality())
            .build();
    }
}
