package com.g11.schedule.dto.response;

import com.g11.schedule.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private int idRole;
    private String roleName;
    public RoleResponse(Role role){
        this.idRole=role.getIdRole();
        this.roleName=role.getRoleName();
    }
}
