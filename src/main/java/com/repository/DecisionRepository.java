package com.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Decision;

public interface DecisionRepository extends JpaRepository<Decision, Integer> {

	@Query("SELECT d FROM Decision d WHERE d.createAt BETWEEN :startDate AND :endDate")
	public List<Decision> finAllByCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT d FROM Decision d GROUP BY d.name")
	public List<Decision> findAllGroupByName();

	@Query("SELECT d FROM Decision d WHERE :commercialId = d.commercial.id")
	public List<Decision> findByCommercial(@Param("commercialId") int commercialId);
	
	@Query("SELECT d FROM Decision d WHERE (d.name like %:search% or d.commercial.name like %:search% or d.project.name like %:search%)")
	public Page<Decision> findAllAndNameContaining(Pageable pageable,@Param("search") String search);
}
