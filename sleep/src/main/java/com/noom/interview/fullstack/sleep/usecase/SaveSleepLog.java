package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.domain.*;
import com.noom.interview.fullstack.sleep.exception.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Service
public class SaveSleepLog {

    private final DailySleepLogProvider provider;


    @Autowired
    public SaveSleepLog(DailySleepLogProvider provider) {
        this.provider = provider;
    }

    public DailySleepLog save(DailySleepLog dailySleepLog) {

        checkOverlaps(dailySleepLog);

        return provider.save(dailySleepLog);
    }

    private void checkOverlaps(DailySleepLog dailySleepLog) {
        List<DailySleepLog> conflictLog = provider.findByUserIdAndInterval(dailySleepLog.getUserId(),
            dailySleepLog.getSleepStart().with(ChronoField.NANO_OF_DAY, LocalTime.MIN.toNanoOfDay()),
            dailySleepLog.getSleepEnd().with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay()));

        LocalDateTime start1 = dailySleepLog.getSleepStart();
        LocalDateTime end1 = dailySleepLog.getSleepEnd();

        for(DailySleepLog log : conflictLog) {
            LocalDateTime start2 = log.getSleepStart();
            LocalDateTime end2 = log.getSleepEnd();
            if (!(end1.isBefore(start2) || start1.isAfter(end2)) || (start1.isAfter(start2) && end1.isBefore(end2))) {
                throw new SleepLogAlreadyExistsException("You already have a log between " + log.getSleepStart() + " and " + log.getSleepEnd());
            }
        }
    }
}
