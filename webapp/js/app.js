"use strict";

var accesstoken;

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
    		Stripe.setPublishableKey('pk_test_tNGrujIVT9iwZIWnheYyETnA');

		};

		ic.pay = function(){
			console.log("start");
			var creditCard = $("#creditCard").val();
			var cvc = $("#cvc").val();
			var expMonth = $("#expMonth").val();
			var expYear = $("#expYear").val();
			var $form = $('#payment-form');

			// Disable the submit button to prevent repeated clicks
			$form.find('button').prop('disabled', true);

			Stripe.card.createToken($form, ic.responseHandler);
		};

		ic.responseHandler = function(status, response){
			var $form = $('#payment-form');

			if (response.error) {
				// Show the errors on the form
				$form.find('.payment-errors').text(response.error.message);
				$form.find('button').prop('disabled', false);
			} else {
				// token contains id, last4, and card type
				var token = response.id;
				var paymentAmount = ic.infoData.price;
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
						var response = xmlhttp.responseText;
						if(response == "1")
						{
							alert("Your payment was successful");
						}
					}
					
				}
				var formData = new FormData();
				formData.append('stripeToken', token);
				formData.append('paymentAmount', paymentAmount);
				xmlhttp.open("POST", "php/stripePay.php", true);
				xmlhttp.send(formData);
			
			}
		};

		ic.init();
	}]);

	app.controller("UploadController", ['$state', function($state){
		var uc = this;

		uc.init = function(){
			var clientid = "8qecVJDX4y7s4d2nRMxR8TswqS-Ihj-_a4WKTAeb";
			var clientsecret = "KtCXP0xOg6H6VlTveptOUFzoE4qz4AP8r6tCE4vB";
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
					var response = JSON.parse(xmlhttp.responseText);
					accesstoken = response["access_token"];
				}
				
			}
			var formData = new FormData();
			formData.append('client_id', clientid);
			formData.append('client_secret', clientsecret);
			formData.append('grant_type', "client_credentials");
			xmlhttp.open("POST", "https://api.clarifai.com/v1/token/", true);
			xmlhttp.send(formData);
		};

		uc.formSubmit = function(){
			var name = $("#foodName").val();
			var description = $("#foodDescription").val();
			var location = $("#foodLocation").val();
			var price = $("#foodPrice").val();
			var img = $("#foodImg").prop("files")[0];
			var contact = $("#foodContact").val();
			var tags = $("#foodTags").val();
			var time = $("#foodTime").val();
			if (time == " " || name == "" || description == "" || location == "" || price == "" || contact == "" || tags == "" || img == "" || $('#foodImg').val() == "")
			{
				$("#feedback").show();
				return;
			} 
			$("#wait").show();
			
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
			formData.append('tags', tags);
			formData.append('timeCooked', time);
			xmlhttp.open("POST", "php/addFood.php", true);
			xmlhttp.send(formData);
		};

		uc.init();

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
		var tagsObj = JSON.parse(xmlhttp.responseText);	
		var tags = tagsObj["results"][0]["result"]["tag"]["classes"];
		var tagsString = "";
		for(var i = 0; i < tags.length; i++)
		{
			if(i == tags.length - 1) tagsString += tags[i];
			else tagsString += tags[i] + ", ";
		}
		$("#foodTags").val(tagsString);
	}
	
}
var formData = new FormData();
formData.append('encoded_data', input.files[0]);
xmlhttp.open("POST", " https://api.clarifai.com/v1/tag/", true);
xmlhttp.setRequestHeader("Authorization", "Bearer " + accesstoken);
xmlhttp.send(formData);
}