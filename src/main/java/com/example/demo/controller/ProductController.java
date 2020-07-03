package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Controller
public class ProductController {
	@Autowired
	private ProductRepository pr;

	// lay danh sach
	@RequestMapping(value = { "/getListProduct" }, method = RequestMethod.GET)
	public ResponseEntity<List<Product>> findAllProduct() {
		List<Product> Product = pr.findAll(Sort.by(Sort.Direction.ASC, "url"));
		if (Product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(Product, HttpStatus.OK);
	}

	@RequestMapping(value = "/getListProduct/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id) {
		Optional<Product> Product = pr.findById(id);

		if (!Product.isPresent()) {
			return new ResponseEntity<>(Product.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(Product.get(), HttpStatus.OK);
	}

	// Create
	@RequestMapping(value = "/getListProduct", method = RequestMethod.POST)
	public ResponseEntity<Product> createProduct(@RequestBody Product Product, UriComponentsBuilder builder) {
		pr.save(Product);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/getListProduct/{id}").buildAndExpand(Product.getId()).toUri());
		return new ResponseEntity<>(Product, HttpStatus.CREATED);
	}

	// Update
	@RequestMapping(value = "/getListProduct/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product Product) {
		Optional<Product> currentProduct = pr.findById(id);
		if (!currentProduct.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		currentProduct.get().setName(Product.getName());
		currentProduct.get().setId_category(Product.getId_category());
		currentProduct.get().setImage(Product.getImage());
		currentProduct.get().setCode(Product.getCode());
		currentProduct.get().setDescription(Product.getDescription());
		currentProduct.get().setActive(Product.getActive());

		pr.save(currentProduct.get());
		return new ResponseEntity<>(currentProduct.get(), HttpStatus.OK);
	}

	// Delete
	@RequestMapping(value = "/getListProduct/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Integer id) {
		Optional<Product> Product = pr.findById(id);
		if (!Product.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		pr.delete(Product.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
