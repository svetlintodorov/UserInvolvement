package com.github.stodorov.repository;

import com.github.stodorov.entities.Announcement;
import com.github.stodorov.entities.Role;
import com.github.stodorov.entities.User;
import com.github.stodorov.repositories.AnnouncementRepository;
import com.github.stodorov.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AnnouncementRepositoryTest {

    private static final String ROLE_NAME1 = "USER";
    private static final String ROLE_NAME2 = "ACTUATOR";

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";

    private static final String ANNOUNCEMENT_TITLE = "Title";
    private static final String ANNOUNCEMENT_CONTENT = "Content";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        List<Role> roles = buildListOfRoles();
        User user = buildUserObject();
        entityManager.persist(user);

        Announcement announcement = buildAnnouncementObject();
        entityManager.persist(announcement);
        Optional<Announcement> foundAnnouncement = announcementRepository.findById(announcement.getId());

        assertNotNull(foundAnnouncement);
        assertEquals(announcement.getId(), foundAnnouncement.get().getId());
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
        return new Announcement(ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, buildUserObject());
    }
}
