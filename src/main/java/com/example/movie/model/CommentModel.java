package com.example.movie.model;

import java.util.UUID;

import lombok.Data;

@Data
public class CommentModel {
	private UUID id;
	
	private UUID movieId;
	
	private UUID usersId;
	
	private String comment;
}
