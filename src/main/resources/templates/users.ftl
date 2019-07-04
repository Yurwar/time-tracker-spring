<#import "/spring.ftl" as spring/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body ng-app="usersListApp">
<div class="container-fluid" ng-controller="usersController">
    <ul class="nav nav-tabs nav-fill bg-light">
        <li class="nav-item">
            <a class="nav-link" href="/index">Main page</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/registration">Registration form</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="/login">Login</a>
        </li>
        <li class="nav-item">
            <a class="nav-link disabled" href="/users">All users</a>
        </li>
    </ul>

    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">All registered users</div>
                <div class="card-body">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Email</th>
                            <th>Password</th>
                            <th>Active</th>
                            <th>Roles</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="user in users">
                            <td>{{user.id}}</td>
                            <td>{{user.firstName}}</td>
                            <td>{{user.lastName}}</td>
                            <td>{{user.email}}</td>
                            <td>{{user.password}}</td>
                            <td>{{user.active}}</td>
                            <td>{{user.roles}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
<script type="text/javascript" src="<@spring.url '/js/usersListApp.js'/>"></script>
</body>
</html>