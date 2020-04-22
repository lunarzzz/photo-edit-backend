package com.example.freechat.model;

import lombok.Data;

@Data
public class User {
    private String id;
    private String userName;
    private String password;
    private String email;
    private String description;
}
