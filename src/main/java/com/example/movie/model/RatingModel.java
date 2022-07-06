package com.example.movie.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RatingModel {

	private UUID id;
	
	@NotNull
	private UUID movieId;
	
	@NotNull
	private UUID usersId;
	
	@NotNull
	private int rating;
}
