package com.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dto.report.NumberOfVisitsReportDto;
import com.entity.FollowerProject;
import com.entity.NatureFollowerProject;

public interface FollowerProjectRepository extends JpaRepository<FollowerProject, Integer> {

	@Query(nativeQuery=true, value="SELECT p.id as projectId, p.name as projectName, t.name as projectTypeName, p.description as projectDescription, l.address as projectAddress, l.latitude as projectLatitude, l.longitude as projectLongitude, c.id as commercialId, c.name as commercialName, COUNT(f.id) as numberOFVisits, min(f.create_at) as followedFist, max(f.create_at) as followedLast " + 
			",(SELECT tr.name FROM tiers tr WHERE tr.id = f.customer_id) as customerName " +
			",(SELECT tc.name FROM type_customer tc, customer cr WHERE tc.id = cr.customer_type_id AND cr.tiers_id = f.customer_id) as customerTypeName " +
			"FROM follower_project f, project p, type_project t, commercial c, locality l " + 
			"WHERE f.create_at BETWEEN :startDate AND :endDate " + 
			"and p.id = f.project_id " + 
			"and c.id = f.commercial_id " + 
			"and p.project_type_id = t.id " +
			"and p.locality_id = l.id " +
			"GROUP BY f.project_id, f.commercial_id")
	public List<NumberOfVisitsReportDto> getProjectFollowerStatistics(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	public List<FollowerProject> findByCommercialId(@Param("commercialId") int commercialId);
	
	public List<FollowerProject> findByCreateAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
 
	@Query("SELECT f FROM FollowerProject f where (f.object like %:search% or f.commercial.name like %:search% or f.project.name like %:search% or f.natureFollower.name like %:search%) order by f.createAt desc")
	public Page<FollowerProject> findAllByOrderByCreateAtDesc(Pageable pageable, @Param("search") String search);
	
	public boolean existsByNatureFollower(NatureFollowerProject natureFollower);
 
}
