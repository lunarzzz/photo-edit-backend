package com.example.freechat.service.impl;

import com.example.freechat.model.Audio;
import com.example.freechat.repository.AudioRepository;
import com.example.freechat.service.IAudioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class AudioService implements IAudioService {

    private final AudioRepository repository;;

    public AudioService(@Autowired AudioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Audio saveAudioFile(MultipartFile file, boolean isEncrypted, Long password) throws IOException {
        Audio audio = new Audio();
        audio.setEncrypted(isEncrypted);
        audio.setPassword(password);
        audio.setVoice(file.getBytes());
        return repository.save(audio);
    }
}
