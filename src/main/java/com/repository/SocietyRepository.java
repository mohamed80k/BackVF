package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Society;

public interface SocietyRepository extends JpaRepository<Society, Integer> {

	Society findByName(String name);
	public boolean existsByName(String n);

}
