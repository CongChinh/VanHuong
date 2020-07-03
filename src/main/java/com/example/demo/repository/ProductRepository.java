package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("SELECT e FROM Product e WHERE e.url_category = :url_category")
	List<Product> listProductByIdCategory(@Param("url_category") String url_category);
	
	@Query("SELECT e FROM Product e WHERE e.url = :url")
	Optional<Product> findProduct(@Param("url") String url);
	
	@Query("SELECT c,e FROM Product e, Category c WHERE e.id_category = c.id and e.url = :url")
	Optional<Product> proName(@Param("url") String url);
	
	@Query("SELECT e FROM Product e WHERE e.status like 'khuyenmai'")
	List<Product> listProductKhuyenMai();
	
	@Query("SELECT e FROM Product e WHERE e.status like 'moi'")
	List<Product> listProductMoi();
	
	@Query("SELECT e FROM Product e WHERE e.status like 'banchay'")
	List<Product> listProductBanChay();
	
	/*Tim kiem*/
	@Query("SELECT e FROM Product e WHERE e.url_category LIKE %?1% OR e.url LIKE %?1% OR e.code LIKE %?1%")
	List<Product> searchProduct(@Param("url") String url);
	
}
