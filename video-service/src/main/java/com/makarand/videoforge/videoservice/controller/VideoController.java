package com.makarand.videoforge.videoservice.controller;


import com.makarand.videoforge.videoservice.model.Video;
import com.makarand.videoforge.videoservice.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/videos")
public class VideoController {

    VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestPart MultipartFile file,@RequestPart String title, @RequestPart String description) throws URISyntaxException, IOException {
       Video savedVideo = videoService.uploadVideo(file, title, description);
       log.info("saved Video - " + savedVideo);
       return ResponseEntity.ok("success");
    }

}
