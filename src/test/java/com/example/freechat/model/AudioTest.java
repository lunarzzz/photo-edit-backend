package com.example.freechat.model;

import com.example.freechat.repository.AudioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AudioTest {


    @Autowired
    private AudioRepository audioRepository;



    @Test
    public void test() {
        int size = audioRepository.findAll().size();
        System.out.println("size is " + size);
        Audio audio = new Audio();
        audio.setEncrypted(false);
        audio.setVoice("12345678".getBytes());
        audio.setPassword(123456L);
        Audio save = audioRepository.save(audio);
        System.out.println(save.getId());
    }

    @Test
    public void test2() {

        Audio audio = audioRepository.findById(9L).get();
        byte[] voice = audio.getVoice();
        try(BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("/Users/apple/Desktop/test3.wav"));) {
            outputStream.write(voice, 0, voice.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}