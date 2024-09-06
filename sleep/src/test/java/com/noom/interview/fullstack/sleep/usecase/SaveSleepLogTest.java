package com.noom.interview.fullstack.sleep.usecase;

import com.noom.interview.fullstack.sleep.db.*;
import com.noom.interview.fullstack.sleep.domain.*;
import com.noom.interview.fullstack.sleep.fixtures.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("unittest")
@SpringBootTest
public class SaveSleepLogTest {
    @InjectMocks
    private SaveSleepLog saveSleepLog;

    @Mock
    private DailySleepLogProvider provider;

    @Test
    public void saveSleepLog() {
        when(provider.save(any(DailySleepLog.class))).thenReturn(DailySleepLogFixture.defaultValues());
        when(provider.findByUserIdAndInterval(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of());

        DailySleepLog result = saveSleepLog.save(DailySleepLogFixture.defaultValuesNoId());

        assertEquals(DailySleepLogFixture.defaultValues(), result);

        ArgumentCaptor<DailySleepLog> captor = ArgumentCaptor.forClass(DailySleepLog.class);
        verify(provider, times(1)).save(captor.capture());
    }

    @Test
    public void saveSleepLogConflictTest() {
        when(provider.save(any(DailySleepLog.class))).thenReturn(DailySleepLogFixture.defaultValues());
        when(provider.findByUserIdAndInterval(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(List.of(DailySleepLogFixture.defaultValues()));

        Exception exception = assertThrows(RuntimeException.class, () -> saveSleepLog.save(DailySleepLogFixture.defaultValuesNoId()));

        assertTrue(exception.getMessage().contains("You already have a log between"));


    }
}
