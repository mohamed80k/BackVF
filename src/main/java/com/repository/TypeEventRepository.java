package com.repository;

import com.entity.TypeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeEventRepository extends JpaRepository<TypeEvent, Integer> {

    public TypeEvent findByName(String name);

    public boolean existsByName(String name);
}
