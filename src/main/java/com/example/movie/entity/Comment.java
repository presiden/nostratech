package com.example.movie.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
	@JsonIgnore
    private Movie movie;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonIgnore
    private Users users;
    
    @NotNull
    private String comments;
    
    @CreationTimestamp
    private LocalDateTime createdDate;
    
    private LocalDateTime updatedDate;
}
