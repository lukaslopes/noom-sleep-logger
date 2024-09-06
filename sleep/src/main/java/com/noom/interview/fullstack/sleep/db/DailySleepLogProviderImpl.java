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
        DailySleepLogEntity data = repository.save(toEntity(dailySleepLog));

        dailySleepLog.setId(data.getId());
        return dailySleepLog;
    }

    @Override
    public List<DailySleepLog> findByUserIdAndInterval(Long userId, LocalDateTime start, LocalDateTime end) {
        List<DailySleepLogEntity> result = repository.findByUserIdAndSleepEndBetweenOrderBySleepEndDesc(userId,
            start , end);

        return result.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private static DailySleepLogEntity toEntity(DailySleepLog dailySleepLog) {
        long timeInBedInMinutes = Duration.between(dailySleepLog.getSleepStart(), dailySleepLog.getSleepEnd()).toMinutes();
        return DailySleepLogEntity.builder()
            .userId(dailySleepLog.getUserId())
            .sleepStart(dailySleepLog.getSleepStart())
            .sleepEnd(dailySleepLog.getSleepEnd())
            .sleepDuration(timeInBedInMinutes)
            .sleepQuality(dailySleepLog.getSleepQuality())
            .build();
    }

    private DailySleepLog toDomain(DailySleepLogEntity dailySleepLogEntity) {
        return DailySleepLog.builder()
            .id(dailySleepLogEntity.getId())
            .userId(dailySleepLogEntity.getUserId())
            .sleepStart(dailySleepLogEntity.getSleepStart())
            .sleepEnd(dailySleepLogEntity.getSleepEnd())
            .sleepQuality(dailySleepLogEntity.getSleepQuality())
            .build();
    }
}
