/**
 *
 */
function showData() {
	$.ajax({
		url: "/user_getData",
		method: "GET",
		type: "JSON",
		success: function (data) {
			//console.log(data);
			$.each(data, function (i, item) {
				var record = "<tr><td>" + (i++) + "</td><td>" + item.firstName
					+ "</td><td>" + item.lastName + "</td></tr>";
				$("#data").append(record);
			});
		}
	});
}

$(document).ready(function () {
	function load() {
		$.ajax({ //create an ajax request to load_page.php
			method: "GET",
			url: "/user_getData",
			type: "JSON", //expect html to be returned
			success: function (data) {
				//console.log(data);
				$.each(data, function (i, item) {
					var record = "<tr ><td>" + (i++) + "</td>"
						+ "<td>" + item.id + "</td>"
						+ "<td>" + item.firstName + "</td>"
						+ "<td>" + item.lastName + "</td>"
						+ "<td>" + item.email + "</td>"
						+ "<td>" + item.password + "</td></tr>";
					$(".userDisplay").append(record);
				});
			}
		});
	}
	load();
});


//--------------------MENU----------------------------
showListMenu();
function showListMenu() {
	$(".showListMenu").empty();
	$.ajax({
		url: "/getListMenu",
		method: "GET",
		type: "JSON",
		success: function (data) {
			$.each(data, function (i, item) {
				var record = 
					"<tr><td>" + item.id + 
					"</td><td>" + item.name + 
					"</td><td>" + '<img src="@{"/images/" + ${'+item.image+'}">' + 
					'</td><td><button class="btn btn-dark" onclick="getOneMenu(' + item.id + ')" data-toggle="modal" data-target="#updateMenuModal">Update</td>' +
					'<td><button class="btn btn-danger" onclick="deleteMenu(' + item.id + ')">Delete</td></tr>';
				var record_array = "<option value="+item.id+">"+item.name+"</option>";
				$(".showMenuArray").append(record_array);
				$(".showListMenu").append(record);
			});
		}
	});
}
function resetDataMenu() {
	$("#menu_name").val("");
}
$(document).ready(function () {
	$("#createMenuForm").submit(function (event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		createMenu();
	})
});
function createMenu() {
	var name = $("#menu_name").val();
	var image = $("#menu_image").val();
	var url = $("#menu_url").val();
	$.ajax({
		method: "POST",
		url: "/getListMenu",
		contentType: "application/json",
		dataType: "JSON",
		data: JSON.stringify({'name': name,'image':image, 'url':url}),
		success: function (data) {
			showListMenu();
			$('#addSuccessMenu').fadeIn(2000, function () {
				$('#addSuccessMenu').fadeOut(2000);
			});
		}
	})
}
function getOneMenu(id) {
	$.ajax({
		method: "GET",
		url: "/getListMenu/" + id,
		dataType: "JSON",
		success: function (data) {
			$('#updateMenu_id').val(data.id);
			$('#updateMenu_name').val(data.name);
			$('#updateMenu_image').val(data.image);
			$('#updateMenu_url').val(data.url);
		}
	})
}
function updateMenu() {
	var menu_id= $("#updateMenu_id").val();
	var menu_name= $("#updateMenu_name").val();
	var menu_image= $("#updateMenu_image").val();
	$.ajax({
		method: "PUT",
		url: "/getListMenu/" + menu_id,
		contentType: "application/json",
		dataType: "JSON",
		data: JSON.stringify({"name":menu_name, 'image':menu_image}),
		success: function () {
			showListMenu();
			$('#updateSuccessMenu').fadeIn(2000, function () {
				$('#updateSuccessMenu').fadeOut(2000);
			});
		}
	})
}
//Delete
function deleteMenu(id) {
	$.ajax({
		method: "DELETE",
		url: "/getListMenu/" + id,
		success: function () {
			showListMenu();
			$('#deleteSuccessMenu').fadeIn(2000, function () {
				$('#deleteSuccessMenu').fadeOut(2000);
			});
		}
	})
}
//---------------------------------------------CATEGORY--------------------------------------------------------
showListCategory();
function showListCategory() {
	$(".showListCategory").empty();
	$.ajax({
		url: "/getListCategory",
		method: "GET",
		type: "JSON",
		success: function (data) {
			$.each(data, function (i, item) {
				var record = 
					"<tr><td>" + item.id + 
					"</td><td>" + item.name + 
					"</td><td>" + item.id_menu +
					"</td><td>" + item.url_menu +
					'</td><td><button class="btn btn-dark" onclick="getOneCategory(' + item.id + ')" data-toggle="modal" data-target="#updateCategoryModal">Update</td>' +
					'<td><button class="btn btn-danger" onclick="deleteCategory(' + item.id + ')">Delete</td></tr>';
				var record_array = "<option value="+item.id+">"+item.url_menu+"</option>";
				$(".showCategoryArray").append(record_array);
				$(".showListCategory").append(record);
			});
		}
	});
}


