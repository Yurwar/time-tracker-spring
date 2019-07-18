let usersListApp= angular.module("usersListApp", []);

usersListApp.controller("usersController", function ($scope, $http) {
    $scope.users = [];
    $http.get('api/user').then(function (response) {
        $scope.users = response.data;
    })
});