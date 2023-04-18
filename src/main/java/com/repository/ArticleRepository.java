package com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Article;
import com.entity.Category;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	public boolean existsByCategory(Category category);

	public boolean existsByReference(String reference);

	@Query("SELECT a FROM Article a WHERE a.category.society.id = :societyId")
	public Page<Article> findBySociety(@Param("societyId") Integer societyId, Pageable page);

	@Query("SELECT a FROM Article a WHERE a.category.society.id = :societyId")
	public List<Article> findBySociety(@Param("societyId") Integer societyId);

	@Query("SELECT a FROM Article a WHERE a.category.id = :categoryId")
	public List<Article> findByCategory(@Param("categoryId") Integer categoryId);

	public Article findByReference(String reference);
}
