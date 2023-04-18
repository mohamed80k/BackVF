package com.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dto.report.ProjectReportDto;
import com.dto.report.ProjectStateDto;
import com.entity.Commercial;
import com.entity.Project;
import com.entity.StateProject;
import com.entity.TypeProject;
import com.entity.type.StatusProject;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	public boolean existsByStateProject(StateProject stateProject);

	public boolean existsByTypeProject(TypeProject typeProject);

	@Query("SELECT p FROM Project p WHERE p.createAt BETWEEN :startDate AND :endDate")
	public List<Project> finAllByCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT p.name as projectName, tp.name as type, l.address, l.latitude, l.longitude, p.description, GROUP_CONCAT(DISTINCT tr.name SEPARATOR ',') AS client,"
			+ " GROUP_CONCAT(DISTINCT tpc.name SEPARATOR ',') AS typeClient, p.segment, GROUP_CONCAT(DISTINCT cm.name SEPARATOR ',') AS commercial, p.ca_bpe as caBpe, p.ca_ma as caMa, p.ca_pl as caPl, p.ca_pv_da as caPvDa, p.ca_estime as caEstime, "
			+ "p.status, sp.name stateProject, p.previous_status as previousStatus, p.closed_at as closedAt, p.create_at as createdAt "
			+ ", s.designation as site, lt.name as lotissement, GROUP_CONCAT(DISTINCT ds.das SEPARATOR ',') as das FROM project p JOIN state_project sp on p.project_state_id = sp.id "
			+ "join type_project tp on p.project_type_id = tp.id "
			+ "join locality l on p.locality_id = l.id LEFT JOIN lotissement lt ON p.project_lotissement_id = lt.id LEFT OUTER JOIN project_tiers prt "
			+ "ON p.id = prt.project_id LEFT OUTER JOIN tiers tr on prt.tiers_id = tr.id left join customer ct on tr.id = ct.tiers_id "
			+ "LEFT OUTER JOIN type_customer tpc on tpc.id = ct.customer_type_id LEFT OUTER JOIN project_commercial pcm on p.id = pcm.project_id LEFT OUTER JOIN "
			+ "commercial cm on pcm.commercial_id = cm.id left join site s on p.project_site_id = s.id "
			+ "Left outer join das ds on p.id = ds.id "
			+ "where p.create_at between :startDate and :endDate group by p.id", nativeQuery = true )
	public List<ProjectStateDto> findAllProjectsByCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);



	@Query("SELECT p FROM Project p INNER JOIN p.siteProject s WHERE p.stateProject.id in :projectStateIds AND s.id in :siteId")
	public List<Project> findAllProjectInSiteAndStateProject(@Param("siteId") List<Integer> siteId, @Param("projectStateIds") List<Integer> projectStateIds);
	
	@Query("SELECT p FROM Project p INNER JOIN p.siteProject s WHERE p.stateProject.id in :projectStateIds")
	public List<Project> findAllProjectInStateProject(@Param("projectStateIds") List<Integer> projectStateIds);
	
	@Query("SELECT p FROM Project p INNER JOIN p.siteProject s WHERE p.createAt BETWEEN :startDate "
			+ "AND :endDate AND s.id in :siteId")
	public List<Project> findInSiteProjectByCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("siteId") List<Integer> siteId);

	@Query(value = "SELECT p.name as projectName, tp.name as type, l.address, l.latitude, l.longitude, p.description, GROUP_CONCAT(DISTINCT tr.name SEPARATOR ',') AS client,"
			+ " GROUP_CONCAT(DISTINCT tpc.name SEPARATOR ',') AS typeClient, p.segment, GROUP_CONCAT(DISTINCT cm.name SEPARATOR ',') AS commercial, p.ca_bpe as caBpe, p.ca_ma as caMa, p.ca_pl as caPl, p.ca_pv_da as caPvDa, p.ca_estime as caEstime, "
			+ "p.status, sp.name stateProject, p.previous_status as previousStatus, p.closed_at as closedAt, p.create_at as createdAt "
			+ ", s.designation as site, lt.name as lotissement, GROUP_CONCAT(DISTINCT ds.das SEPARATOR ',') as das FROM project p JOIN state_project sp on p.project_state_id = sp.id "
			+ "join type_project tp on p.project_type_id = tp.id "
			+ "join locality l on p.locality_id = l.id LEFT JOIN lotissement lt ON p.project_lotissement_id = lt.id LEFT OUTER JOIN project_tiers prt "
			+ "ON p.id = prt.project_id LEFT OUTER JOIN tiers tr on prt.tiers_id = tr.id left join customer ct on tr.id = ct.tiers_id "
			+ "LEFT OUTER JOIN type_customer tpc on tpc.id = ct.customer_type_id LEFT OUTER JOIN project_commercial pcm on p.id = pcm.project_id LEFT OUTER JOIN "
			+ "commercial cm on pcm.commercial_id = cm.id join site s on p.project_site_id = s.id and s.id in :siteId "
			+ "Left outer join das ds on p.id = ds.id "
			+ "where p.create_at between :startDate and :endDate group by p.id", nativeQuery = true)
	public List<ProjectStateDto> findInSitesProjectOrderByCreatAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("siteId") List<Integer> siteId);

	
	@Query("SELECT p FROM Project p WHERE p.createAt BETWEEN :startDate "
			+ "AND :endDate AND p.status in :status")
	public List<Project> findInSatusBycreateAt(Date startDate, Date endDate,@Param("status") List<StatusProject> status);
	

	@Query(value = "SELECT p.name as projectName, tp.name as type, l.address, l.latitude, l.longitude, p.description, GROUP_CONCAT(DISTINCT tr.name SEPARATOR ',') AS client,"
			+ " GROUP_CONCAT(DISTINCT tpc.name SEPARATOR ',') AS typeClient, p.segment, GROUP_CONCAT(DISTINCT cm.name SEPARATOR ',') AS commercial, p.ca_bpe as caBpe, p.ca_ma as caMa, p.ca_pl as caPl, p.ca_pv_da as caPvDa, p.ca_estime as caEstime, "
			+ "p.status, sp.name stateProject, p.previous_status as previousStatus, p.closed_at as closedAt, p.create_at as createdAt "
			+ ", s.designation as site, lt.name as lotissement, GROUP_CONCAT(DISTINCT ds.das SEPARATOR ',') as das FROM project p JOIN state_project sp on p.project_state_id = sp.id "
			+ "join type_project tp on p.project_type_id = tp.id "
			+ "join locality l on p.locality_id = l.id LEFT JOIN lotissement lt ON p.project_lotissement_id = lt.id LEFT OUTER JOIN project_tiers prt "
			+ "ON p.id = prt.project_id LEFT OUTER JOIN tiers tr on prt.tiers_id = tr.id left join customer ct on tr.id = ct.tiers_id "
			+ "LEFT OUTER JOIN type_customer tpc on tpc.id = ct.customer_type_id LEFT OUTER JOIN project_commercial pcm on p.id = pcm.project_id LEFT OUTER JOIN "
			+ "commercial cm on pcm.commercial_id = cm.id left join site s on p.project_site_id = s.id "
			+ "Left outer join das ds on p.id = ds.id "
			+ "where p.create_at between :startDate and :endDate and p.status in :status group by p.id", nativeQuery = true)
	public List<ProjectStateDto> findInStatusProjectOrderByCreatAt(Date startDate, Date endDate,@Param("status") List<String> status);

	
	@Query("SELECT p FROM Project p INNER JOIN p.siteProject s WHERE p.createAt BETWEEN :startDate "
			+ "AND :endDate AND s.id in :siteId AND p.status in :status")
	public List<Project> findInSiteProjectAndByStatus(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("siteId") List<Integer> siteId, @Param("status") List<StatusProject> status);

	@Query(value = "SELECT p.name as projectName, tp.name as type, l.address, l.latitude, l.longitude, p.description, GROUP_CONCAT(DISTINCT tr.name SEPARATOR ',') AS client,"
			+ " GROUP_CONCAT(DISTINCT tpc.name SEPARATOR ',') AS typeClient, p.segment, GROUP_CONCAT(DISTINCT cm.name SEPARATOR ',') AS commercial, p.ca_bpe as caBpe, p.ca_ma as caMa, p.ca_pl as caPl, p.ca_pv_da as caPvDa, p.ca_estime as caEstime, "
			+ "p.status, sp.name stateProject, p.previous_status as previousStatus, p.closed_at as closedAt, p.create_at as createdAt "
			+ ", s.designation as site, lt.name as lotissement, GROUP_CONCAT(DISTINCT ds.das SEPARATOR ',') as das FROM project p JOIN state_project sp on p.project_state_id = sp.id "
			+ "join type_project tp on p.project_type_id = tp.id "
			+ "join locality l on p.locality_id = l.id LEFT JOIN lotissement lt ON p.project_lotissement_id = lt.id LEFT OUTER JOIN project_tiers prt "
			+ "ON p.id = prt.project_id LEFT OUTER JOIN tiers tr on prt.tiers_id = tr.id left join customer ct on tr.id = ct.tiers_id "
			+ "LEFT OUTER JOIN type_customer tpc on tpc.id = ct.customer_type_id LEFT OUTER JOIN project_commercial pcm on p.id = pcm.project_id LEFT OUTER JOIN "
			+ "commercial cm on pcm.commercial_id = cm.id join site s on p.project_site_id = s.id and s.id in :siteId "
			+ "Left outer join das ds on p.id = ds.id "
			+ "where p.create_at between :startDate and :endDate and p.status in :status group by p.id", nativeQuery = true)
	public List<ProjectStateDto> findInSiteAndStatusProject(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("siteId") List<Integer> siteId, @Param("status") List<String> status);

	
	@Query("SELECT p FROM Project p WHERE :commercial MEMBER OF p.commercials and p.stateProject.name != 'Ach' ")
	public List<Project> findByCommercial(@Param("commercial") Commercial commercial);

	public boolean existsByName(String name);

	@Query("SELECT p FROM Project p where (p.name like %:search%) order by p.createAt desc")
	public Page<Project> findAllByOrderByCreateAtDesc(Pageable pageable, @Param("search") String search);

	public List<Project> findAllByOrderByCreateAtDesc();
	
	@Query("SELECT p FROM Project p WHERE (SELECT COUNT(c.id) FROM p.commercials c WHERE c.id IN :commercialsId) > 0 and p.stateProject.name != 'Ach' ORDER BY p.createAt DESC")
	public List<Project> findByCommercials(@Param("commercialsId") Set<Integer> commercialsId);

	@Query(value = "SELECT count(p.id) as count, p.status as status, tp.name as projectTypeName, month(p.create_at) as month, year(p.create_at) as year"
			+ " FROM project p, type_project tp" + " WHERE p.create_at BETWEEN :startDate AND :endDate"
			+ " AND p.project_type_id  = tp.id"
			+ " GROUP BY month(p.create_at), year(p.create_at), p.status, p.project_type_id"
			+ " ORDER BY month(p.create_at) DESC , year(p.create_at) DESC", nativeQuery = true)
	public List<ProjectReportDto> getStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query(value = "SELECT  c.id as commercialId, c.name as commercialName, tp.name as projectTypeName, count(p.id) as count"
			+ " FROM commercial c, project p, project_commercial pc, type_project tp" + " WHERE p.id = pc.project_id"
			+ " AND p.project_type_id = tp.id" + " AND c.id = pc.commercial_id"
			+ " AND p.create_at BETWEEN :startDate AND :endDate" + " GROUP BY tp.id , c.id"
			+ " ORDER BY p.create_at DESC", nativeQuery = true)
	public List<ProjectReportDto> getStatisticsByCommercial(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
	
	@Query(value = "SELECT count(p.id) as count,tp.name as projectTypeName, s.id as siteId , s.designation as siteName"
			 + " FROM project p, site s, type_project tp WHERE p.project_site_id = s.id AND "
			 + " p.project_type_id = tp.id and p.create_at BETWEEN :startDate AND :endDate "
			 + " group by tp.id , s.id ORDER BY s.id, p.create_at DESC", nativeQuery = true)
	public List<ProjectReportDto> getStatisticsBySite(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT p FROM Project p WHERE p NOT IN (SELECT f.project FROM FollowerProject f WHERE f.commercial.id IN (:commercialsId) AND f.createAt BETWEEN :startDate AND :endDate)")
	public List<Project> getProjectsNotFollowedByCommercialsAndDateInterval(@Param("commercialsId") Set<Integer> commercialsId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
