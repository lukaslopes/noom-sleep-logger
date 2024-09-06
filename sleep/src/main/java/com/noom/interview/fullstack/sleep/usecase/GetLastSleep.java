package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;

@Service
public class GetLastSleep {

    private final DailySleepLogProvider provider;

    @Autowired
    public GetLastSleep(DailySleepLogProvider provider) {
        this.provider = provider;
    }

    public DailySleepLog execute(Long userId) {
        return provider
            .findByUserIdAndInterval(userId, LocalDate.now().atStartOfDay(), LocalDateTime.now())
            .stream()
            .findFirst()
            .orElse(null);
    }
}
