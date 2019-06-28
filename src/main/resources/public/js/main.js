let app= angular.module("springBootApp", []);

app.controller("appController", function ($scope, $http) {
    $scope.users = [];
    $http.get('api/users').then(function (response) {
        $scope.users = response.data;
    })
});
    
