<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Sample Webapp - Authorization Error</title>
        <link rel="stylesheet" type="text/css" href="/static/css/styles.css" media="screen"/>
    </head>

    <body>
        <h1 class="errorCode">Error 403</h1>

        <div class="errorDescription">You're here because you don't have the permissions to executed the desired operation.</div>

        <div class="errorLink">Go to <a href="/home">home page</a> to using this app.</div>
    </body>
</html>

