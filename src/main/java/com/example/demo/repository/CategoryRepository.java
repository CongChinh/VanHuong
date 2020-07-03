package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Category;
import com.example.demo.model.Menu;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	/*@Query("SELECT e FROM Category e WHERE e.id_menu = :id")
	List<Category> listCategoryByIdMenu(@Param("id") Integer id);*/
	
	@Query("SELECT e FROM Category e WHERE e.url_menu = :url_menu")
	List<Category> listCategoryByIdMenu(@Param("url_menu") String url_menu);
	
	@Query("SELECT e FROM Category e WHERE e.url = :url")
	Optional<Category> cateName(@Param("url") String url);
}