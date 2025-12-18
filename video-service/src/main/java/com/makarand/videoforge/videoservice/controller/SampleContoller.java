package com.makarand.videoforge.videoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class SampleContoller {

    @GetMapping("/greet")
    public String greeting(){
        return "WElCOME VIDEO LIBRARY";
    }
}
