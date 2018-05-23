package com.github.stodorov.integration;

import com.github.stodorov.UserInvolvementApplication;
import com.github.stodorov.entities.Announcement;
import com.github.stodorov.entities.Role;
import com.github.stodorov.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = UserInvolvementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnnouncementRestControllerIT {

    private static final String ROLE_NAME1 = "USER";
    private static final String ROLE_NAME2 = "ACTUATOR";

    private static final String USERNAME = "peter";
    private static final String PASSWORD = "d1s2a3";

    private static final String ANNOUNCEMENT_TITLE = "Test Title";
    private static final String ANNOUNCEMENT_CONTENT = "Test Content";

    @Test
    public void testCreateAnnouncement() {
        User user = buildUserObject();

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<User> requestRegister = new HttpEntity<>(user);
        ResponseEntity<User> responseRegister = restTemplate
                .exchange("http://localhost:8080/api/register", HttpMethod.POST, requestRegister, User.class);

        Assert.assertEquals(responseRegister.getStatusCode(), HttpStatus.CREATED);

        HttpEntity<User> requestLogin = new HttpEntity<>(user);
        ResponseEntity<User> responseLogin = restTemplate
                .exchange("http://localhost:8080/api/login", HttpMethod.POST, requestLogin, User.class);

        Assert.assertEquals(responseLogin.getStatusCode(), HttpStatus.OK);

        Announcement announcement = buildAnnouncementObject();

        String header = responseLogin.getHeaders().get("Set-Cookie")
                .stream()
                .map(n -> String.valueOf(n)).collect(Collectors.joining(";"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("cookie", header);

        HttpEntity<Announcement> requestAnnouncement = new HttpEntity<>(announcement, headers);
        ResponseEntity<Announcement> responseAnnouncement = restTemplate
                .exchange("http://localhost:8080/api/announcement", HttpMethod.POST, requestAnnouncement, Announcement.class);

        Assert.assertEquals(responseAnnouncement.getStatusCode(), HttpStatus.CREATED);
    }

    private List<Role> buildListOfRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ROLE_NAME1));
        roles.add(new Role(ROLE_NAME2));

        return roles;
    }

    private User buildUserObject() {
        return new User(USERNAME, PASSWORD, buildListOfRoles());
    }

    private Announcement buildAnnouncementObject() {
        return new Announcement(ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, null);
    }
}
