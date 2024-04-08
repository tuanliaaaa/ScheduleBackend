package com.g11.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class CostRequest {
    private String costName;
    private Integer price;
}
