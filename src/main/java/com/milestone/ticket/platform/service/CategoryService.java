package com.milestone.ticket.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milestone.ticket.platform.model.Category;
import com.milestone.ticket.platform.repository.CategoryRepository;


@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository repository;
	
	public List<Category> findAllCategories() {

		return repository.findAll();

	}
}