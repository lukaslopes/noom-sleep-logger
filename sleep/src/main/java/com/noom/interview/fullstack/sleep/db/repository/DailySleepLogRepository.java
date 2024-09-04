package com.noom.interview.fullstack.sleep.db.repository;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Repository
public interface DailySleepLogRepository extends JpaRepository<DailySleepLogDataMapper, Long> {

    List<DailySleepLogDataMapper> findByUserIdAndSleepEndBetweenOrderBySleepEndDesc(Long userId, LocalDateTime start, LocalDateTime end);
}
