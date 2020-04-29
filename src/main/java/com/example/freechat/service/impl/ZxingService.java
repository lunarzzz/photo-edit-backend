package com.example.freechat.service.impl;

import com.example.freechat.util.ZxingUtils;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author lunar
 */
@Slf4j
public class ZxingService {

    public static BufferedImage addErweima(MultipartFile file, String text, boolean isEncrypted) throws IOException, WriterException {
        log.info("start to generate QRcode...");
        BufferedImage hello_lunar = ZxingUtils.enQRCode("hello lunar", 50, 50);
        // build file image -> BufferedImage
        log.info("end to generate QRcode...");
        log.info("start to generate background image...");

        BufferedImage image  = ZxingUtils
                .drawImage(file.getInputStream());
        log.info("end to generate background image...");
        log.info("start to generate mix image...");
        BufferedImage bufferedImage = ZxingUtils.drawImage(image, hello_lunar, 100, 100);
        log.info("end to generate mix image...");
        InputStream inputStream = ZxingUtils.bufferedImageToInputStream(bufferedImage);
        try {
            //保存为本地图片
            ZxingUtils.saveFile(inputStream, "/Users/apple/Desktop/test.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }
}
