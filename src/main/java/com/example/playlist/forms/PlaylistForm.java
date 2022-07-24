package com.example.playlist.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistForm {

    // プレイリスト名
    @NotNull
    @Size(min = 1, max = 30)
    private String name;
}
