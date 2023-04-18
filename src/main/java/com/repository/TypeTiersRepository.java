package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.TypeTiers;

public interface TypeTiersRepository extends JpaRepository<TypeTiers, Integer> {
	
	public TypeTiers findByName(String name);

	public boolean existsByName(String name);

}
