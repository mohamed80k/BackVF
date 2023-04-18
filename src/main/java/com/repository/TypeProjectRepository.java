package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.TypeProject;
import com.entity.TypeTiers;

public interface TypeProjectRepository extends JpaRepository<TypeProject, Integer>  {
	public TypeProject findByName(String name);

	public boolean existsByName(String name);
}
