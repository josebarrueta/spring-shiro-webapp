<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="sign">
    <shiro:authenticated>
        <a href="/logout">Sign out</a>
    </shiro:authenticated>

    <shiro:notAuthenticated>
        <a href="/login">Sign in</a>
    </shiro:notAuthenticated>
</div>