package com.example.playlist.controllers;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.playlist.entities.Playlist;
import com.example.playlist.entities.Video;
import com.example.playlist.forms.VideoForm;
import com.example.playlist.repositories.PlaylistRepository;
import com.example.playlist.repositories.VideotRepository;

@RequestMapping("/video")
@Controller
public class VideoController {

	private PlaylistRepository playlistRepo;
	private VideotRepository videoRepo;

	@Autowired
	public VideoController(PlaylistRepository playlistRepo, VideotRepository videoRepo) {
		this.playlistRepo = playlistRepo;
		this.videoRepo = videoRepo;
	}

	// 一覧画面
	@GetMapping("/")
	public String index(Model model) {
		return "movies";
	}

	// 新規登録画面
	@GetMapping("/create")
	public String create(@ModelAttribute("videoForm") VideoForm videoForm, Model model) {
		List<Playlist> playlists = playlistRepo.findAll();
		if (playlists.size() == 0) {
			return "redirect:/playlists/";
		}
		model.addAttribute("playLists", playlistRepo.findAll());
		model.addAttribute("videoForm", videoForm);
		return "blank";
	}

	// 登録処理
	@PostMapping("/create")
	public String createVideo(@Valid VideoForm videoForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return create(videoForm, model);
		}

		Video video = new Video();
		Playlist playlist = playlistRepo.findById(videoForm.getPlaylistId()).orElseThrow();
		video.setPlaylist(playlist);
		video.setUrl(videoForm.getUrl());

		// 動画タイトルを取得
		String movieTitle = null;
		try {
			Document document = Jsoup.connect(video.getUrl()).get();
			movieTitle = document.title().replace("- YouTube", " ").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// name が空欄で入力されたら動画タイトルをセットする
		if (videoForm.getName().isEmpty()) {
			video.setName(movieTitle);
		} else {
			video.setName(videoForm.getName());
		}
		
		// サムネイルを取得
		String[] url = video.getUrl().split("v=");
		// https://img.youtube.com/vi/<ここにID>/hqdefault.jpg
		String thumbnail = "https://img.youtube.com/vi/" + url[1] + "/hqdefault.jpg";
		video.setThumbnail(thumbnail);

		videoRepo.save(video);
		return "redirect:/playlists/" + playlist.getId() + "/videos/";
	}

	@PostMapping("/delete/{id}")
	public String deleteVideo(@PathVariable("id") int id, Model model) {
		int playlistId = videoRepo.findById(id).orElseThrow().getPlaylist().getId();
		videoRepo.deleteById(id);
		return "redirect:/playlists/" + playlistId + "/videos/";
	}
}
