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

import com.example.demo.model.Category;
import com.example.demo.model.Menu;
import com.example.demo.repository.CategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	private CategoryRepository cr;

	// lay danh sach
	@RequestMapping(value = { "/getListCategory" }, method = RequestMethod.GET)
	public ResponseEntity<List<Category>> findAllMenu() {
		List<Category> category = cr.findAll(Sort.by(Sort.Direction.ASC, "url"));
		if (category.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@RequestMapping(value = "/getListCategory/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id) {
		Optional<Category> category = cr.findById(id);

		if (!category.isPresent()) {
			return new ResponseEntity<>(category.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(category.get(), HttpStatus.OK);
	}

	// Create
	@RequestMapping(value = "/getListCategory", method = RequestMethod.POST)
	public ResponseEntity<Category> createCategory(@RequestBody Category category, UriComponentsBuilder builder) {
		cr.save(category);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/getListMenu/{id}").buildAndExpand(category.getId()).toUri());
		return new ResponseEntity<>(category, HttpStatus.CREATED);
	}

	// Update
	@RequestMapping(value = "/getListCategory/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
		Optional<Category> currentCategory = cr.findById(id);
		if (!currentCategory.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		currentCategory.get().setName(category.getName());
		currentCategory.get().setId_menu(category.getId_menu());
		currentCategory.get().setImage(category.getImage());

		cr.save(currentCategory.get());
		return new ResponseEntity<>(currentCategory.get(), HttpStatus.OK);
	}

	// Delete
	@RequestMapping(value = "/getListCategory/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") Integer id) {
		Optional<Category> category = cr.findById(id);
		if (!category.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		cr.delete(category.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
