package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
public class SaveSleepLog {

    private final DailySleepLogProvider provider;


    @Autowired
    public SaveSleepLog(DailySleepLogProvider provider) {
        this.provider = provider;
    }

    public DailySleepLog save(DailySleepLog dailySleepLog) {
        long timeInBedInMinutes = Duration.between(dailySleepLog.getSleepStart(), dailySleepLog.getSleepEnd()).toMinutes();
        dailySleepLog.setSleepDuration(timeInBedInMinutes);

        List<DailySleepLog> conflictLog = provider.findByUserIdAndInterval(dailySleepLog.getUserId(),
            dailySleepLog.getSleepStart(),
            dailySleepLog.getSleepEnd());

        if(!conflictLog.isEmpty()) {
            throw new RuntimeException("You already have a log between " + dailySleepLog.getSleepStart() + " and " + dailySleepLog.getSleepEnd());
        }

        return provider.save(dailySleepLog);
    }
}
