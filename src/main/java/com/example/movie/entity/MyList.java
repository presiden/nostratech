package com.example.movie.entity;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class MyList {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users users;
    
    @OneToOne
	@JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;
    
    @CreationTimestamp
    private LocalDate watchDate;

	public MyList() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public MyList(Users users, Movie movie) {
    	this.users = users;
    	this.movie = movie;
    }
}
