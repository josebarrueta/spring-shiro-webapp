<%@ page contentType="text/html" language="java" pageEncoding="utf-8" %>
<html ng-app="customLoginApp">
<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="/assets/styles/main.css">
</head>
<body>

    <!-- Add your site or application content here -->
    <div class="container" ng-view=""></div>

    <script src="/assets/js/lib/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.4/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.4/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.4/angular-resource.min.js"></script>

    <script src="/assets/js/app.js"></script>
    <script src="/assets/js/lib/angular-facebook.js"></script>
    <script src="/assets/js/controllers/login.js"></script>
    <script src="/assets/js/controllers/signup.js"></script>
</body>
</html>
