<%--
  Created by IntelliJ IDEA.
  User: Man
  Date: 05.03.2017
  Time: 1:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Account</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <div class="col-xs-4 centered">
        <h3>Account:</h3>
        <h4>Client: ${clientName}</h4>
        <h4>Account type: ${account.accountType}</h4>
        <h4>Balance: ${account.balance}</h4>
        <c:if test="${account.accountType=='CHECKING'}">
            <h4>Overdraft: ${account.overdraft}</h4>
        </c:if>
        <form class="form-inline">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">$</div>
                    <input id="amount" type="text" class="form-control" placeholder="Amount" name="amount">
                    <div class="input-group-addon">.00</div>
                </div>
            </div>
            <input type="hidden" name="clientId" value="${clientId}">
            <input type="hidden" name="id" value="${account.id}">
            <input formaction="/client/account/deposit" type="submit" class="btn btn-success" value="Deposit">
            <input formaction="/client/account/withdraw" type="submit" class="btn btn-warning" value="Withdraw">
            <button type="button" name="transferButton" class="btn btn-info">Transfer</button>
        </form>
        <script>

        </script>
        <form hidden="hidden" name="transferForm">
            <h4>Choose client and account to transfer:</h4>
            <c:forEach items="${clients}" var="client" varStatus="count">
                <tr>
                    <th scope="row">${count.index+1}</th>
                    <td>${client.name}</td>
                    <td>${client.gender}</td>
                    <td>${client.city}</td>
                    <td>
                        <select class="form-control">
                            <c:forEach items="${client.accounts}" var="account">
                                <option>${account}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </c:forEach>
        </form>
        <c:url value="/client?id=${clientId}" var="backToClientUrl"/>
        <a href="${backToClientUrl}" class="btn btn-primary" role="button">Back to client info</a>
    </div>
</div>
</body>
</html>
