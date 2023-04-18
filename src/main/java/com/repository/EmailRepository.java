package com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Email;
import com.entity.Society;

public interface EmailRepository extends JpaRepository<Email, Integer> {

	@Query("SELECT e FROM Email e WHERE e.site.society = :society")
	public Page<Email> findBySociety(@Param("society") Society society, Pageable page);
}
