package com.github.stodorov.web.dto;

import com.github.stodorov.entities.AnnouncementUsers;
import com.github.stodorov.entities.UserFeedback;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnouncementUsersDto implements ModelConvertable<AnnouncementUsers> {

    private AnnouncementDto announcement;
    private UserDto user;
    private UserFeedback feedback;

    public AnnouncementUsersDto(AnnouncementDto announcement, UserDto user, UserFeedback feedback) {
        this.announcement = announcement;
        this.user = user;
        this.feedback = feedback;
    }

    @Override
    public AnnouncementUsers asModel() {
        AnnouncementUsers announcementUser = new AnnouncementUsers();
        announcementUser.setAnnouncement(this.announcement.asModel());
        announcementUser.setUser(this.user.asModel());
        announcementUser.setFeedback(this.feedback);

        return announcementUser;
    }
}
