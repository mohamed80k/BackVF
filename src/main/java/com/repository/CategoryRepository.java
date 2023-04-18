package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Category;
import com.entity.Society;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public boolean existsByName(String name);
	
	public Category findByName(String name);
	
	public List<Category> findBySociety(Society society);
	
}
