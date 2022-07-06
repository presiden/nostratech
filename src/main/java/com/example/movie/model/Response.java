package com.example.movie.model;

import lombok.Data;

@Data
public class Response {	
	
	private Object content;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Response(Object content) {
		this.content = content;
	}
}
