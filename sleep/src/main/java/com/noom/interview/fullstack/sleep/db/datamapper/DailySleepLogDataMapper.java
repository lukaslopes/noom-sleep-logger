package com.noom.interview.fullstack.sleep.db.datamapper;

import com.noom.interview.fullstack.sleep.entity.*;
import lombok.*;
import org.springframework.data.annotation.*;

import javax.persistence.Id;
import javax.persistence.*;
import java.time.*;

@Data
@Entity(name = "daily_sleep_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailySleepLogDataMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Instant sleepStart;
    private Instant sleepEnd;
    private Long sleepDuration;
    @Enumerated(EnumType.ORDINAL)
    private SleepQuality sleepQuality;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
}
