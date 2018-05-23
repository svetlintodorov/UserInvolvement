package com.github.stodorov.controllers;

import com.github.stodorov.entities.Announcement;
import com.github.stodorov.entities.AnnouncementUsers;
import com.github.stodorov.entities.UserFeedback;
import com.github.stodorov.services.AnnouncementService;
import com.github.stodorov.services.AnnouncementUsersService;
import com.github.stodorov.services.UserService;
import com.github.stodorov.web.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/api"})
public class AnnouncementUsersController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementUsersService announcementUsersService;

    @Autowired
    private UserService userService;

    @GetMapping("/announcement/{announcementId}")
    public ResponseEntity<?> getUserInvolvement(@PathVariable Long announcementId) {
        log.info("Get user involvement: ");

        List<AnnouncementUsers> announcementUsers = announcementUsersService.findAnnouncementUsersByAnnouncement(announcementId);
        int likes = (int) announcementUsers.stream().filter(au -> au.getFeedback() == UserFeedback.LIKE).count();
        int dislikes = announcementUsers.size() - likes;

        return new ResponseEntity<>(new UserInvolvementDto(likes, dislikes), HttpStatus.OK);
    }

    @PostMapping({"/announcements/{announcementId}/like"})
    public ResponseEntity<?> likeAnnouncement(@PathVariable Long announcementId) {
        log.info("User like " + announcementId);
        Announcement foundAnnouncement = announcementService.findById(announcementId);
        if (foundAnnouncement == null) {
            log.error("Announcement with this: "+ announcementId + " does't exist!");
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }

        AnnouncementUsers announcementUser = buildAnnouncementUser(foundAnnouncement, UserFeedback.LIKE);

        announcementUsersService.save(announcementUser);
        log.info("User involvement: " + foundAnnouncement.getTitle() + " was submitted!");

        return new ResponseEntity<>(announcementId, HttpStatus.CREATED);
    }

    @PostMapping({"/announcements/{announcementId}/dislike"})
    public ResponseEntity<?> dislikeAnnouncement(@PathVariable Long announcementId) {
        log.info("User dislike " + announcementId);

        Announcement foundAnnouncement = announcementService.findById(announcementId);
        if (foundAnnouncement == null) {
            log.error("Announcement with this: "+ announcementId + " does't exist!");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AnnouncementUsers announcementUser = buildAnnouncementUser(foundAnnouncement, UserFeedback.DISLIKE);
        announcementUsersService.save(announcementUser);
        log.info("User involvement: " + foundAnnouncement.getTitle() + " was submitted!");

        return new ResponseEntity<>(announcementId, HttpStatus.CREATED);
    }

    private AnnouncementUsers buildAnnouncementUser(Announcement announcement, UserFeedback feedback) {
        AnnouncementUsers announcementUser = new AnnouncementUsers();
        announcementUser.setAnnouncement(announcement);
        announcementUser.setUser(userService.getCurrentlyLoggedUser());
        announcementUser.setFeedback(feedback);

        return announcementUser;
    }
}
