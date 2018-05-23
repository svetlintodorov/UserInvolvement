package com.github.stodorov.integration;

import com.github.stodorov.UserInvolvementApplication;
import com.github.stodorov.entities.Role;
import com.github.stodorov.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = UserInvolvementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIT {

    private static final String ROLE_NAME1 = "USER";
    private static final String ROLE_NAME2 = "ACTUATOR";

    @Test
    public void testRegisterUser() {
        User user = new User("dom", "password", buildListOfRoles());

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate
                .exchange("http://localhost:8080/api/register", HttpMethod.POST, request, User.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @Rollback
    public void testLoginUser() {
        User user = new User("admin", "pass", buildListOfRoles());

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate
                .exchange("http://localhost:8080/api/register", HttpMethod.POST, request, User.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);

        HttpEntity<User> requestLogin = new HttpEntity<>(user);
        ResponseEntity<User> responseLogin = restTemplate
                .exchange("http://localhost:8080/api/login", HttpMethod.POST, requestLogin, User.class);

        Assert.assertEquals(responseLogin.getStatusCode(), HttpStatus.OK);
    }

    private List<Role> buildListOfRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ROLE_NAME1));
        roles.add(new Role(ROLE_NAME2));

        return roles;
    }
}

