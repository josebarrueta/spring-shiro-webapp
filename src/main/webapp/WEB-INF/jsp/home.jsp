<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="title">Select an action to execute</div>


<shiro:hasRole name="admin">
    <div class="userAction">You have permissions to add more users to the application:  <a href="/admin/createUser">Create a new user</a>.</div>
</shiro:hasRole>

<shiro:hasAnyRoles name="admin, user">
    <div class="userAction">You can only check the current accounts in the application: <a href="/accounts">See accounts</a>. </div>
</shiro:hasAnyRoles>