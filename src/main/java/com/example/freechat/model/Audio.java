package com.example.freechat.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isEncrypted;

    @Column
    private Long password;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] voice;
}
