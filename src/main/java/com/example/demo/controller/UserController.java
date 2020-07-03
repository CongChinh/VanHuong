package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/dang_ky" }, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject("user", user);
		model.setViewName("web/dang_ky");

		return model;
	}

	@RequestMapping(value = { "/dang_ky" }, method = RequestMethod.POST)
	public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());

		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "This email already exists!");
		}
		if (bindingResult.hasErrors()) {
			model.setViewName("web/dang_ky");
		} else {
			userService.saveUser(user);
			model.addObject("msg", "User has been registered successfully!");
			model.addObject("user", new User());
			model.setViewName("web/dang_ky");
		}

		return model;
	}

	@RequestMapping(value = { "/admin/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		model.addObject("userName", user.getFirstname() + " " + user.getLastname());
		model.setViewName("admin/home");
		return model;
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView dangnhap() {
		ModelAndView model = new ModelAndView();
		model.setViewName("web/login");
		return model;
	}

	@RequestMapping(value = { "/404" }, method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/404");
		return model;
	}

	/*
	 * public static Logger logger = LoggerFactory.getLogger(UserController.class);
	 * 
	 * @Autowired UserRepository userRepository;
	 * 
	 * @RequestMapping(value = "/user_getData", method = RequestMethod.GET)//Lay
	 * danh sach tat ca public ResponseEntity<List<User>> listAllUser(){ List<User>
	 * listUser = userRepository.findAll(); if(listUser.isEmpty()) { return new
	 * ResponseEntity<List<User>>(HttpStatus.NO_CONTENT); } return new
	 * ResponseEntity<List<User>>(listUser, HttpStatus.OK); }
	 * 
	 * @RequestMapping(value = "/user_getData/{id}", method = RequestMethod.GET)
	 * public User findUser(@PathVariable("id") long id) { User user =
	 * userRepository.getOne(id);//getOne if(user == null) {
	 * ResponseEntity.notFound().build(); } return user; }
	 * 
	 * @RequestMapping(value = "/admin/user/", method = RequestMethod.POST)//Tao moi
	 * public User saveUser(@Valid @RequestBody User user) { return
	 * userRepository.save(user); }
	 * 
	 * @RequestMapping(value = "/admin/user/{id}", method =
	 * RequestMethod.PUT)//update public ResponseEntity<User>
	 * updateUser(@PathVariable(value = "id") Long contactId,
	 * 
	 * @Valid @RequestBody User userForm) { User user =
	 * userRepository.getOne(contactId); if(user == null) { return
	 * ResponseEntity.notFound().build(); }
	 * user.setFirstName(userForm.getFirstName());
	 * user.setLastName(userForm.getLastName()); user.setEmail(userForm.getEmail());
	 * user.setPassword(userForm.getPassword());
	 * 
	 * User updatedUser = userRepository.save(user); return
	 * ResponseEntity.ok(updatedUser); }
	 * 
	 * @RequestMapping(value = "/admin/user/{id}", method =
	 * RequestMethod.DELETE)//xoa public ResponseEntity<User>
	 * deleteUser(@PathVariable(value = "id") Long id) { User user =
	 * userRepository.getOne(id); if(user == null) { return
	 * ResponseEntity.notFound().build(); } userRepository.delete(user); return
	 * ResponseEntity.ok().build(); }
	 */
}
