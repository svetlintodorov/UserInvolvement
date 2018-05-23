package com.github.stodorov.services;

import com.github.stodorov.entities.Announcement;
import com.github.stodorov.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement findById(Long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if (!announcement.isPresent()) {
            return announcement.get();
        }

        return announcement.get();
    }

    public void save(Announcement announcement){
        announcementRepository.save(announcement);
    }
}
