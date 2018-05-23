package com.github.stodorov.entities;

import com.github.stodorov.web.dto.AnnouncementDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="announcements")
public class Announcement implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="announcement_id")
    private Long id;
    private String title;
    private String content;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private User author;
    @OneToMany(mappedBy = "announcement")
    private Set<AnnouncementUsers> announcementUsers = new HashSet<>();

    public Announcement(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public AnnouncementDto asDto() {
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setTitle(this.getTitle());
        announcementDto.setContent(this.getContent());
        announcementDto.setAuthor(this.getAuthor().asDto());

        return announcementDto;
    }
}
