$('.slider').slick({
	autoplay : true,
	autoplaySpeed : 3000,
	arrows : true,
	// prevArrow:'<button type="button" class="fas fa-cloud"
	// style="color:red"></button>',
	// nextArrow:'<button type="button" class="fas fa-cloud"></button>',
	// centerMode:true,
	slidesToShow : 3,
	slidesToScroll : 1
});

function openNav() {
	document.getElementById("mySidepanel").style.width = "250px";
}

function closeNav() {
	document.getElementById("mySidepanel").style.width = "0";
}