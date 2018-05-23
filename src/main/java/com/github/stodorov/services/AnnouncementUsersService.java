package com.github.stodorov.services;

import com.github.stodorov.entities.AnnouncementUsers;
import com.github.stodorov.repositories.AnnouncementUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnnouncementUsersService {

    @Autowired
    private AnnouncementUsersRepository announcementUsersRepository;

    public List<AnnouncementUsers> findAnnouncementUsersByAnnouncement(Long announcementId) {
        return announcementUsersRepository.findAnnouncementUsersByAnnouncementId(announcementId);
    }

    public void save(AnnouncementUsers announcementUsers) {
        announcementUsersRepository.save(announcementUsers);
    }
}
