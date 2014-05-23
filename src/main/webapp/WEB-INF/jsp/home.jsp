<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<div class="accountInfo">
    <div>Hello, ${account.givenName} ${account.surname}</div>
    <div>Your email is: ${account.email}</div>
</div>

<br/>
<div class="title">Select an action to execute</div>

<shiro:hasRole name="admin">
    <div class="userAction">Click here to add more users to the application:  <a href="/admin/createUser">Create a new user</a>.</div>
</shiro:hasRole>

<shiro:hasAnyRoles name="admin, user">
    <div class="userAction">Click here to se the accounts in the application: <a href="/accounts">See accounts</a>. </div>
</shiro:hasAnyRoles>