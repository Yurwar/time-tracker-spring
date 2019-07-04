<#import "/spring.ftl" as spring/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<@spring.url '/css/signin.css'/>" >
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</head>
<body class="text-center" ng-app="loginFormApp" ng-controller="loginFormController">
<form class="form-signin" method="post" action="/login">
    <a href="/index"><img class="mb-4" src="<@spring.url '/images/stopwatch.svg'/>" width="100" height="100"></a>
    <h1 class="h3 mb-3 font-weight-normal">Sign in to Time-Tracker</h1>
    <#if logout>
        <div class="alert alert-success" role="alert">You have been logged out successfully</div>
    </#if>
    <#if error>
        <div class="alert alert-danger" role="alert">Incorrect email or password</div>
    </#if>
    <label id="emailLabel" for="email" class="sr-only">Email address</label>
    <input type="email"
           id="email"
           name="username"
           class="form-control"
           placeholder="Email address"
           required
           autofocus
           ng-model="regData.email">
    <label id="passwordLabel" for="password" class="sr-only">Password</label>
    <input type="password"
           name="password"
           id="password"
           placeholder="Password"
           required
           class="form-control"
           ng-model="regData.password">
<#--    <div class="checkbox mb-3">-->
<#--        <label>-->
<#--            <input type="checkbox" value="remember-me"> Remember me-->
<#--        </label>-->
<#--    </div>-->
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
<script type="text/javascript" src="<@spring.url '/js/loginFormApp.js'/>"></script></body>
</html>