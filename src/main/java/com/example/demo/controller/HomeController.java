package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Category;
import com.example.demo.model.Menu;
import com.example.demo.model.News;
import com.example.demo.model.NewsCategory;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.NewsCateRepository;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.ProductRepository;

@Controller
public class HomeController {
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private CategoryRepository cateRepository;
	
	@Autowired
	private ProductRepository proRepository;
	
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private NewsCateRepository newsCateRepository;
	
	@RequestMapping(value= {"/search"}, method=RequestMethod.GET)
	public ModelAndView search(@Param("keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		List<Product> search = proRepository.searchProduct(keyword.replaceAll("\\s+", "-"));
		mav.addObject("search", search);
		List<Menu> menuAll = menuRepository.findAll();
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("menuAll", menuAll);
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/search");
		return mav;
	}
	
	@RequestMapping(value= {"/","/index"}, method=RequestMethod.GET)
	public ModelAndView trangchu(Model model,@RequestParam(required = true, defaultValue = "0") int page ) {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		List<Category> cateAll = cateRepository.findAll();
		Page<Product> proAll = proRepository.findAll( new PageRequest(page, 12));
		List<Product> proKhuyenMai = proRepository.listProductKhuyenMai();
		List<Product> proMoi = proRepository.listProductMoi();
		List<Product> proBanChay = proRepository.listProductBanChay();
		mav.addObject("menuAll", menuAll);
		mav.addObject("cateAll", cateAll);
		mav.addObject("proAll", proAll);
		model.addAttribute("currentPage", page);
		//model.addAttribute("current", (proAll.getNumber()+4));
		model.addAttribute("begin", Math.max(0, page-2));
		model.addAttribute("end", Math.min(Math.max(0, page-2)+4, proAll.getTotalPages()));
		
		
		mav.addObject("proKhuyenMai", proKhuyenMai);
		mav.addObject("proMoi", proMoi);
		mav.addObject("proBanChay", proBanChay);
		mav.setViewName("index");
		return mav;
	}
	@RequestMapping(value= {"/list/{url_menu}.html"}, method=RequestMethod.GET)
	public ModelAndView trangchu1(@PathVariable String url_menu) {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		List<Category> listcategoryByIdMenu = cateRepository.listCategoryByIdMenu(url_menu);
		Optional<Menu> menuName = menuRepository.menuName(url_menu);
		mav.addObject("menuAll", menuAll);
		mav.addObject("listcategoryByIdMenu", listcategoryByIdMenu);
		mav.addObject("menuName", menuName.get());
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/list/list_category");
		return mav;
	}
	@RequestMapping(value= {"/list/{url_menu}/{url_category}.html"}, method=RequestMethod.GET)
	public ModelAndView trangchu2(@PathVariable String url_menu, @PathVariable String url_category) {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		List<Category> listcategoryByIdMenu = cateRepository.listCategoryByIdMenu(url_menu);
		List<Product> listProductByIdCategory = proRepository.listProductByIdCategory(url_category);
		
		Optional<Menu> menuName = menuRepository.menuName(url_menu);
		Optional<Category> cateName = cateRepository.cateName(url_category);
		
		mav.addObject("menuAll", menuAll);
		mav.addObject("listcategoryByIdMenu", listcategoryByIdMenu);
		mav.addObject("listProductByIdCategory", listProductByIdCategory);
		mav.addObject("menuName", menuName.get());
		mav.addObject("cateName", cateName.get());
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/list/list_product");
		return mav;
	}
	//Chi tiet san pham
	@RequestMapping(value= {"/detail/{url}.html"}, method=RequestMethod.GET)
	public ModelAndView trangchu3(@PathVariable String url) {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		Optional<Product> product = proRepository.findProduct(url);
		Optional<Product> proName = proRepository.proName(url);
		mav.addObject("menuAll", menuAll);
		mav.addObject("product", product.get());
		mav.addObject("proName", proName.get());
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/detail/detail");
		return mav;
	}
	@RequestMapping(value = {"/gioi-thieu.html"}, method = RequestMethod.GET)
	private ModelAndView gioithieu() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("menuAll", menuAll);
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/gioi-thieu");
		return mav;
	}
	@RequestMapping(value= {"/san-pham.html"}, method=RequestMethod.GET)
	public ModelAndView sanpham() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/san-pham");
		return mav;
	}
	@RequestMapping(value= {"/tin-tuc.html"}, method=RequestMethod.GET)
	public ModelAndView tintuc() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		List<News> newsAll = newsRepository.findAll();
		mav.addObject("newsAll", newsAll);
		mav.setViewName("web/tin-tuc");
		return mav;
	}
	@RequestMapping(value= {"/tin-tuc/{url}.html"}, method=RequestMethod.GET)
	public ModelAndView tintuc1(@PathVariable String url) {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		List<News> newsAll = newsRepository.findAll();
		mav.addObject("newsAll", newsAll);
		Optional<NewsCategory> newsCategory = newsCateRepository.newsCategory(url);
		mav.addObject("newsCategory", newsCategory.get());
		mav.setViewName("web/menu/tin_tuc_detail");
		return mav;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value= {"/khach-hang.html"}, method=RequestMethod.GET)
	public ModelAndView khachhang() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/khach-hang");
		return mav;
	}
	@RequestMapping(value= {"/nha-san-xuat.html"}, method=RequestMethod.GET)
	public ModelAndView nhasanxuat() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/nha-san-xuat");
		return mav;
	}
	@RequestMapping(value= {"/tuyen-dung.html"}, method=RequestMethod.GET)
	public ModelAndView tuyendung() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/tuyen-dung");
		return mav;
	}
	@RequestMapping(value= {"/dich-vu.html"}, method=RequestMethod.GET)
	public ModelAndView dichvu() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/dich-vu");
		return mav;
	}
	@RequestMapping(value= {"/lien-he.html"}, method=RequestMethod.GET)
	public ModelAndView lienhe() {
		ModelAndView mav = new ModelAndView();
		List<Menu> menuAll = menuRepository.findAll();
		mav.addObject("menuAll", menuAll);
		List<Product> proMoi = proRepository.listProductMoi();
		mav.addObject("proMoi", proMoi);
		mav.setViewName("web/lien-he");
		return mav;
	}
}
