<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Sample Webapp - Login</title>
        <link rel="stylesheet" type="text/css" href="/static/css/styles.css" media="screen" />
    </head>

    <body>

        <div class="title">Enter your credentials to enter the application</div>

        <div class="form">
            <form method="POST">
                <div class="inputForm">
                    <span>Username: </span> <input type="text" name="username" value="" maxlength="50"/>
                </div>
                <div class="inputForm">
                    <span>Password: </span> <input type="password" name="password" value="" maxlength="50"/>
                </div>
                <div>
                    <span>Remember me:</span> <input type="checkbox" name="rememberMe" value="true">
                </div>
                <div>
                    <input type="submit" value="Submit"/>
                </div>
            </form>
        </div>
    </body>
</html>