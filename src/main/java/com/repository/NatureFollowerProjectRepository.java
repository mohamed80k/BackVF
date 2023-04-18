package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.NatureFollowerProject;

public interface NatureFollowerProjectRepository extends JpaRepository<NatureFollowerProject, Integer>{

	public boolean existsByName(String name);
}
