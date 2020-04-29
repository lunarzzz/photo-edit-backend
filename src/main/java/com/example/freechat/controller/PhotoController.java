package com.example.freechat.controller;

import com.example.freechat.model.Audio;
import com.example.freechat.repository.AudioRepository;
import com.example.freechat.service.IAudioService;
import com.example.freechat.service.impl.ZxingService;
import com.example.freechat.util.Response;
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

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class PhotoController {

    @Autowired
    private AudioRepository repository;

    @Autowired
    private IAudioService audioService;

    @Value("voice.domain")
    private String domain;

    @PostMapping(value = "/fileUpload")
    public Response uploadImage(@RequestParam("myFile") MultipartFile file,
                                     @RequestParam("myAudio") MultipartFile audioFile,
                                     @RequestParam("isEncrypted") boolean isEncrypted,
                                     @RequestParam("password") Long password) {
        log.info("upload succeed");
        log.info("Name is " + audioFile.getName());
        try {
            audioFile.transferTo(Paths.get("/Users/apple/Desktop/myAudio.wav"));
        } catch (IOException e) {
            return Response.notFount("failed to upload image.");
        }
        // save audio 这个逻辑不应该放在这里
        Audio audio = audioService.saveAudioFile(audioFile, isEncrypted, password);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ZxingService.addErweima(file,domain + "/audio/" + audio.getId().toString(), false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return Response.ok().withData(bufferedImage);
    }

    @Bean
    public BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter(){
        return new BufferedImageHttpMessageConverter();
    }

    @GetMapping(value = "/audio")
    public Response getAudio(@RequestParam("audioId") Long audioId,
                           @RequestParam(name = "isEncrypted", required = false) boolean isEncrypted,
                           @RequestParam(name = "password", required = false) Long password) {
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
        return Response.ok().withData(audioVO.getAudio());
    }
}
