<%@ page contentType="text/html" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
    <head>
        <title>Sample Webapp - <tiles:insertAttribute name="title" ignore="true" /></title>
        <link rel="stylesheet" type="text/css" href="/static/css/styles.css" media="screen" />
    </head>
    <body>
        <div class="mainHeader">
            <tiles:insertAttribute name="header"/>
        </div>

        <div class="mainBody">
            <tiles:insertAttribute name="body" />
        </div>

        <div class="mainFooter">
            <tiles:insertAttribute name="footer" />
        </div>

    </body>
</html>