<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Client</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="col-xs-4 centered">
        <h3>Client:</h3>

        <h4>Name: ${client.name}</h4>
        <h4>Gender: ${client.gender}</h4>
        <h4>City: ${client.city}</h4>
        <h4>E-mail: ${client.email}</h4>
        <h4>Telephone: ${client.telephone}</h4>
        <h3>Accounts:</h3>
        <table class="table table-hover">
            <thead>
            <th>#</th>
            <th>Type</th>
            <th>Balance</th>
            <th>Overdraft</th>
            <th>Status</th>
            </thead>
            <tbody>
            <c:forEach items="${client.accounts}" var="account" varStatus="count">
                <tr>
                    <th scope="row">${count.index + 1}</th>
                    <td><c:url value="/client/account?id=${account.id}&clientId=${client.id}" var="accountUrl"/>
                        <a href="${accountUrl}">${account.accountType}</a></td>
                    <td>${account.balance}</td>
                    <c:choose>
                        <c:when test="${account.accountType=='CHECKING'}">
                            <td>${account.overdraft}</td>
                        </c:when>
                        <c:otherwise>
                            <td>not available</td>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${account.id==client.activeAccount.id}">
                            <td>ACTIVE</td>
                        </c:when>
                        <c:otherwise>
                            <td>NOT - Active</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td>${balance}</td>
                <td>${overdraft}</td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <c:url value="/client/edit?id=${client.id}" var="editUrl"/>
        <a href="${editUrl}" class="btn btn-primary" role="button">Edit</a>

        <c:url value="/client/account/add?clientId=${client.id}" var="addAccountUrl"/>
        <a href="${addAccountUrl}" class="btn btn-success" role="button">Add account</a>

        <c:url value="/client" var="allClientsUrl"/>
        <a href="${allClientsUrl}" class="btn btn-default" role="button">Back to table</a>
    </div>
</div>

</body>
</html>