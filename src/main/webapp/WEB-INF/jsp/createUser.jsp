<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
    <title>Sample Webapp - Create user</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css" media="screen" />
</head>

<body>
    Hello,

    <div class="form">
        <form method="POST">
            <div class="inputForm">
                <span>username: </span> <input type="text" name="username" value="" maxlength="50"/>
            </div>
            <input type="submit">
        </form>
    </div>
</body>
</html>