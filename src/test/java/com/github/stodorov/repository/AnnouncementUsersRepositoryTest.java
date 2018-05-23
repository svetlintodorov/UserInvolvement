package com.github.stodorov.repository;

import com.github.stodorov.entities.*;
import com.github.stodorov.repositories.AnnouncementUsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AnnouncementUsersRepositoryTest {
    private static final String ROLE_NAME1 = "USER";
    private static final String ROLE_NAME2 = "ACTUATOR";

    private static final String USERNAME = "test";
    private static final String PASSWORD = "pass";

    private static final String ANNOUNCEMENT_TITLE = "Title";
    private static final String ANNOUNCEMENT_CONTENT = "Content";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnnouncementUsersRepository announcementUsersRepository;

    // TODO Test DISLIKE will be the same just only replace UserFeedback.LIKE with UserFeedback.DISLIKE
    @Test
    public void testLikeAnnouncement() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(ROLE_NAME1));
        roles.add(new Role(ROLE_NAME2));
        User user =new User(USERNAME, PASSWORD, roles);
        entityManager.persist(user);

        Announcement announcement = new Announcement(ANNOUNCEMENT_TITLE, ANNOUNCEMENT_CONTENT, user);
        entityManager.persist(announcement);

        AnnouncementUsers announcementUsers = new AnnouncementUsers();
        announcementUsers.setAnnouncement(announcement);
        announcementUsers.setUser(user);
        announcementUsers.setFeedback(UserFeedback.LIKE);
        entityManager.persist(announcementUsers);

        List<AnnouncementUsers> foundList = announcementUsersRepository.findAnnouncementUsersByAnnouncementId(announcement.getId());
        assertNotNull(foundList.get(0));
        assertEquals(foundList.get(0).getFeedback(), UserFeedback.LIKE);
        assertEquals(announcementUsers.getUser().getUsername(), user.getUsername());
    }

    @Test
    public void testFindAnnouncementUsersByAnnouncementId() {
        List<Role> roles = buildListOfRoles();
        User user = buildUserObject();
        entityManager.persist(user);

        Announcement announcement = buildAnnouncementObject();
        entityManager.persist(announcement);

        AnnouncementUsers announcementUsers = new AnnouncementUsers();
        announcementUsers.setAnnouncement(announcement);
        announcementUsers.setUser(user);
        announcementUsers.setFeedback(UserFeedback.LIKE);
        entityManager.persist(announcementUsers);

        List<AnnouncementUsers> foundAnnouncementUsers = announcementUsersRepository.findAnnouncementUsersByAnnouncementId(announcement.getId());
        assertNotNull(foundAnnouncementUsers);
        assertEquals(foundAnnouncementUsers.get(0).getAnnouncement().getId(), announcement.getId());
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
