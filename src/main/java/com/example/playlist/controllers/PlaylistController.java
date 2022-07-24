package com.example.playlist.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.example.playlist.entities.Playlist;
import com.example.playlist.entities.Video;
import com.example.playlist.forms.PlaylistForm;
import com.example.playlist.forms.SearchForm;
import com.example.playlist.repositories.PlaylistRepository;
import com.example.playlist.repositories.VideotRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final VideotRepository videoRepository;

    public PlaylistController(PlaylistRepository playlistRepository, VideotRepository videoRepository) {
        this.playlistRepository = playlistRepository;
        this.videoRepository = videoRepository;
    }

    // プレイリスト一覧画面 + 検索フォーム
    @GetMapping("/playlists/")
    private String playlistsView(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
        List<Playlist> playlists = playlistRepository.findAll();
        model.addAttribute("playlists", playlists);
        return "playlists";
    }

    // プレイリスト登録画面
    @GetMapping("/playlists/new")
    private String createPlaylist(@ModelAttribute("playlistForm") PlaylistForm playlistForm, Model model) {
        return "createPlaylist";
    }

    // 登録POST先
    @PostMapping("/playlists/new")
    private String createPlaylistProcess(@Valid PlaylistForm playlistForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 入力にエラーがあれば戻す
            return createPlaylist(playlistForm, model);
        }
        Playlist playlist = new Playlist();
        playlist.setName(playlistForm.getName());
        playlistRepository.save(playlist);
        return "redirect:/playlists/";
    }

    // 削除
    @PostMapping("/playlists/delete/{id}")
    private String deletePlaylist(@PathVariable("id") int id, Model model) {
        playlistRepository.deleteById(id);
        return "redirect:/playlists/";
    }

    // プレイリストの動画一覧
    @GetMapping("/playlists/{id}/videos/")
    private String videosView(@PathVariable("id") int id, Model model) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow();
        // List<Video> videos = playlist.getVideoList();
        model.addAttribute("playlist", playlist);
        // model.addAttribute("videos", videos);
        return "movies";
    }

    // 検索プロセス
    @PostMapping("/search/process")
    public String searchProcess(@Valid SearchForm searchForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return playlistsView(searchForm, model);
        }

        String query = searchForm.getQuery();
        System.out.println(query);
        // String query = searchForm.getQuery().replaceAll("\\W", "+");
        redirectAttributes.addAttribute("query", query);
        return "redirect:/search/result/{query}";
        // return searchResult(query, searchForm, model, true);
    }

    // 部分一致で検索
    @GetMapping("/search/result/{query}")
    public String searchResult(@PathVariable("query") String query, @ModelAttribute("searchForm") SearchForm searchForm,
            Model model, boolean isAble) {

        List<Video> result = new ArrayList<>();
        for (Video video : videoRepository.findAll()) {
            if (video.getName().contains(query)) {
                result.add(video);
            }
        }
        model.addAttribute("result", result);
        return "searchResult";
    }
}
