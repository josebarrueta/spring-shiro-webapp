<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Sample Webapp - Home</title>
    </head>

    <body>
        <c:choose>
            <c:when test="${isAdmin}">
                <div>Admin can add more users to the application:  <a href="/admin/createUser">Create Users</a></div>
            </c:when>
            <c:when test="${isUser}">
                <div>Hello user!</div>
            </c:when>
            <c:otherwise>
                <div>An error might occurred, you're not supposed to see this page.</div>
            </c:otherwise>
        </c:choose>
    </body>
</html>