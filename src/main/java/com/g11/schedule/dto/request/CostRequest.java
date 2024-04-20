package com.g11.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class CostRequest {
    private String costName;
    private Integer price;
    private LocalDate refundDate;
    private Integer idAssignment;
}
