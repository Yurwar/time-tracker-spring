let usersListApp= angular.module("usersListApp", []);
let regFormApp = angular.module("regFormApp", []);
let loginFormApp = angular.module("loginFormApp", []);

usersListApp.controller("usersController", function ($scope, $http) {
    $scope.users = [];
    $http.get('api/users').then(function (response) {
        $scope.users = response.data;
    })
});


regFormApp.controller("regFormController", function ($scope, $http) {
    $scope.regData = {};
    $scope.sendForm = function (regData) {
        $http({
            method: 'POST',
            url: '/register',
            data: $.param(regData),
            headers: {'Content-Type' : 'application/x-www-form-urlencoded'}
        })
    }
});

loginFormApp.controller("loginFormController", function ($scope, $http) {
   $scope.loginData = {};
   $scope.sendForm = function (loginData) {
       $http({
           method: 'POST',
           url: '/login-user',
           data: $.param(loginData),
           headers: {'Content-Type' : 'application/x-www-form-urlencoded'}
       })
   }
});

