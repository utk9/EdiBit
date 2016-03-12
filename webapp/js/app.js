"use strict";

$(document).ready(function(){
	$(".navbar a").on("click", function(){
	   $(".nav").find(".active").removeClass("active");
	   $(this).parent().addClass("active");
	});
});

(function(){
	var app = angular.module('foodApp', ['ui.router']);

	app.config(function($stateProvider, $urlRouterProvider){

 		$urlRouterProvider.otherwise("/home");

		$stateProvider
			.state('home', {
				url: "/home",
				templateUrl: 'partial-home.html'
			})
			.state('about', {
				url: "/about",
				templateUrl: 'partial-about.html'
			})
			.state('search', {
				url: "/search/:query",
				templateUrl: 'partial-search.html',
				controller: 'SearchController',
				controllerAs: 'search'
			})
			.state('info', {
				url: "/info/:id",
				templateUrl: 'partial-info.html',
				controller: 'InfoController',
				controllerAs: 'info'
			})
			.state('upload', {
				url: "/upload",
				templateUrl: 'partial-upload.html',
				controller: 'UploadController',
				controllerAs: 'upload'
			})
			.state('contact', {
				url: "/contact",
				templateUrl: 'partial-contact.html'
			});
	});

	app.controller("SearchController", ['$stateParams', '$http', function($stateParams, $http){
		var sc = this;
		sc.init = function(){

			//post to php
			$http.post("php/search.php", $stateParams.query)
				.success(function(data, status) {
            		sc.foodData = data;
            		$("#searchWait").remove();
        		});


		};
		sc.init();
	}]);

	app.controller("InfoController", ['$stateParams', '$http', function($stateParams, $http){
		var ic = this;
		ic.init = function(){
			//post to php
			$http.post("php/info.php", $stateParams.id)
				.success(function(data, status) {
            		ic.infoData = data;
            		$("#searchWait").remove();
        		});

		};
		ic.init();
	}]);

	app.controller("UploadController", ['$state', function($state){
		var uc = this;

		uc.formSubmit = function(){
			$("#wait").show();
			var name = $("#foodName").val();
			var description = $("#foodDescription").val();
			var location = $("#foodLocation").val();
			var price = $("#foodPrice").val();
			var img = $("#foodImg").prop("files")[0];
			var contact = $("#foodContact").val();
			console.log(contact);
			if (name == "" || description == "" || location == "" || price == "" || contact == "" || img == "" || $('#foodImg').val() == "")
			{
				$("#feedback").show();
				return;
			} 
			
			var success = 0;
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
				xmlhttp = new XMLHttpRequest();
			}
			else
			{
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==0)
				{
					console.log("not initialized");
				}
				if(xmlhttp.readyState==1)
				{
					console.log("connection established");
				}
				
				if(xmlhttp.readyState==2)
				{
					console.log("request received");
				}
				
				if(xmlhttp.readyState==3)
				{
					console.log("processing request");
				}
				if(xmlhttp.readyState==4)
				{
					var idR = xmlhttp.responseText;
					$("#wait").hide();
					$state.go("info", {"id": idR});
				}
				
			}
			var formData = new FormData();
			formData.append('file', img);
			formData.append('name', name);
			formData.append('description', description);
			formData.append('location', location);
			formData.append('price', price);
			formData.append('contact', contact);
			xmlhttp.open("POST", "php/addFood.php", true);
			xmlhttp.send(formData);
		};

	}]);

})();


function readURL(input) {
if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      $('#preview')
        .css('background-image', "url('" + e.target.result + "')");
    };
    reader.readAsDataURL(input.files[0]);
  }
}