package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Commercial;
import com.entity.Site;

public interface SiteRepository extends JpaRepository<Site, Integer> {

	@Query("SELECT s FROM Site s WHERE s.society.id = :societyId AND s.expedition = true")
	public List<Site> findExpeditionBySociety(@Param("societyId") Integer societyId);

	@Query("SELECT s FROM Site s WHERE s.society.id = :societyId AND s.sale = true")
	public List<Site> findSaleBySociety(@Param("societyId") Integer societyId);

	@Query("SELECT s FROM Site s WHERE s.society.id = :societyId")
	public List<Site> findBySociety(@Param("societyId") Integer societyId);
	
	@Query("SELECT s FROM Site s WHERE s.society.id = :societyId and s.designation = :designation")
	public Site findBySocietyAndDesignation(@Param("societyId") Integer societyId, @Param("designation") String designation);

//	@Query("SELECT s FROM Site s WHERE s.society.id = :societyId AND s.expedition = true AND :commercial member of s.commercials")
//	public List<Site> findExpeditionBySocietyAndCommercial(@Param("societyId") Integer societyId,
//			@Param("commercial") Commercial commercial);
//
//	@Query("SELECT s FROM Site s WHERE s.society.id = :societyId AND s.sale = true AND :commercial member of s.commercials")
//	public List<Site> findSaleBySocietyAndCommercial(@Param("societyId") Integer societyId,
//			@Param("commercial") Commercial commercial);

}