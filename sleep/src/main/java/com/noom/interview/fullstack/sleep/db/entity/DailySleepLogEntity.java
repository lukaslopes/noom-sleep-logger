package com.noom.interview.fullstack.sleep.db.entity;

import com.noom.interview.fullstack.sleep.domain.*;
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
public class DailySleepLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDateTime sleepStart;
    private LocalDateTime sleepEnd;
    private Long sleepDuration;
    @Enumerated(EnumType.ORDINAL)
    private SleepQuality sleepQuality;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
}
