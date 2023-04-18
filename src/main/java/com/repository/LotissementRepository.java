package com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Lotissement;

public interface LotissementRepository extends JpaRepository<Lotissement, Integer> {

	public boolean existsByName(String name);

	@Query("SELECT l FROM Lotissement l where (l.name like %:search%)")
	public Page<Lotissement> findAllLotissements(Pageable pageable, @Param("search") String search);

	public Lotissement findByName(String name);


}
