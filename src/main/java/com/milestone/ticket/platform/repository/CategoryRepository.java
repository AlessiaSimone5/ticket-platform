package com.milestone.ticket.platform.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.milestone.ticket.platform.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>{

}