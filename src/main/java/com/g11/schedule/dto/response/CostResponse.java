package com.g11.schedule.dto.response;

import com.g11.schedule.entity.Cost;
import com.g11.schedule.entity.Team;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.apache.bcel.classfile.Code;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CostResponse {
    private int idCost;
    private int iDAssigment;
    private String costName;
    private Integer price;
    private LocalDate refundDate;
    private String nameAssigment;
    public CostResponse(Cost cost){
        this.idCost=cost.getIdCost();
        this.iDAssigment=cost.getAssigment().getIdAssigment();
        this.costName=cost.getCostName();
        this.price=cost.getPrice();
        this.refundDate = cost.getRefundDay();
        this.nameAssigment=cost.getAssigment().getNameAssignment();
    }
}
