package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;

@Service
public class GetLastMonth {

    private final DailySleepLogProvider provider;

    @Autowired
    public GetLastMonth(DailySleepLogProvider provider) {
        this.provider = provider;
    }

    public AvgSleepLog execute(Long userId) {
        List<DailySleepLog> result = provider.findByUserIdAndInterval(userId, LocalDate.now().atStartOfDay().minusMonths(1), LocalDateTime.now());

        if(Objects.isNull(result) || result.isEmpty()) {
            return null;
        }

        AtomicInteger totalSleepStart = new AtomicInteger();
        AtomicInteger totalSleepEnd = new AtomicInteger();
        Map<SleepQuality, Integer> sleepQualityCount = new HashMap<>();
        sleepQualityCount.put(SleepQuality.BAD, 0);
        sleepQualityCount.put(SleepQuality.OK, 0);
        sleepQualityCount.put(SleepQuality.GOOD, 0);

        result.forEach(log -> {
            totalSleepStart.addAndGet(log.getSleepStart().toLocalTime().toSecondOfDay());
            totalSleepEnd.addAndGet(log.getSleepEnd().toLocalTime().toSecondOfDay());
            sleepQualityCount.put(log.getSleepQuality(), sleepQualityCount.get(log.getSleepQuality()) + 1);
        });

        LocalTime avgSleepStart = LocalTime.ofSecondOfDay(totalSleepStart.get()/result.size());
        LocalTime avgSleepEnd = LocalTime.ofSecondOfDay(totalSleepEnd.get()/result.size());

        return AvgSleepLog.builder()
            .userId(userId)
            .sleepStart(avgSleepStart)
            .sleepEnd(avgSleepEnd)
            .sleepQualityCount(sleepQualityCount)
            .startDate(LocalDate.now().minusMonths(1))
            .endDate(LocalDate.now())
            .build();
    }
}
