/**
 * Created by Протик on 24.11.2016.
 */

var app=angular.module('game', ['ngRoute'])
    app.config( ['$routeProvider', function($routeProvider) {
        $routeProvider

            .when('/page/0', {
                templateUrl: '/assets/0.html'
            })
            .when('/page/1', {
                templateUrl: '/assets/1.html'
            })
            .when('/page/2', {
                templateUrl: '/assets/2.html'
            })
            .when('/page/3', {
                templateUrl: '/assets/3.html'
            })
            .when('/page/4', {
                templateUrl: '/assets/4.html'
            })

            .otherwise({
                templateUrl: '/assets/0.html'
            });
    }])
        .directive("menu", function(){
        return {
            templateUrl:"assets/menu.html",
            replace: true,
            restrict: 'E'


        }
    })