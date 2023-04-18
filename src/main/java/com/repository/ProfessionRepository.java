package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Profession;

public interface ProfessionRepository extends JpaRepository<Profession, Integer> {

	public boolean existsByName(String name);
}
