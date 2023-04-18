package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.TypeCustomer;

public interface TypeCustomerRepository extends JpaRepository<TypeCustomer, Integer> {

	public TypeCustomer findByName(String name);

	public boolean existsByName(String name);
}
