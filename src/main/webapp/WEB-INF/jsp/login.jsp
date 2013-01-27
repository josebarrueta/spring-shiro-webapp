<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="title">Please enter your username and password to access the application.</div>

<div class="form">
    <form method="POST">
        <div class="inputForm">
            <span>Username: </span> <input type="text" name="username" value="" maxlength="50"/>
        </div>
        <div class="inputForm">
            <span>Password: </span> <input type="password" name="password" value="" maxlength="50"/>
        </div>
        <div class="inputForm">
            <span>Remember me:</span> <input type="checkbox" name="rememberMe" value="true">
        </div>
        <input type="submit" value="Submit" class="button"/>
    </form>
</div>