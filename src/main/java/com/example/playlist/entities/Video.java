package com.example.playlist.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class Video {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Playlist playlist;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "url", length = 200, nullable = false)
    private String url;

    @Column(name = "thumbnail", length = 200, nullable = false)
    private String thumbnail;

}
