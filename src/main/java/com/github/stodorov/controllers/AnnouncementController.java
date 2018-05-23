package com.github.stodorov.controllers;

import com.github.stodorov.entities.Announcement;
import com.github.stodorov.services.AnnouncementService;
import com.github.stodorov.services.UserService;
import com.github.stodorov.web.dto.AnnouncementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping({"/api"})
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserService userService;

    @PostMapping({"/announcement"})
    public ResponseEntity<?> createAnnouncement(@Valid @RequestBody AnnouncementDto announcementDto) {
        log.info("Creating Announcement " + announcementDto.getTitle());

        Announcement announcement = announcementDto.asModel();
        announcement.setAuthor(userService.getCurrentlyLoggedUser());
        announcementService.save(announcement);

        log.info("Announcement " + announcementDto.getTitle() + " was created!");

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
