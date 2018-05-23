package com.github.stodorov.controllers;

import com.github.stodorov.entities.User;
import com.github.stodorov.services.UserService;
import com.github.stodorov.web.dto.RoleDto;
import com.github.stodorov.web.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping({"/api"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping({"/register"})
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Creating User " + userDto.getUsername());

        List<RoleDto> rolesDto = new ArrayList<>();
        rolesDto.add(new RoleDto("USER"));
        rolesDto.add(new RoleDto("ACTUATOR"));

        userDto.setRoles(rolesDto);

        User user = userDto.asModel();

        Optional<User> existing = userService.findUserByUsername(user.getUsername());
        if (existing.isPresent()){
            log.error("There is already an account registered with that username");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.save(user);

        log.info("User " + userDto.getUsername() + " was created!");

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    @PostMapping({"/login"})
    public ResponseEntity<Principal> authenticate(@Valid @RequestBody UserDto userDto) {
        log.info("UserController Login ");

        Optional<User> user = userService.findUserByUsername(userDto.getUsername());
        if (!user.isPresent()) {
            log.error("Username not found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if(!passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())){
            log.error("Invalid password!");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<GrantedAuthority> grantedAuths =
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.get().getRoles().toString());

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword(), grantedAuths));

        log.info("User successfully logged!");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
