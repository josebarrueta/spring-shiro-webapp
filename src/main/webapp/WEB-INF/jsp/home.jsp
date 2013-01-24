<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
    <head>
        <title>Sample Webapp - Home</title>
        <link rel="stylesheet" type="text/css" href="/static/css/styles.css" media="screen" />
    </head>

    <body>
        Hello,

        <shiro:hasRole name="admin">
            <div class="">You have permissions to add more users to the application:  <a href="/admin/createUser">Create a new user</a>.</div>

            <div>Or you can check the current accounts in the application: <a href="/accounts">See accounts</a>. </div>
        </shiro:hasRole>

        <shiro:hasRole name="user">
            <div>You can only check the current accounts in the application: <a href="/accounts">See accounts</a>. </div>
        </shiro:hasRole>

    </body>
</html>