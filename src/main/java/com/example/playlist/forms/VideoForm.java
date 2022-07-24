package com.example.playlist.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoForm {
	@Size(max = 100)
	private String name;

	@NotNull
	private int playlistId;

	@NotNull
	private String url;

}
