var myApp = angular.module('myApp', ['ngResource']);

myApp.factory(	
	'PricesPaid',
	['$resource', function($resource) {
		return $resource('http://heathfield.local:8080/pricespaid/find', {
		}
		);
	}]
);

myApp.factory(	
	'ZooplaListings',
	['$resource', function($resource) {
		return $resource('http://heathfield.local:8080/zoopla/find', {
		}
		);
	}]
);

myApp.controller('Admin', 
	function ($scope, $http, PricesPaid, ZooplaListings) {
	
		$scope.showCounties = function() {			
			$http.get('http://heathfield.local:8080/pricespaid/counties').success(function(result) {
				$scope.counties = result;			
			});		
		};
		
		$scope.showBoroughs = function() {
			$http.get('http://heathfield.local:8080/pricespaid/boroughs?county=' + $scope.selectedCounty).success(function(result) {
				$scope.boroughs = result;			
			});							
		};
		
		$scope.showStreets = function() {
			$http.get('http://heathfield.local:8080/pricespaid/streets?borough=' + $scope.selectedBorough).success(function(result) {
				$scope.streets = result;	
			});		
		};
		
		$scope.showZooplaAddresses = function() {
			$http.get('http://heathfield.local:8080/zoopla/addresses').success(function(result) {
				$scope.zooplaAddresses = result;	
			});		
		};
		
		$scope.showResults = function() {										
			PricesPaid.query({
				county: $scope.selectedCounty,
				borough: $scope.selectedBorough,
				street: $scope.selectedStreet},
				function(result) {							
					$scope.pricesPaid = result;
			});						
		};
		
		$scope.showZooplaResults = function() {										
			ZooplaListings.get({
				q: $scope.selectedZooplaAddress},
				function(result) {			
					$scope.zooplaListings = result;
			});
		};
		
		$scope.addressNumbers = {};
		$scope.addressStreets = {};
		$scope.addressBoroughs = {};
		$scope.addressCounties = {};

		$scope.showCounties();
		$scope.showZooplaAddresses();
		
		$scope.saveAddress = function(listingId) {
			console.log('Listing id' + listingId);
			console.log($scope.addressNumbers[listingId]);
			console.log($scope.addressStreets[listingId]);
			console.log($scope.addressBoroughs[listingId]);
			console.log($scope.addressCounties[listingId]);
		};
	}
	
);
