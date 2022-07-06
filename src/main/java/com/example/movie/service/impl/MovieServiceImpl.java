package com.example.movie.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MyList;
import com.example.movie.entity.Users;
import com.example.movie.model.MovieResponse;
import com.example.movie.model.MovieThumbnail;
import com.example.movie.repository.MovieRepository;
import com.example.movie.repository.UsersRepository;
import com.example.movie.service.MovieService;
import com.example.movie.service.MyListService;

import reactor.core.publisher.Mono;

@Service
public class MovieServiceImpl implements MovieService {

//    private static final String FORMAT = "classpath:videos/%s.mp4";
    private static final String FORMAT = "file:%s";
    private static final String COVER_DIR = "C:\\nostramovie\\cover\\";
    private static final String MOVIE_DIR = "C:\\nostramovie\\movie\\";
    
	@Autowired
	ModelMapper modelMapper;

    @Autowired
    private ResourceLoader resourceLoader;
    
	@Autowired
	MovieRepository movieRepo;
	
	@Autowired
	UsersRepository usersRepo;

	@Autowired
	MyListService myListService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Movie add(Movie movie, MultipartFile[] file) throws IOException {
		String coverExtension = org.springframework.util.StringUtils.getFilenameExtension(file[0].getOriginalFilename());
		String movieExtension = org.springframework.util.StringUtils.getFilenameExtension(file[1].getOriginalFilename());
		
		String generatedRandomString = generateRandomString();
		String coverFullName = generatedRandomString + "." + coverExtension;
		String movieFullName = generatedRandomString + "." + movieExtension;
		String coverFullPath = COVER_DIR + coverFullName;
		String movieFullPath = MOVIE_DIR + movieFullName;
		
		Files.createDirectories(Paths.get(COVER_DIR));
		Files.createDirectories(Paths.get(MOVIE_DIR));
		File coverDest = new File(coverFullPath);
		File videoDest = new File(movieFullPath);
		
		if(!coverDest.exists()) {
			file[0].transferTo(new File(coverFullPath));
		}
		
		if(!videoDest.exists()) {
			file[1].transferTo(new File(movieFullPath));
		}
		
		movie.setCoverPath(coverFullPath);
		movie.setMoviePath(movieFullPath);
		
		return movieRepo.save(movie);
	}
	
	@Override
	public Mono<Resource> getVideo(UUID movieId, UUID usersId) {
		Movie movie = movieRepo.findById(movieId).get();
		Users users = usersRepo.findById(usersId).get();
		MyList myList = new MyList(users, movie);
		myListService.add(myList);
		
	    return Mono.fromSupplier(() -> this.resourceLoader.getResource(String.format(FORMAT, movie.getMoviePath())));
	}

//	@Override
//	public byte[] getCover(UUID id) throws IOException {
//		String path = "cover/" + id + ".jpg";
//		ClassPathResource imgFile = new ClassPathResource(path);
//        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
//			    
//		return bytes;
//	}

	@Override
	public Page<MovieThumbnail> findAll(int page, int size, String sortBy, String sortDirection) throws IOException {
		Sort sort = getSortDirection(sortBy, sortDirection);
		Pageable paging = PageRequest.of(page, size, sort);
		Page<Movie> pageMovie = movieRepo.findAll(paging);
		
		List<Movie> listMovie = pageMovie.getContent();
		List<MovieThumbnail> listMovieThumbnail = modelMapper.map(listMovie, new TypeToken<List<MovieThumbnail>>() {}.getType());
		
		for(MovieThumbnail m: listMovieThumbnail) {
			m.setCover(encodeCover(m.getCoverPath()));
		}
		
		Page<MovieThumbnail> pageMovieThumbnail = new PageImpl<>(listMovieThumbnail, paging, pageMovie.getTotalElements());
		
		return pageMovieThumbnail;
	}
	
	@Override
	public Page<MovieThumbnail> findAllRecommend(int page, int size, String sortBy, String sortDirection) throws IOException {
		Sort sort = Sort.by("rating").descending().and(getSortDirection(sortBy, sortDirection));
		Pageable paging = PageRequest.of(page, size, sort);
		Page<Movie> pageMovie = movieRepo.findAll(paging);
		
		List<Movie> listMovie = pageMovie.getContent();
		List<MovieThumbnail> listMovieThumbnail = modelMapper.map(listMovie, new TypeToken<List<MovieThumbnail>>() {}.getType());
		
		for(MovieThumbnail m: listMovieThumbnail) {
			m.setCover(encodeCover(m.getCoverPath()));
		}
		
		Page<MovieThumbnail> pageMovieThumbnail = new PageImpl<>(listMovieThumbnail, paging, pageMovie.getTotalElements());
		
		return pageMovieThumbnail;
	}
	
	@Override
	public Page<MovieThumbnail> findAllNewMovie(int page, int size, String sortBy, String sortDirection) throws IOException {
		Sort sort = Sort.by("releaseDate").descending().and(getSortDirection(sortBy, sortDirection));
		Pageable paging = PageRequest.of(page, size, sort);
		Page<Movie> pageMovie = movieRepo.findAll(paging);
		
		List<Movie> listMovie = pageMovie.getContent();
		List<MovieThumbnail> listMovieThumbnail = modelMapper.map(listMovie, new TypeToken<List<MovieThumbnail>>() {}.getType());
		
		for(MovieThumbnail m: listMovieThumbnail) {
			m.setCover(encodeCover(m.getCoverPath()));
		}
		
		Page<MovieThumbnail> pageMovieThumbnail = new PageImpl<>(listMovieThumbnail, paging, pageMovie.getTotalElements());
		
		return pageMovieThumbnail;
	}

	@Override
	public Optional<Movie> findById(UUID id) throws IOException {
		Movie movie = movieRepo.findById(id).get();
		movie.setCover(encodeCover(movie.getCoverPath()));
        
		return Optional.of(movie);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Movie update(Movie movie) {
		movie.setUpdatedDate(LocalDateTime.now());
		return movieRepo.save(movie);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(UUID id) throws IOException {
		if(findById(id).isPresent()) {
			movieRepo.delete(findById(id).get());
		}
	}

	@Override
	public Page<MovieThumbnail> findByTitle(String title, int page, int size) throws IOException {
		title = StringUtils.defaultString(title);
		Pageable paging = PageRequest.of(page, size);
		Page<Movie> movieList = movieRepo.findByTitleContainingIgnoreCase(title, paging);
		List<MovieThumbnail> movieThumbnailList = new ArrayList<>();
		
		for(Movie movie: movieList.getContent()) {
			MovieThumbnail movieThumbnail = new MovieThumbnail();
			movieThumbnail.setTitle(movie.getTitle());
			movieThumbnail.setDirector(movie.getDirector());
			movieThumbnail.setCover(encodeCover(movie.getCoverPath()));
			movieThumbnailList.add(movieThumbnail);
		}
		Page<MovieThumbnail> result = new PageImpl<>(movieThumbnailList, paging, movieList.getTotalElements());
		
		return result;
	}
	
	private String encodeCover(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
        String encoded = Base64.getEncoder().encodeToString(bytes);
        
        return encoded;
	}
	
	private String generateRandomString() {
		int length = 32;
	    boolean useLetters = true;
	    boolean useNumbers = true;
	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

	    return generatedString;
	}
	
	private Sort getSortDirection(String sortBy, String direction) {
		return "asc".equals(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
	}
}
