package com.github.stodorov.repositories;

import com.github.stodorov.entities.AnnouncementUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementUsersRepository extends JpaRepository<AnnouncementUsers, Long> {
    List<AnnouncementUsers> findAnnouncementUsersByAnnouncementId(Long announcementId);
}
