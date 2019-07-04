<#import "/spring.ftl" as spring/>
<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Time-tracker</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
        body {
            padding-top: 3.5rem;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="#">
        <img src="<@spring.url '/images/stopwatch-white.svg'/>" class="d-inline-block align-top" alt="" width="30" height="30">
        Time-Tracker
        <span class="sr-only">(current)</span>
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbars">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <@security.authorize access="isAuthenticated()">
            <li class="nav-item active">
                <a class="nav-link" href="/users">All users</a>
            </li>
            </@security.authorize>
        </ul>

        <form class="form-inline my-2 my-lg-0" action="/registration" method="get">
            <button type="submit" class="btn btn-outline-primary mr-3 my-2 my-sm-0">Sign up</button>
        </form>
        <form class="form-inline my-2 my-lg-0" action="/login" method="get">
            <button type="submit" class="btn btn-success my-2 my-sm-0">Log in</button>
        </form>
    </div>
</nav>

<main role="main">

    <!-- Main jumbotron for a primary marketing message or call to action -->
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-4">Time-Tracker!</h1>
            <p>This is a time tracking program that allows you to track your activities</p>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h3>Track activities</h3>
                <p></p>
<#--                <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>-->
            </div>
            <div class="col-md-4">
                <h3>Mark the amount of spent time</h3>
                <p></p>
<#--                <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>-->
            </div>
            <div class="col-md-4">
                <h3>Make requests on adding or removing activites</h3>
                <p></p>
<#--                <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p>-->
            </div>
        </div>

        <hr>

    </div> <!-- /container -->

</main>

<footer class="container">
    <p>Footer</p>
</footer>
<#--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>-->
<#--<script>window.jQuery || document.write('<script src="/docs/4.3/assets/js/vendor/jquery-slim.min.js"><\/script>')</script>-->
<#--<script src="/docs/4.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-xrRywqdh3PHs8keKZN+8zzc5TX0GRTLCcmivcbNJWm2rs5C8PRhcEn3czEjhAO9o" crossorigin="anonymous"></script></body>-->
</html>