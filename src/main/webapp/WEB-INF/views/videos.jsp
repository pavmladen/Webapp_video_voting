<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.voting.model.Video" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Videos</title>
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

    <h1 class="naslov">All Videos</h1>

    <div style="text-align: center; margin-bottom: 20px;">
        <a href="${pageContext.request.contextPath}/admin/videos?action=add" class="vote-dugme">Add New Video</a>
    </div>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Rank</th>
                    <th>Thumbnail</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>YouTube ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="v" items="${videos}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td> 
                        <td>
                            <c:choose>
                                <c:when test="${v.thumbnailUrl.startsWith('http')}">
                                    <img src="${v.thumbnailUrl}" alt="thumbnail" width="120">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/${v.thumbnailUrl}" alt="thumbnail" width="120">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${v.title}</td>
                        <td>${v.description}</td>
                        <td>${v.youtubeId}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/videos?action=edit&id=${v.id}" class="like-button">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/videos?action=delete&id=${v.id}" class="skip-button" onclick="return confirm('Are you sure?')">Delete</a>
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
