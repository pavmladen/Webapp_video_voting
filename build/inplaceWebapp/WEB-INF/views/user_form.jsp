<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title><c:choose>
        <c:when test="${isEdit}">Edit User</c:when>
        <c:otherwise>Add New User</c:otherwise>
    </c:choose></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="form-container">
    <h2 style="color: #ff3c20; text-align: center;">
        <c:choose>
            <c:when test="${isEdit}">Edit User</c:when>
            <c:otherwise>Add New User</c:otherwise>
        </c:choose>
    </h2>

    <form method="post" action="${pageContext.request.contextPath}/admin/users" class="styled-form">
        <c:if test="${isEdit}">
            <input type="hidden" name="id" value="${user.id}">
        </c:if>

        <label for="username"><b>Username:</b></label>
        <input type="text" name="username" required value="${isEdit ? user.username : ''}">

        <label for="password"><b>Password:</b></label>
        <input type="text" name="password" required value="${isEdit ? user.password : ''}">

        <label for="roles"><b>Roles:</b></label>
        <div class="checkbox-group">
            <c:forEach var="role" items="${roles}">
                <label class="checkbox-label">
                    <input type="checkbox" name="roleIds" value="${role.id}"
                        <c:if test="${isEdit and user.roles.contains(role)}">checked</c:if>> ${role.name}
                </label>
            </c:forEach>
        </div>

        <button type="submit" class="submit-button">
            <c:choose>
                <c:when test="${isEdit}">Update User</c:when>
                <c:otherwise>Add User</c:otherwise>
            </c:choose>
        </button>
    </form>

    <div class="back-link" style="text-align: center; margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/admin/users" class="back-link-button">Back to User List</a>
    </div>
</div>

</body>
</html>
