let regFormApp = angular.module("regFormApp", []);

regFormApp.controller("regFormController", function ($scope, $http) {
    $scope.regData = {};

    let resultMessageEl = document.getElementById('regResMsg');
    let firstNameEl = document.getElementById('firstName');
    let lastNameEl = document.getElementById('lastName');
    let emailEl = document.getElementById('email');
    let passwordEl = document.getElementById('password');
    let emailLabel = document.getElementById('emailLabel');

    $scope.sendForm = function (regData) {
        $http({
            method: 'POST',
            url: '/registration',
            data: regData,
            headers: {'Content-Type' : 'application/json'}
        }).then(
            (data) => {
                $scope.message = 'Successfully registered';
                resultMessageEl.style.color = 'green';
                firstNameEl.value = '';
                lastNameEl.value = '';
                passwordEl.value = '';

                emailEl.value = '';
                emailLabel.style.color = 'black';
            },
            (error) => {
                resultMessageEl.style.color = 'red';
                resultMessageEl.style.color = 'red';
                $scope.message = error.data.message;

                emailLabel.style.color = 'red';
            }
        )

    };

    emailEl.addEventListener('input', () => {
        emailLabel.style.color = 'black';
        $scope.message = '';
    });
});