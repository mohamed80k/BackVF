package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.CustomerAccount;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Integer> {

	public boolean existsByReference(String reference);

}
