<%--
  Created by IntelliJ IDEA.
  User: Man
  Date: 04.03.2017
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All clients</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">

</head>
<body>

<div class="container">
    <div class="col-xs-4 centered">
        <h2>Clients:</h2>
        <table class="table table-hover">
            <td>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Gender</th>
                    <th>City</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${clients}" var="client" varStatus="count">
                    <tr>
                        <th scope="row">${count.index + 1}</th>
                        <td>
                            <c:url value="/client?id=${client.id}" var="clientUrl"/>
                            <a href="${clientUrl}">${client.name}</a>
                        </td>
                        <td>${client.gender}</td>
                        <td>${client.city}</td>
                        <td>
                            <c:url value="/client/delete?id=${client.id}" var="deleteUrl"/>
                            <form action="${deleteUrl}" method="get">
                            <a href="${deleteUrl}" class="btn btn-danger" role="button">Delete</a>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
        </table>
        <c:url value="/client/edit" var="createUrl"/>
        <a href="${createUrl}" class="btn btn-lg btn-primary" role="button">New client</a>
    </div>
</div>
</body>
</html>
