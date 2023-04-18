package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.PointInterest;
import com.entity.type.PointInterestType;
 

public interface PointInteretRepository extends JpaRepository<PointInterest, Long> {

	@Query
	public List<PointInterest> findByType(PointInterestType polygon);

	@Query( "SELECT p FROM PointInterest p WHERE idPointInterest IN :pointOfInterests order by p.idPointInterest asc" )
	List<PointInterest> findByIds(@Param("pointOfInterests") List<Long> pointOfInterests);

}
