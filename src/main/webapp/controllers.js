var myApp = angular.module('myApp', ['ngResource']);

myApp.factory(	
	'PricesPaid',
	['$resource', function($resource) {
		return $resource('http://localhost:8080/pricespaid/find', {
		}
		);
	}]
);

myApp.controller('Admin', 
	function ($scope, $http, PricesPaid) {
	
		$scope.showCounties = function() {			
			$http.get('http://localhost:8080/pricespaid/counties').success(function(result) {
				$scope.counties = result;			
			});		
		};
		
		$scope.showBoroughs = function() {
			$http.get('http://localhost:8080/pricespaid/boroughs?county=' + $scope.selectedCounty).success(function(result) {
				$scope.boroughs = result;			
			});							
		};
					
		$scope.showDistricts = function() {
			$http.get('http://localhost:8080/pricespaid/districts?borough=' + $scope.selectedBorough).success(function(result) {
				$scope.districts = result;			
			});								
		};
				
		$scope.showStreets = function() {
			$http.get('http://localhost:8080/pricespaid/streets?district=' + $scope.selectedDistrict).success(function(result) {
				$scope.streets = result;	
			});		
		};
		
		$scope.showResults = function() {										
			PricesPaid.query({
				county: $scope.selectedCounty,
				borough: $scope.selectedBorough,
				district: $scope.selectedDistrict,
				street: $scope.selectedStreet},
				function(result) {							
					$scope.pricesPaid = result;
			});						
		};
		
		$scope.showCounties();
	}
	
);
