package com.noom.interview.fullstack.sleep.controller.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private List<String> errors;
}
