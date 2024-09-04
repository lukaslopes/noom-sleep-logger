package com.noom.interview.fullstack.sleep.db.repository;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import com.noom.interview.fullstack.sleep.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.*;

import java.time.*;
import java.util.*;

@ActiveProfiles("dbtest")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DailySleepLogRepositoryTests {

    @Autowired
    private DailySleepLogRepository repository;

    @Test
    public void dataLoadTest() {
        Assertions.assertNotNull(repository);

        List<DailySleepLogDataMapper> data = repository.findAll();
        Assertions.assertEquals(3, data.size());
    }

    @Test
    public void insertDataTest() {
        repository.save(DailySleepLogDataMapper.builder()
                .userId(2L)
                .sleepStart(LocalDateTime.of(2024, 1, 5, 10, 0, 0).toInstant(ZoneOffset.UTC))
                .sleepEnd(LocalDateTime.of(2024, 1, 6, 8, 0, 0).toInstant(ZoneOffset.UTC))
                .sleepDuration(600L)
                .sleepQuality(SleepQuality.OK)
                .build());

        List<DailySleepLogDataMapper> data = repository.findAll();
        Assertions.assertEquals(4, data.size());
        Optional<DailySleepLogDataMapper> insertedData = repository.findById(4L);
        Assertions.assertTrue(insertedData.isPresent());
        Assertions.assertEquals(600L, insertedData.get().getSleepDuration());
        Assertions.assertEquals(SleepQuality.OK, insertedData.get().getSleepQuality());
        Assertions.assertEquals(2L, insertedData.get().getUserId());
    }
}
