package com.noom.interview.fullstack.sleep.db;

import com.noom.interview.fullstack.sleep.db.entity.*;
import com.noom.interview.fullstack.sleep.db.repository.*;
import com.noom.interview.fullstack.sleep.domain.*;
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
        DailySleepLogEntity data = repository.save(toDataMapper(dailySleepLog));

        dailySleepLog.setId(data.getId());
        return dailySleepLog;
    }

    @Override
    public List<DailySleepLog> findByUserIdAndInterval(Long userId, LocalDateTime start, LocalDateTime end) {
        List<DailySleepLogEntity> result = repository.findByUserIdAndSleepEndBetweenOrderBySleepEndDesc(userId,
            start , end);

        return result.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private static DailySleepLogEntity toDataMapper(DailySleepLog dailySleepLog) {
        return DailySleepLogEntity.builder()
            .userId(dailySleepLog.getUserId())
            .sleepStart(dailySleepLog.getSleepStart())
            .sleepEnd(dailySleepLog.getSleepEnd())
            .sleepDuration(dailySleepLog.getSleepDuration())
            .sleepQuality(dailySleepLog.getSleepQuality())
            .build();
    }

    private DailySleepLog toEntity(DailySleepLogEntity dailySleepLogEntity) {
        return DailySleepLog.builder()
            .id(dailySleepLogEntity.getId())
            .userId(dailySleepLogEntity.getUserId())
            .sleepDuration(dailySleepLogEntity.getSleepDuration())
            .sleepStart(dailySleepLogEntity.getSleepStart())
            .sleepEnd(dailySleepLogEntity.getSleepEnd())
            .sleepQuality(dailySleepLogEntity.getSleepQuality())
            .build();
    }
}
