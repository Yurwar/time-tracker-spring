let usersListApp= angular.module("usersListApp", []);
let regFormApp = angular.module("regFormApp", []);


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