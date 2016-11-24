/**
 * Created by Протик on 24.11.2016.
 */

var app = angular.module("game", ["ngRoute","ngResource"]);
/*
app.config(function($routeProvider) {
    $routeProvider
        .when("/page/:id", {
            templateUrl : function(page){
                return "assets/"+page+".html"
            },
            controller: "pagesController"
        })
        .otherwise("page/0");
})
    .controller("pagesController",function($scope,$log,$rootScope,$routeParams){
        $scope.page=parseInt($routeParams.id) || 0;
    })
*/

var app=angular.module('game', ['ngRoute'])
    app.config( ['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/page/0', {
                templateUrl: '../assets/0.html'
            })
            .when('/page/1', {
                templateUrl: '../assets/1.html'
            })
            .when('/page/2', {
                templateUrl: '../assets/2.html'
            })
            .when('/page/3', {
                templateUrl: '../assets/3.html'
            })
            .when('/page/4', {
                templateUrl: '../assets/4.html'
            })

            .otherwise({
                templateUrl: '../assets/0.html'
            });
    }])





    .directive("menu", function(){
        return {
            templateUrl:"menu.html",
            replace: true,
            restrict: 'E'


        }
    })