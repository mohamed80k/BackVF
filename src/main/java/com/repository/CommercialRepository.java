package com.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Commercial;

public interface CommercialRepository extends JpaRepository<Commercial, Integer> {

//	@Query("SELECT c FROM Commercial c WHERE :societyId IN (SELECT s.society.id FROM c.sites s) and (c.name like %:search% )")
//	public Page<Commercial> findBySociety(@Param("societyId") Integer societyId, Pageable page,@Param("search") String search);
	
//	@Query("SELECT c FROM Commercial c WHERE :societyId IN (SELECT s.society.id FROM c.sites s)")
//	public List<Commercial> findBySociety(@Param("societyId") Integer societyId);
	
//	@Query("SELECT c FROM Commercial c WHERE :societyId IN (SELECT s.society.id FROM c.sites s) AND c.id IN :commercialsId")
//	public List<Commercial> findBySocietyAndCommercials(@Param("societyId") Integer societyId, @Param("commercialsId") Set<Integer> commercialsId);
	
	public boolean existsByName(String name);
	
	public Commercial findByName(String name);
	
	@Query("SELECT c FROM Commercial c WHERE c.id IN :commercialsId")
	public List<Commercial> findByCommercials(@Param("commercialsId") Set<Integer> commercialsId);

}
