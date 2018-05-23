package com.github.stodorov.entities;

import com.github.stodorov.web.dto.RoleDto;
import com.github.stodorov.web.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="user_id")
    private Long id;
    private String username;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Role> roles;
    @OneToOne(fetch=FetchType.LAZY, mappedBy="author")
    private Announcement announcement;
    @OneToMany(mappedBy = "user")
    private Set<AnnouncementUsers> announcementUsers = new HashSet<>();

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserDto asDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername(this.getUsername());
        userDto.setPassword(this.getPassword());
        List<RoleDto> rolesDto = new ArrayList<>();
        if (this.getRoles() != null) {
            this.getRoles().stream().forEach(r -> rolesDto.add(new RoleDto(r.getName())));
        }

        userDto.setRoles(rolesDto);

        return userDto;
    }
}
