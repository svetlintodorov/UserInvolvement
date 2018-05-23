package com.github.stodorov.web.dto;

import com.github.stodorov.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDto implements ModelConvertable<Role> {
    private String name;

    public RoleDto(String name) {
        this.name = name;
    }

    @Override
    public Role asModel() {
        Role role = new Role();
        role.setName(this.getName());

        return role;
    }
}