//Create - JQuery Submit
$(document).ready(function () {
	$("#createCategoryForm").submit(function (event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		createCategory();
	})
	$("#updateCategoryForm").submit(function (event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		updateCategory(id);
	})
});
function resetData() {
	$("#category_name").val("");
}
function createCategory() {
	var	menu_id=$("#menu_id").val();
	var	category_name= $("#category_name").val();
	var	category_image= $("#category_image").val();
	var	category_url= $("#category_url").val();
	var	category_url_menu= $("#category_url_menu").val();
	$.ajax({
		method: "POST",
		url: "/getListCategory",
		contentType: "application/json",
		dataType: "JSON",
		data: JSON.stringify({'id_menu':menu_id, 'name':category_name, 'image':category_image, 'url':category_url, 'url_menu':category_url_menu}),
		success: function () {
			showListCategory();
			$('#addSuccessCategory').fadeIn(2000, function () {
				$('#addSuccessCategory').fadeOut(2000);
			});
		}
	})
	//resetData();
}

//Update Category
function getOneCategory(id) {
	$.ajax({ 
		method: "GET",
		url: "/getListCategory/" + id,
		dataType: "JSON",
		success: function (data) {
			$('#updatecategory_id').val(data.id);
			$('#updatecategory_idMenu').val(data.id_menu);
			$('#updatecategory_name').val(data.name);
			$('#updatecategory_image').val(data.image);
			$('#updatecategory_url').val(data.url);
			$('#updatecategory_url_menu').val(data.url_menu);
		}
	})
}
function updateCategory() {
	var	category_id= $("#updatecategory_id").val();
	var	category_idMenu= $("#updatecategory_idMenu").val();
	var	category_name= $("#updatecategory_name").val();
	var	category_image= $("#updatecategory_image").val();
	var category_url = $("#updatecategory_url").val();
	var category_url_menu = $("#updatecategory_url_menu").val();
	$.ajax({
		method: "PUT",
		url: "/getListCategory/" + category_id,
		contentType: "application/json",
		dataType: "JSON",
		data: JSON.stringify({'id': category_id, 'id_menu':category_idMenu, 'name':category_name, 'image':category_image, 'url':category_url, 'url_menu':category_url_menu}),
		success: function () {
			console.log("Updated success");
			showListCategory();
			$('#updateSuccessCategory').fadeIn(2000, function () {
				$('#updateSuccessCategory').fadeOut(2000);
			});
		}
	})
}
//Delete
function deleteCategory(id) {
	$.ajax({
		method: "DELETE",
		url: "/getListCategory/" + id,
		success: function () {
			showListCategory();
			$('#deleteSuccessCategory').fadeIn(2000, function () {
				$('#deleteSuccessCategory').fadeOut(2000);
			});
			console.log("Deleted success");
		}
	})
}
//-------------------------------PRODUCT------------------------------------
showListProduct();
function showListProduct() {
	$(".showListProduct").empty();
	$.ajax({
		url: "/getListProduct",
		method: "GET",
		type: "JSON",
		success: function (data) {
			$.each(data, function (i, item) {
				var record = 
					"<tr><td>" + item.id + 
					"</td><td>" + item.name + 
					"</td><td>" + item.id_category + 
					"</td><td>" + item.image +
					"</td><td>" + item.url +
					"</td><td>" + item.url_category +
					"</td><td>" + item.code + 
					"</td><td>" + item.description + 
					"</td><td>" + item.active + 
					
					"</td><td>" + item.status +
					'</td><td><button class="btn btn-dark" onclick="getOneProduct(' + item.id + ')" data-toggle="modal" data-target="#updateProductModal">Update</td>' +
					'<td><button class="btn btn-danger" onclick="deleteProduct(' + item.id + ')">Delete</td></tr>';
				$(".showListProduct").append(record);
			});
		}
	});
}

