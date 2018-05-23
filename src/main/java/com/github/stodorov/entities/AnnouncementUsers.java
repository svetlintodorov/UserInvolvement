package com.github.stodorov.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name="announcement_users")
public class AnnouncementUsers implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="announcement_user_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "feedback")
    private UserFeedback feedback;

    public AnnouncementUsers(Announcement announcement, User user, UserFeedback feedback) {
        this.announcement = announcement;
        this.user = user;
        this.feedback = feedback;
    }
}
