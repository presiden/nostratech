package com.example.movie.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.movie.entity.Comment;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Users;
import com.example.movie.model.CommentModel;
import com.example.movie.repository.CommentRepository;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.UsersRepository;
import com.example.movie.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired 
	MovieRepository movieRepo;
	
	@Autowired
	UsersRepository usersRepo;
	
	@Override
	public Comment add(CommentModel commentModel) {
		Movie movie = movieRepo.findById(commentModel.getMovieId()).get();
		Users users = usersRepo.findById(commentModel.getUsersId()).get();
		
		Comment comment = new Comment();
		comment.setMovie(movie);
		comment.setUsers(users);
		comment.setComments(commentModel.getComment());
		comment.setCreatedDate(LocalDateTime.now());
		
		return commentRepo.save(comment);
	}

	@Override
	public Page<Comment> findAll(int page, int size, String sortBy, String sortDirection) {
		Sort sort = getSortDirection(sortBy, sortDirection);
		Pageable paging = PageRequest.of(page, size, sort);
		
		return commentRepo.findAll(paging);
	}

	@Override
	public Optional<Comment> findById(UUID id) {
		return commentRepo.findById(id);
	}

	@Override
	public Page<Comment> findByMovieId(UUID movieId, int page, int size, String sortBy, String sortDirection) {
		Sort sort = getSortDirection(sortBy, sortDirection);
		Pageable paging = PageRequest.of(page, size, sort);
				
		return commentRepo.findByMovieId(movieId, paging);
	}

	@Override
	public Comment update(CommentModel commentModel) {
		Comment comment = findById(commentModel.getId()).get();
		comment.setComments(commentModel.getComment());
		comment.setUpdatedDate(LocalDateTime.now());
		
		return commentRepo.save(comment);
	}

	@Override
	public void delete(UUID id) {
		if(findById(id).isPresent()) {
			commentRepo.delete(findById(id).get());
		}
	}
	
	private Sort getSortDirection(String sortBy, String direction) {
		return "asc".equals(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	}
}
