package com.example.freechat.controller;

import com.beust.jcommander.internal.Lists;
import com.example.freechat.model.Audio;
import com.example.freechat.repository.AudioRepository;
import com.example.freechat.service.AudioService;
import com.example.freechat.service.ZxingService;
import com.example.freechat.vo.AudioVO;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class GreetingController {
    private ZxingService service = new ZxingService();
    @GetMapping("/age")
    public Integer g() {
        return 12;
    }

    @Autowired
    private AudioRepository repository;

    @Autowired
    private AudioService audioService;

    @Value("voice.domain")
    private String domain;


    @GetMapping("/relation")
    public String getRelation(@RequestParam String name) {
        System.out.println("hello relation");
        return name + " has no relationship with me";
    }

    @PostMapping(value = "/fileUpload", produces = MediaType.IMAGE_JPEG_VALUE)
    public BufferedImage uploadImage(@RequestParam("myFile") MultipartFile file, @RequestParam("myAudio") MultipartFile audioFile, @RequestParam("isisEncrypted") boolean isisEncrypted, @RequestParam("password") Long password) throws IOException, WriterException {
        log.info("upload succeed");
        log.info("Name is " + audioFile.getName());
        audioFile.transferTo(Paths.get("/Users/apple/Desktop/myAudio.wav"));
        // save audio
        Audio audio;
        if(isisEncrypted) {
            audio = audioService.saveAudioFileWithPassword(audioFile, password);
        } else {
            audio = audioService.saveAudioFileWithoutPassword(audioFile);
        }
        BufferedImage bufferedImage = service.addErweima(file,domain + "/audio/" + audio.getId().toString(), false);
//        ZxingUtils.saveFile(ZxingUtils.bufferedImageToInputStream(bufferedImage), "/Users/apple/Desktop/test2.jpg");
        return bufferedImage;
    }
    @PostMapping(value = "/upload")
    public String upload(@RequestParam("myAudio") MultipartFile audioFile) throws IOException, WriterException {
        log.info("upload succeed");
        log.info("Name is " + audioFile.getName());
        audioFile.transferTo(Paths.get("/Users/apple/Desktop/myAudio.wav"));
        return "success";
    }

    @Bean
    public BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter(){ return new BufferedImageHttpMessageConverter(); }

    @GetMapping(value = "/audio", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAudio(@RequestParam("audioId") Long audioId, @RequestParam(name = "isEncrypted", required = false) boolean isEncrypted, @RequestParam(name = "password", required = false) Long password) {
        log.info("start to get audio" + audioId + "|" + isEncrypted + "|" + password);
        // get from database
        Audio audio = repository.findById(audioId).orElse(new Audio());
        AudioVO audioVO = new AudioVO();
        if(audio.isEncrypted()) {
            if(audio.getPassword().equals(password)) {
                audioVO.setAudio(audio.getVoice());
                audioVO.setSucceed(true);
            } else {
                audioVO.setSucceed(false);
            }
        } else {
            audioVO.setSucceed(true);
            audioVO.setAudio(audio.getVoice());
        }
        return audioVO.getAudio();
    }
}
