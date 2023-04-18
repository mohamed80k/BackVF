package com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dto.LoginInfoDto;
import com.entity.Login;

public interface LoginRepository extends JpaRepository<Login, Integer> {
	@Query(value = "SELECT NEW com.dto.LoginInfoDto(l.id, l.loginAt) FROM Login l WHERE l.user.id = :userId ORDER by l.id DESC")
	public Page<LoginInfoDto> findLoginByUser(@Param("userId") Integer userId, Pageable page);

}
