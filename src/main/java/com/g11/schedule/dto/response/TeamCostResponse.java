package com.g11.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamCostResponse {
    private Integer idTeam;
    private Integer cost;
    private List<CostResponse>  costResponseList;
}
