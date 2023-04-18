package com.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dto.report.StatisticInfoDto;
import com.entity.Tiers;
import com.entity.TypeTiers;

public interface TiersRepository<T extends Tiers> extends JpaRepository<T, Integer> {

	public Page<T> findAll(Pageable pageable);

	public boolean existsByTypeTiers(TypeTiers typeTiers);

	public boolean existsByName(String name);

	@Query("SELECT t FROM Tiers t WHERE t.typeTiers.name IN :typesTiers")
	public List<Tiers> findByTypes(@Param("typesTiers") List<String> typesTiers);
	
	@Query("SELECT t FROM Tiers t WHERE t.typeTiers.name IN :typesTiers AND (SELECT COUNT(c.id) FROM t.commercials c WHERE c.id IN :commercialsId) > 0")
	public List<Tiers> findByTypesAndCommercials(@Param("typesTiers") List<String> typesTiers, @Param("commercialsId") Set<Integer> commercialsId);
	

	@Query("SELECT t FROM Tiers t WHERE (t.typeTiers.name IN :typesTiers) AND (:commercialId IN (SELECT c.id FROM t.commercials c))")
	public List<Tiers> findByTypesAndCommercial(@Param("typesTiers") List<String> typesTiers,
			@Param("commercialId") Integer commercialId);

	@Query("SELECT t FROM Tiers t WHERE :commercialId IN (SELECT c.id FROM t.commercials c)")
	public List<Tiers> findByCommercial(@Param("commercialId") Integer commercialId);

	@Query("SELECT t FROM Tiers t WHERE t.id = :id AND t.typeTiers.name IN :typesTiers")
	public Tiers findByIdAndTypes(@Param("id") Integer id, @Param("typesTiers") List<String> typesTiers);
	
	@Query("SELECT t FROM Tiers t WHERE t.name = :name AND t.typeTiers.name IN :typesTiers")
	public Tiers findByNameAndTypes(@Param("name") String name, @Param("typesTiers") List<String> typesTiers);

	@Query(value = "SELECT tt.name as name ,count(t.id) as value FROM tiers t, type_tiers tt WHERE tt.id = t.tiers_type_id AND t.create_at BETWEEN :startDate AND :endDate GROUP BY tt.id;", nativeQuery = true)
	public List<StatisticInfoDto> getStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT t FROM Tiers t where (t.name like %:search% or t.cin like %:search% or t.typeTiers.name like %:search%) order by t.createAt desc")
	public Page<Tiers> findAllByOrderByCreateAtDesc(Pageable pageable, @Param("search") String search);
 
	@Query("SELECT t FROM Tiers t WHERE (SELECT COUNT(c.id) FROM t.commercials c WHERE c.id IN :commercialsId) > 0 ORDER BY t.createAt DESC")
	public List<Tiers> findByCommercials(@Param("commercialsId") Set<Integer> commercialsId);


	@Query("select t from Tiers t where t.typeTiers.id = 18 and t.name like %:search% order by t.createAt desc")
	public Page<Tiers> findEventsTiers(Pageable pageable, @Param("search") String search);

	
}
