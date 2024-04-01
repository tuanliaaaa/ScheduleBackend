package com.g11.schedule.dto.response;

import com.g11.schedule.entity.Account;
import com.g11.schedule.entity.Assigment;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssigmentUserResponse {
    private int idAssigmentUser;
    private String username;
    private String status;
    private String process;

}
