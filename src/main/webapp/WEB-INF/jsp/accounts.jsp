<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="title">This is the list of current application accounts.</div>

<table class="accounts">
    <thead>
    <tr>
        <td>Username</td>
        <td>Full name</td>
        <td>Email</td>
        <td>Status</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${accountList}" var="account">
        <tr>
            <td>${account.username}</td>
            <td>${account.givenName} ${account.surname}</td>
            <td>${account.email}</td>
            <td>${account.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="returnAction">Return to <a href="/home">home</a> to continue working.</div>