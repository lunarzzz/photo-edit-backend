package com.example.freechat.vo;

import lombok.Data;

@Data
public class AudioVO {

    private boolean isSucceed;
    private byte[] audio;
    private Integer code;
}
