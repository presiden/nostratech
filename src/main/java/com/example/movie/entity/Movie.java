package com.example.movie.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Movie {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@NotNull
	private String title;

	@NotNull
	@Column(length = 1000)
	private String description;

	@NotNull
	private String director;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;

	@NotNull
	private int duration;

	@JsonIgnore
	private String moviePath;
	
	@JsonIgnore
	private String coverPath;
	
	@Transient
	private String cover;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private List<Genre> genre = new ArrayList<>();

	private double rating;

	@CreationTimestamp
	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
    
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "my_list_id", referencedColumnName = "id")
	@JsonIgnore
    private MyList myList;
}
