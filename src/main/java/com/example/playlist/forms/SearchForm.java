package com.example.playlist.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {
    @NotBlank
    @Size(min = 1, max = 200)
    private String query;
}
