package com.example.movie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class MovieThumbnail {
	
	private String title;

	private String director;

	private String cover;
	
	@JsonIgnore
	private String coverPath;
}
