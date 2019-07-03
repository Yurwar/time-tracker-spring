let usersListApp= angular.module("usersListApp", []);
let regFormApp = angular.module("regFormApp", []);
let loginFormApp = angular.module("loginFormApp", []);

usersListApp.controller("usersController", function ($scope, $http) {
    $scope.users = [];
    $http.get('api/user').then(function (response) {
        $scope.users = response.data;
    })
});


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
            url: '/register-user',
            data: regData,
            headers: {'Content-Type' : 'application/json'}
        }).then(
            (data) => {
                resultMessageEl.style.color = 'green';
                $scope.message = 'Successfully registered';
                firstNameEl.value = '';
                lastNameEl.value = '';
                passwordEl.value = '';

                emailEl.value = '';
                emailLabel.style.color = 'black';
            },
            (error) => {
                console.log(error.data);
                resultMessageEl.style.color = 'red';
                $scope.message = error.data.message;

                emailLabel.style.color = 'red';
            }
        )

    };

    emailEl.addEventListener('input', (event) => {
        emailLabel.style.color = 'black';
        $scope.message = '';
    });
});

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

   $scope.sendForm = function (loginData) {
       $http({
           method: 'POST',
           url: '/login-user',
           data: $.param(loginData),
           headers: {'Content-Type' : 'application/x-www-form-urlencoded'}
       }).then(
       (data) => {
           console.log(data);
           $scope.message = 'Logged in';
           resultMessageEl.style.color = 'green';
           emailLabel.style.color = 'black';
           emailEl.value = '';
           passwordEl.value = '';
           },
           (error) => {
               console.log(error.data);

               resultMessageEl.style.color = 'red';
               emailLabel.style.color = 'red';
               passwordLabel.style.color = 'red';

               passwordEl.value = '';
               $scope.message = error.data.message;
           }
       );
   }
});

