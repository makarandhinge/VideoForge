package com.makarand.videoforge.videoservice.repository;

import com.makarand.videoforge.videoservice.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {


}