$(document).ready(function () {
	$("#createProductForm").submit(function (event) {
		// Prevent the form from submitting via the browser.
		event.preventDefault();
		createProduct();
	})
});
function createProduct() {
	var name = $("#Product_name").val();
	var id_category = $("#Product_idCategory").val();
	var image = $("#Product_image").val();
	var url = $("#Product_url").val();
	var url_category = $("#Product_url_category").val();
	var code = $("#Product_code").val();
	var description = $("#Product_description").val();
	var active = $("#Product_active").val();
	var detail = $("#Product_detail").val();
	var status = $("#Product_status").val();
	$.ajax({
		method: "POST",
		url: "/getListProduct",
		contentType: "application/json",
		dataType: "JSON",
		data: JSON.stringify({'name': name,'image':image, 'id_category':id_category, 'image':image,'url':url,'url_category':url_category,'code':code,'description':description, 'active':active,'detail':detail,'status':status}),
		success: function (data) {
			showListProduct();
			$('#addSuccessProduct').fadeIn(2000, function () {
				$('#addSuccessProduct').fadeOut(2000);
			});
		}
	})
}
function getOneProduct(id) {
	$.ajax({
		method: "GET",
		url: "/getListProduct/" + id,
		dataType: "JSON",
		success: function (data) {
			$('#updateProduct_id').val(data.id);
			$('#updateProduct_idCategory').val(data.id_category);
			$('#updateProduct_name').val(data.name);
			$('#updateProduct_image').val(data.image);
			$('#updateProduct_url').val(data.url);
			$('#updateProduct_url_category').val(data.url_category);
			$('#updateProduct_code').val(data.code);
			$('#updateProduct_description').val(data.description);
			$('#updateProduct_active').val(data.active);
			$('#updateProduct_detail').val(data.detail);
			$('#updateProduct_status').val(data.status);
		}
	})
}
function updateProduct() {
	var	product_id= $("#updateProduct_id").val();
	var	product_idCategory= $("#updateProduct_idCategory").val();
	var	product_name= $("#updateProduct_name").val();
	var	product_image= $("#updateProduct_image").val();
	var	product_url= $("#updateProduct_url").val();
	var	product_url_category= $("#updateProduct_url_category").val();
	var product_code = $('#updateProduct_code').val();
	var product_description = $('#updateProduct_description').val();
	var product_active = $('#updateProduct_active').val();
	var	product_detail= $("#updateProduct_detail").val();
	var	product_status= $("#updateProduct_status").val();
	$.ajax({
		method: "PUT",
		url: "/getListProduct/" + product_id,
		contentType: "application/json",
		dataType: "JSON",
		data: JSON.stringify({'id': product_id, 'id_category':product_idCategory, 'name':product_name, 'image':product_image,'url':product_url,'url_category':product_url_category,'code':product_code,'description':product_description,'active':product_active, 'detail':product_detail, 'status':product_status}),
		success: function () {
			console.log("Updated success");
			showListProduct();
			$('#updateSuccessProduct').fadeIn(2000, function () {
				$('#updateSuccessProduct').fadeOut(2000);
			});
		}
	})
}

//Delete
function deleteProduct(id) {
	$.ajax({
		method: "DELETE",
		url: "/getListProduct/" + id,
		success: function () {
			showListProduct();
			$('#deleteSuccessProduct').fadeIn(2000, function () {
				$('#deleteSuccessProduct').fadeOut(2000);
			});
			console.log("Deleted success");
		}
	})
}

function menuOption1() {
	  var x = document.getElementById("menuOption2");
	  var i = x.selectedIndex;
	  document.getElementById("menu_id").value = x.options[i].value;
	}
function menuOption3() {
	  var x = document.getElementById("menuOption4");
	  var i = x.selectedIndex;
	  document.getElementById("Product_idCategory").value = x.options[i].value;
	}
