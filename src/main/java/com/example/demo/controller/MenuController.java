package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Menu;
import com.example.demo.repository.MenuRepository;

@Controller
public class MenuController {

	@Autowired
	private MenuRepository cr;

	// lay danh sach
	@RequestMapping(value = { "/getListMenu" }, method = RequestMethod.GET)
	public ResponseEntity<List<Menu>> findAllMenu() {
		List<Menu> Menu = cr.findAll();
		if (Menu.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(Menu, HttpStatus.OK);
	}

	@RequestMapping(value = "/getListMenu/{id}", method = RequestMethod.GET)
	public ResponseEntity<Menu> getMenuById(@PathVariable("id") Integer id) {
		Optional<Menu> Menu = cr.findById(id);

		if (!Menu.isPresent()) {
			return new ResponseEntity<>(Menu.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(Menu.get(), HttpStatus.OK);
	}

	// Create
	@RequestMapping(value = "/getListMenu", method = RequestMethod.POST)
	public ResponseEntity<Menu> createMenu(@RequestBody Menu Menu, UriComponentsBuilder builder) {
		cr.save(Menu);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/getListMenu/{id}").buildAndExpand(Menu.getId()).toUri());
		return new ResponseEntity<>(Menu, HttpStatus.CREATED);
	}

	// Update
	@RequestMapping(value = "/getListMenu/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Menu> updateMenu(@PathVariable("id") Integer id, @RequestBody Menu Menu) {
		Optional<Menu> currentMenu = cr.findById(id);
		if (!currentMenu.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		currentMenu.get().setName(Menu.getName());

		cr.save(currentMenu.get());
		return new ResponseEntity<>(currentMenu.get(), HttpStatus.OK);
	}

	// Delete
	@RequestMapping(value = "/getListMenu/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Menu> deleteMenu(@PathVariable("id") Integer id) {
		Optional<Menu> Menu = cr.findById(id);
		if (!Menu.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		cr.delete(Menu.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
