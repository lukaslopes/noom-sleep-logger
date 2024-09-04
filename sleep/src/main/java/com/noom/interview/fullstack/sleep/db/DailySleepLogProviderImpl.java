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
@NoArgsConstructor
public class DailySleepLogProviderImpl implements DailySleepLogProvider {

    private DailySleepLogRepository repository;

    @Override
    public DailySleepLog save(DailySleepLog dailySleepLog) {
        DailySleepLogDataMapper data = repository.save(toDataMapper(dailySleepLog));

        dailySleepLog.setId(data.getId());
        return dailySleepLog;
    }

    @Override
    public List<DailySleepLog> findByUserIdAndInterval(Long userId, LocalDateTime start, LocalDateTime end) {
        List<DailySleepLogDataMapper> result = repository.findByUserIdAndSleepEndBetween(userId,
            start.toInstant(ZoneOffset.UTC),
            end.toInstant(ZoneOffset.UTC));

        return result.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private static DailySleepLogDataMapper toDataMapper(DailySleepLog dailySleepLog) {
        return DailySleepLogDataMapper.builder()
            .userId(dailySleepLog.getUserId())
            .sleepStart(dailySleepLog.getSleepStart().toInstant(ZoneOffset.UTC))
            .sleepEnd(dailySleepLog.getSleepEnd().toInstant(ZoneOffset.UTC))
            .sleepDuration(dailySleepLog.getSleepDuration())
            .sleepQuality(dailySleepLog.getSleepQuality())
            .build();
    }

    private DailySleepLog toEntity(DailySleepLogDataMapper dailySleepLogDataMapper) {
        return DailySleepLog.builder()
            .id(dailySleepLogDataMapper.getId())
            .userId(dailySleepLogDataMapper.getUserId())
            .sleepDuration(dailySleepLogDataMapper.getSleepDuration())
            .sleepStart(LocalDateTime.ofInstant(dailySleepLogDataMapper.getSleepStart(), ZoneOffset.UTC))
            .sleepEnd(LocalDateTime.ofInstant(dailySleepLogDataMapper.getSleepEnd(), ZoneOffset.UTC))
            .sleepQuality(dailySleepLogDataMapper.getSleepQuality())
            .build();
    }
}
