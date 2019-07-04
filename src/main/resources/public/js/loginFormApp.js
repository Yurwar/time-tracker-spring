let loginFormApp = angular.module("loginFormApp", []);

loginFormApp.controller("loginFormController", function ($scope, $http) {
    $scope.loginData = {};

    let resultMessageEl = document.getElementById('logResMsg');
    let emailEl = document.getElementById('email');
    let passwordEl = document.getElementById('password');

    let emailLabel = document.getElementById('emailLabel');
    let passwordLabel = document.getElementById('passwordLabel');

    emailEl.addEventListener('input', () => {
        emailLabel.style.color = 'black';
        passwordLabel.style.color = 'black';
        $scope.message = '';
    });

    passwordEl.addEventListener('input', () => {
        emailLabel.style.color = 'black';
        passwordLabel.style.color = 'black';
        $scope.message = '';
    });

    $scope.sendForm = function (loginData) {
        $http({
            method: 'POST',
            url: '/login',
            data: loginData,
            headers: {'Content-Type' : 'application/json'}
        }).then(
            (data) => {
                $scope.message = 'Logged in';
                resultMessageEl.style.color = 'green';
                emailLabel.style.color = 'black';
                emailEl.value = '';
                passwordEl.value = '';
            },
            (error) => {
                $scope.message = error.data.message;
                resultMessageEl.style.color = 'red';

                emailLabel.style.color = 'red';
                passwordLabel.style.color = 'red';

                passwordEl.value = '';
            }
        );
    }
});

