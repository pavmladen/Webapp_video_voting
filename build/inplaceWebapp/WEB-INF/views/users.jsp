<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <main>
        <header>
            <h1>RWA Voting Competition - Admin Panel</h1>
            <nav>
                <a href="${pageContext.request.contextPath}/home">Home</a>
                <a href="${pageContext.request.contextPath}/rankings.html">Rankings</a>
                <a href="${pageContext.request.contextPath}/admin/users">Users</a>
                <a href="${pageContext.request.contextPath}/admin/videos">Videos</a>
            </nav>
            <div class="ikonice">
                <a href="${pageContext.request.contextPath}/admin/logout" title="Logout" style="color: white;"
                onclick="return confirm('Are you sure you want to logout?');">
                    <img src="${pageContext.request.contextPath}/img/logout.png" alt="Logout" style="width: 30px; vertical-align: middle;">
                </a>
            </div>
        </header>

        <div class="admin-container">
            <h1 class="naslov">All Users</h1>

            <div style="text-align: center; margin-bottom: 20px;">
                <a href="${pageContext.request.contextPath}/admin/users?action=add" class="vote-dugme">Add New User</a>
            </div>

            <table class="video-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Roles</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.username}</td>
                        <td>${u.password}</td>
                        <td>
                            <c:forEach var="r" items="${u.roles}">
                                <span>${r.name}</span>
                            </c:forEach>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.id}" class="like-button">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.id}" class="skip-button"
                            onclick="return confirm('Delete this user?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </main>

    <footer style="padding-top: 30px; padding-bottom: 30px;">
        <p>RWA Voting Competition – Admin Panel</p>
    </footer>
</body>
</html>
