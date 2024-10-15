package com.milestone.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milestone.ticket.platform.model.User;
import com.milestone.ticket.platform.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> findAllUsers() {

		return repository.findAll();

	}
	
	public List<User> findAllUsersStatusTrue(Boolean status) {
		
		return repository.findByStatus(status);

	}

	public User getById(int id) {

		return repository.findById(id).get();

	}

	public User update(User user) {

		return repository.save(user);

	}
	
	public void deleteUserById(int id) {
		repository.deleteById(id);
	}

	public User getByEmail(String userEmail) {
		return repository.findByEmail(userEmail).get();
	}
}
