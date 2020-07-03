package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Category;
import com.example.demo.model.Menu;
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer>{
	@Query(value="SELECT e FROM Menu e WHERE e.url = :url")
	Optional<Menu> menuName(@Param("url") String  url);
}
