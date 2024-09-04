package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class SaveSleepLog {

    private final DailySleepLogProvider provider;

    @Autowired
    public SaveSleepLog(DailySleepLogProvider provider) {
        this.provider = provider;
    }

    public DailySleepLog save(DailySleepLog dailySleepLog) {
        return provider.save(dailySleepLog);
    }
}
