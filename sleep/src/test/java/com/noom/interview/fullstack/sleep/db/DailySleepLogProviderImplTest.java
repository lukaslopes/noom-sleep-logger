package com.noom.interview.fullstack.sleep.db;

import com.noom.interview.fullstack.sleep.db.datamapper.*;
import com.noom.interview.fullstack.sleep.db.repository.*;
import com.noom.interview.fullstack.sleep.entity.*;
import com.noom.interview.fullstack.sleep.fixtures.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

import java.time.*;
import java.util.*;

import static org.mockito.Mockito.*;

@ActiveProfiles("unittest")
@SpringBootTest
public class DailySleepLogProviderImplTest {

    @InjectMocks
    private DailySleepLogProviderImpl provider;

    @Mock
    private DailySleepLogRepository sleepLogRepository;

    @Test
    public void saveTest() {
        when(sleepLogRepository.save(any(DailySleepLogDataMapper.class)))
            .thenReturn(DailySleepLogDataMapperFixture.defaultValues());

        DailySleepLog toSave = DailySleepLogFixture.defaultValuesNoId();
        DailySleepLog result = provider.save(toSave);
        Assertions.assertEquals(1L, result.getId());

        ArgumentCaptor<DailySleepLogDataMapper> captor = ArgumentCaptor.forClass(DailySleepLogDataMapper.class);
        verify(sleepLogRepository, times(1)).save(captor.capture());
        DailySleepLogDataMapper data = captor.getValue();
        Assertions.assertEquals(toSave.getSleepStart().toInstant(ZoneOffset.UTC), data.getSleepStart());
        Assertions.assertEquals(toSave.getSleepEnd().toInstant(ZoneOffset.UTC), data.getSleepEnd());
        Assertions.assertEquals(toSave.getSleepDuration(), data.getSleepDuration());
        Assertions.assertEquals(toSave.getSleepQuality(), data.getSleepQuality());
        Assertions.assertEquals(toSave.getUserId(), data.getUserId());
    }

    @Test
    public void findByIntervalTest(){
        DailySleepLogDataMapper resultFixture = DailySleepLogDataMapperFixture.defaultValues();
        when(sleepLogRepository.findByUserIdAndSleepEndBetween(anyLong(), any(Instant.class), any(Instant.class))).thenReturn(List.of(resultFixture));

        List<DailySleepLog> result = provider.findByUserIdAndInterval(1L, LocalDateTime.now(), LocalDateTime.now());
        verify(sleepLogRepository, times(1)).findByUserIdAndSleepEndBetween(anyLong(), any(Instant.class), any(Instant.class));
        Assertions.assertEquals(1, result.size());

        DailySleepLog data = result.get(0);
        Assertions.assertEquals(resultFixture.getSleepStart(), data.getSleepStart().toInstant(ZoneOffset.UTC));
        Assertions.assertEquals(resultFixture.getSleepEnd(), data.getSleepEnd().toInstant(ZoneOffset.UTC));
        Assertions.assertEquals(resultFixture.getSleepDuration(), data.getSleepDuration());
        Assertions.assertEquals(resultFixture.getSleepQuality(), data.getSleepQuality());
        Assertions.assertEquals(resultFixture.getUserId(), data.getUserId());
        Assertions.assertEquals(resultFixture.getId(), data.getId());

    }
}
