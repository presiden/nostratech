package com.example.movie.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movie.entity.MyList;
import com.example.movie.repository.MyListRepository;
import com.example.movie.service.MyListService;

@Service
public class MyListServiceImpl implements MyListService {

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	MyListRepository myListRepo;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public MyList add(MyList myList) {
		boolean isPresent = myListRepo.findByUsersAndMovie(myList.getUsers(), myList.getMovie()).isPresent();
		if(isPresent) {
			return null;
		}
		return myListRepo.save(myList);
	}

	@Override
	public List<MyList> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyList findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MyList update(MyList myList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(MyList myList) {
		// TODO Auto-generated method stub
		
	}

}
