package com.makarand.videoforge.videoservice.service;

import com.makarand.videoforge.videoservice.model.Video;
import com.makarand.videoforge.videoservice.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VideoService {

    VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }
    private final Path root = Paths.get("/home/makarandhinge_dekstop/videos");

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy ss-mm-HH");

    public Video uploadVideo(MultipartFile file, String title, String description) throws URISyntaxException, IOException {
        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setCreatedAt(LocalDateTime.now().format(format));
        video.setSize(file.getSize());

        Video savedVideo = videoRepository.save(video);

        String filename = String.valueOf(savedVideo.getId());
        Path target = root.resolve(filename);

        try(InputStream in = file.getInputStream()){
            Files.copy(in,target, StandardCopyOption.REPLACE_EXISTING);
        }
        return savedVideo;
    }

    public List<String> getVideoList() {
        List<Video> listOfVideos = videoRepository.findAll();
        return listOfVideos.stream().map(v -> v.getTitle()).collect(Collectors.toList());
    }
}
