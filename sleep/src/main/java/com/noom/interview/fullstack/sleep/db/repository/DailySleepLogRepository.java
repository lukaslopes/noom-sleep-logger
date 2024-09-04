package com.noom.interview.fullstack.sleep.db.repository;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface DailySleepLogRepository extends JpaRepository<DailySleepLogDataMapper, Long> {
}
