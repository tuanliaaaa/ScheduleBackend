package com.g11.schedule.dto.response;

import com.g11.schedule.entity.Cost;
import com.g11.schedule.entity.Team;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.apache.bcel.classfile.Code;

@Data
@AllArgsConstructor
public class CostResponse {
    private int idCost;
    private int iDTeam;
    private String costName;
    private Integer price;
    public CostResponse(Cost cost){
        this.idCost=cost.getIdCost();
        this.iDTeam=cost.getTeam().getIdTeam();
        this.costName=cost.getCostName();
        this.price=cost.getPrice();
    }
}
