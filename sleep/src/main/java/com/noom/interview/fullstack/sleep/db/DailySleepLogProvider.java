package com.noom.interview.fullstack.sleep.db;

import com.noom.interview.fullstack.sleep.entity.*;

import java.time.*;
import java.util.*;

public interface DailySleepLogProvider {

    DailySleepLog save(DailySleepLog dailySleepLog);

    List<DailySleepLog> findByUserIdAndInterval(Long userId, LocalDateTime start, LocalDateTime end);
}
