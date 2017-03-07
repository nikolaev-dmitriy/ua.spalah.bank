<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit client:</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="col-xs-4 centered">
        <h3>Edit client:</h3>

        <c:url value="/client/edit" var="editUrl"/>
        <form action="${editUrl}" method="post">

            <input type="hidden" name="id" value="${client.id}">
            <input type="hidden" name="activeAccountId" value="${client.activeAccount.id}">

            <div class="form-group">
                <label for="name">Name:</label>
                <input id="name" type="text" class="form-control" name="name" value="${client.name}">
            </div>

            <div class="form-group">
                <label>Gender:</label>
                <input type="radio" name="gender" value="FEMALE" ${client.gender == "FEMALE" ? "checked" : ""}/>FEMALE
                <input type="radio" name="gender" value="MALE" ${client.gender == "MALE" ? "checked" : ""}/>MALE
            </div>

            <div class="form-group">
                <label for="city">City:</label>
                <input id="city" type="text" class="form-control" name="city" value="${client.city}">
            </div>

            <div class="form-group">
                <label for="email">E-mail:</label>
                <input id="email" type="text" class="form-control" name="email" value="${client.email}">
            </div>

            <div class="form-group">
                <label for="telephone">Telephone:</label>
                <input id="telephone" type="text" class="form-control" name="telephone" value="${client.telephone}">
            </div>

            <input type="submit" class="btn btn-success" value="Submit">
        </form>
    </div>
</div>

</body>
</html>