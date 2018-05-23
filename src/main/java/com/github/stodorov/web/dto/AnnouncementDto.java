package com.github.stodorov.web.dto;

import com.github.stodorov.entities.Announcement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnouncementDto implements ModelConvertable<Announcement> {

    private String title;
    private String content;
    private UserDto author;

    public AnnouncementDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public AnnouncementDto(String title, String content, UserDto author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public Announcement asModel() {
        Announcement announcement = new Announcement();
        announcement.setTitle(this.title);
        announcement.setContent(this.content);
        if (this.author != null) {
            announcement.setAuthor(this.author.asModel());
        }

        return announcement;
    }
}
