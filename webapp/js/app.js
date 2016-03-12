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
})();