package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.StateProject;
import com.entity.TypeTiers;

public interface StateProjectRepository extends JpaRepository<StateProject, Integer> {

	public StateProject findByName(String name);

	public boolean existsByName(String name);
}
