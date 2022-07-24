package com.example.playlist.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Playlist {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // プレイリスト名
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    // Videoのフィールドと関連付ける
    @OneToMany(mappedBy = "playlist", fetch = FetchType.EAGER)
    private List<Video> videoList;

}