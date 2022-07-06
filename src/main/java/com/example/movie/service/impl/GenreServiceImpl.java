package com.example.movie.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.entity.Genre;
import com.example.movie.repository.GenreRepository;
import com.example.movie.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	GenreRepository genreRepo;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Genre add(Genre genre) {
		return genreRepo.save(genre);
	}

	@Override
	public Page<Genre> findAll(int page, int size, String sortBy, String sortDirection) {
		Sort sort = getSortDirection(sortBy, sortDirection);
		Pageable paging = PageRequest.of(page, size, sort);
		Page<Genre> pageGenre = genreRepo.findAll(paging);
		
		return pageGenre;
	}

	@Override
	public Optional<Genre> findById(UUID id) {
		return genreRepo.findById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Genre update(Genre genre) {
		return genreRepo.save(genre);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(UUID id) {
		if(findById(id).isPresent()) {
			genreRepo.delete(findById(id).get());
		}
	}

	private Sort getSortDirection(String sortBy, String direction) {
		return "asc".equals(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	}
}
