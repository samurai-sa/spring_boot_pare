package com.example.playlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.playlist.entities.Video;

public interface VideotRepository extends JpaRepository<Video, Integer> {

}
