<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
          <title>Sample Webapp - Login</title>
    </head>

    <body>
        <div style="margin: 0px 10px;">Enter your credentials to enter the application:</div>
        <form method="POST">
            <div class="inputForm">
                <span>Username: </span> <input type="text" name="username" value="" maxlength="50"/>
            </div>
            <div class="inputForm">
                <span>Password: </span> <input type="text" name="password" value="" maxlength="50"/>
            </div>
            <div>
                <span>Remember me:</span> <input type="checkbox" name="rememberMe" value="false">
            </div>
            <div>
                <input type="submit"/>
            </div>
        </form>
    </body>
</html>