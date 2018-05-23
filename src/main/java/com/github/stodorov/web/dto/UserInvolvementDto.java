package com.github.stodorov.web.dto;

import lombok.Data;

@Data
public class UserInvolvementDto {

    int likes;
    int dislikes;

    public UserInvolvementDto(int likes, int dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }
}
