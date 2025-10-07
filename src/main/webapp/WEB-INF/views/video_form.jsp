<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.voting.model.Video" %>
<%
    Video video = (Video) request.getAttribute("video");
    boolean isEdit = (video != null);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Edit Video" : "Add Video" %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;700&display=swap" rel="stylesheet">
    <style>
        .form-container {
            max-width: 600px;
            margin: 60px auto;
            padding: 30px;
            background-color: #202020;
            border-radius: 8px;
            color: #ffffff;
        }

        .form-container h2 {
            text-align: center;
            color: #ff4500;
            margin-bottom: 20px;
        }

        .form-container label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        .form-container input, .form-container textarea {
            width: 100%;
            padding: 10px;
            background-color: #282828;
            border: none;
            border-radius: 5px;
            color: white;
        }

        .form-container input[type="file"] {
            padding: 5px;
        }

        .form-container button {
            margin-top: 25px;
            width: 100%;
            padding: 12px;
            background-color: #ff4500;
            border: none;
            color: white;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-container button:hover {
            background-color: #e03e00;
        }

        .preview-img {
            margin-top: 15px;
            text-align: center;
        }

        .preview-img img {
            max-width: 100%;
            border-radius: 5px;
        }

        .back-link {
            display: block;
            margin-top: 20px;
            text-align: center;
        }

        .back-link a {
            color: #ff4500;
            text-decoration: none;
        }

        .back-link a:hover {
            color: white;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2><%= isEdit ? "Edit Video" : "Add New Video" %></h2>

    <form method="post" action="${pageContext.request.contextPath}/admin/videos" enctype="multipart/form-data">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= video.getId() %>">
        <% } %>

        <label for="title">Title:</label>
        <input type="text" name="title" required value="<%= isEdit ? video.getTitle() : "" %>">

        <label for="description">Description:</label>
        <textarea name="description" required rows="3"><%= isEdit ? video.getDescription() : "" %></textarea>

        <label for="youtubeId">YouTube ID:</label>
        <input type="text" name="youtubeId" required value="<%= isEdit ? video.getYoutubeId() : "" %>">

        <label for="thumbnail">Upload Custom Thumbnail (optional):</label>
        <input type="file" name="thumbnail">

        <div class="preview-img">
            <%
                String thumb = null;

                if (isEdit && video != null) {
                    thumb = video.getThumbnailUrl();

                    if (thumb == null || thumb.trim().isEmpty()) {
                        thumb = "https://img.youtube.com/vi/" + video.getYoutubeId() + "/0.jpg";
                    } else if (!thumb.startsWith("http")) {
                        thumb = request.getContextPath() + "/" + thumb;
                    }
                } else {
                    
                    String tempYoutubeId = request.getParameter("youtubeId");
                    if (tempYoutubeId != null && !tempYoutubeId.isEmpty()) {
                        thumb = "https://img.youtube.com/vi/" + tempYoutubeId + "/0.jpg";
                    }
                }
            %>

            <% if (thumb != null) { %>
                <img src="<%= thumb %>" alt="Preview thumbnail">
            <% } %>
        </div>


        <button type="submit"><%= isEdit ? "Update Video" : "Add Video" %></button>
    </form>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/admin/videos">Back to Video List</a>
    </div>
</div>

</body>
</html>
