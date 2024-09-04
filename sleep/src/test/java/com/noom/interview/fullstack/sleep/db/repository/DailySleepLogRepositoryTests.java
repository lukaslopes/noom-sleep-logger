package com.noom.interview.fullstack.sleep.db.repository;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import com.noom.interview.fullstack.sleep.entity.*;
import com.noom.interview.fullstack.sleep.fixtures.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
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
        DailySleepLogDataMapper fixture = DailySleepLogDataMapperFixture.insertValues();
        repository.save(fixture);

        List<DailySleepLogDataMapper> data = repository.findAll();
        Assertions.assertEquals(4, data.size());
        Optional<DailySleepLogDataMapper> insertedData = repository.findById(4L);
        Assertions.assertTrue(insertedData.isPresent());
        Assertions.assertEquals(fixture.getSleepDuration(), insertedData.get().getSleepDuration());
        Assertions.assertEquals(fixture.getSleepQuality(), insertedData.get().getSleepQuality());
        Assertions.assertEquals(fixture.getUserId(), insertedData.get().getUserId());
    }

    @Test
    public void findByUserIdAndIntervalTest() {
        List<DailySleepLogDataMapper> data = repository.findByUserIdAndSleepEndBetweenOrderBySleepEndDesc(2L,
            LocalDateTime.of(2024, 1, 2, 0, 0),
            LocalDateTime.of(2024, 1, 2, 23, 59));
        Assertions.assertEquals(1, data.size());
        Assertions.assertEquals(SleepQuality.BAD, data.get(0).getSleepQuality());
    }

    @Test
    public void findByUserIdAndIntervalTwoDaysTest() {
        List<DailySleepLogDataMapper> data = repository.findByUserIdAndSleepEndBetweenOrderBySleepEndDesc(2L,
            LocalDateTime.of(2024, 1, 2, 0, 0),
            LocalDateTime.of(2024, 1, 3, 23, 59));
        Assertions.assertEquals(2, data.size());
        Assertions.assertEquals(SleepQuality.BAD, data.get(1).getSleepQuality());
        Assertions.assertEquals(SleepQuality.OK, data.get(0).getSleepQuality());
    }
}
