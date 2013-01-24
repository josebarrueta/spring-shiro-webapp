<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
    <title>Sample Webapp - Accounts</title>
    <link rel="stylesheet" type="text/css" href="/static/css/styles.css" media="screen"/>
</head>

<body>
    <div>This is the list of current application accounts.</div>

    <table class="accounts">
        <thead>
            <tr>
                <td>first name</td>
                <td>e-mail</td>
                <td>status</td>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${accountList}" var="account">
                <tr>
                    <td>${account.givenName}</td>
                    <td>${account.email}</td>
                    <td>${account.status}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>