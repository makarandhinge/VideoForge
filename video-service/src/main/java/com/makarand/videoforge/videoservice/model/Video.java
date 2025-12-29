package com.makarand.videoforge.videoservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String description;

    private double size;

    private String createdAt;


}
