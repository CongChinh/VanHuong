package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.NewsCategory;

public interface NewsCateRepository extends JpaRepository<NewsCategory, Integer>{
	@Query(value="SELECT e FROM NewsCategory e WHERE e.url = :url")
	Optional<NewsCategory> newsCategory(@Param("url") String  url);
}
