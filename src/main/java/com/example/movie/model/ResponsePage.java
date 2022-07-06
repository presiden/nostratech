package com.example.movie.model;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ResponsePage {
	
	private Object content;
	
	private int currentPage;
	
	private long totalItems;
	
	private int totalPages;
	
	public ResponsePage(Page<?> page) {
		this.content = page.getContent();
		this.currentPage = page.getNumber();
		this.totalItems = page.getTotalElements();
		this.totalPages = page.getTotalPages();
	}
}
