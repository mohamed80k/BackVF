package com.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Role;
import com.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public boolean existsByUsername(String username);

	public User findByUsername(String username);

	@Query("SELECT u.roles FROM User u WHERE u.username = :username")
	public Set<Role> findRolesByUsername(@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE u.manager.id = :managerId")
	public List<User> getListByManager(@Param("managerId") Integer managerId);
	
}
