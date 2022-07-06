package com.example.movie.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.movie.entity.MyList;

@Service
public interface MyListService {

	MyList add(MyList myList);
	
	List<MyList> findAll();
	
	MyList findById(UUID id);
	
	MyList update(MyList myList);
	
	void delete(MyList myList);
}
