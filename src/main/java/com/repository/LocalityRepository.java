package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Locality;

public interface LocalityRepository extends JpaRepository<Locality, Integer> {

}
