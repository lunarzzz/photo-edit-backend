package com.example.freechat.service;

import com.example.freechat.model.Audio;
import com.example.freechat.repository.AudioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class AudioService {

    @Autowired
    private AudioRepository repository;;



    public Audio saveAudioFileWithoutPassword(MultipartFile file) throws IOException {
        Audio audio = new Audio();
        audio.setVoice(file.getBytes());
        audio.setEncrypted(false);
        return repository.save(audio);
    }

    public Audio saveAudioFileWithPassword(MultipartFile file, Long password) throws IOException {
        Audio audio = new Audio();
        audio.setVoice(file.getBytes());
        audio.setEncrypted(true);
        audio.setPassword(password);
        return repository.save(audio);
    }
}
