package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController {
	/*@RequestMapping("/admin")
	private String admin() {
		return "admin/home";
	}*/
	@RequestMapping("/admin/login")
	private String adminLogin() {
		return "admin/login";
	}
	@RequestMapping("/admin/register")
	private String adminRegister() {
		return "admin/register";
	}
	@RequestMapping("/admin/forgot-password")
	private String adminForgot_password() {
		return "admin/forgot-password";
	}
	@RequestMapping("/admin/404")
	private String admin404() {
		return "admin/404";
	}
	@RequestMapping("/admin/blank")
	private String adminBlank() {
		return "admin/blank";
	}
	@RequestMapping("/admin/charts")
	private String adminCharts() {
		return "admin/charts";
	}
	@RequestMapping("/admin/user")
	private String adminTables() {
		return "admin/user";
	}
	
	@RequestMapping("/admin/menu")
	private String adminMenu() {
		return "admin/menu";
	}
	@RequestMapping("/admin/category")
	private String adminCategory() {
		return "admin/category";
	}
	@RequestMapping("/admin/product")
	private String adminProduct() {
		return "admin/product";
	}
}
