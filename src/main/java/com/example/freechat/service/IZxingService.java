package com.example.freechat.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface IZxingService {
    BufferedImage addErweima(MultipartFile file, String text, boolean isEncrepted);
}
