<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RWA Voting Competition</title>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<main>
    <header>
        <h1>RWA Voting Competition</h1>
        <nav>
            <a href="/home">Home</a>
            <a href="/rankings.html">Rankings</a>
        </nav>
        <div class="ikonice">
            <span id="refresh-button">&#x21bb;</span> 
            <span id="share-button">&#x1f517;</span> 
            <a href="${pageContext.request.contextPath}/admin/login" title="Login" style="color: white;">
                <img src="${pageContext.request.contextPath}/img/login.png" alt="Login" style="width: 30px; vertical-align: middle;">
            </a>
        </div>
    </header>


    <section id="video-container">
        <p>Loading...</p>
    </section>

    <section id="mini-ranking">
        <h2>Top 5 Videos</h2>
        <table>
            <thead>
                <tr>
                    <th>Thumbnail</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Votes(Positive/Total)</th>
                    <th>Rank</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="video" items="${topVideos}" varStatus="status">
                    <tr>
                        <!--<td><img src="https://img.youtube.com/vi/${video.youtubeId}/maxresdefault.jpg" alt="${video.title}" width="160"></td> -->
                        <td>
                            <c:choose>
                                <c:when test="${video.thumbnailUrl.startsWith('http')}">
                                    <img src="${video.thumbnailUrl}" alt="thumbnail" width="120">
                                </c:when>
                                <c:otherwise>
                                    <img src="/${video.thumbnailUrl}" alt="thumbnail" width="120">
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${video.title}</td>
                        <td>${video.description}</td>
                        <td>${video.positiveVotes}/${video.totalVotes}</td>
                        <td>${status.index + 1}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>
</main>

<footer>
    <p>RWA Voting Competition</p>
</footer>

<script src="js/script.js"></script>
</body>
</html>