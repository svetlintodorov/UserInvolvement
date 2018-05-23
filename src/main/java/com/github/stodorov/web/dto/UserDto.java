package com.github.stodorov.web.dto;

import com.github.stodorov.entities.Role;
import com.github.stodorov.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto implements ModelConvertable<User> {
    private String username;
    private String password;
    private List<RoleDto> roles;

    public UserDto(String username, String password, List<RoleDto> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public User asModel() {
        User user = new User();
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        List<Role> rls = new ArrayList<>();
        if (roles != null) {
            roles.stream().forEach(r -> rls.add(r.asModel()));
        }
        user.setRoles(rls);

        return user;
    }
}