package com.example.movie.repository;

import java.util.Optional;
import java.util.UUID;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.movie.entity.Movie;
import com.example.movie.entity.MyList;
import com.example.movie.entity.Users;

@Repository
public interface MyListRepository extends JpaRepository<MyList, UUID>{

	@Query("select m from MyList m where m.users = :users and m.movie = :movie")
	Optional<MyList> findByUsersAndMovie(Users users, Movie movie);
}
