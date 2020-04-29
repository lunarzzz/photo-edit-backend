package com.example.freechat.service;

import com.example.freechat.model.Audio;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface IAudioService {
    Audio saveAudioFile(MultipartFile file, boolean isEncrypted, Long password) throws IOException;
}
