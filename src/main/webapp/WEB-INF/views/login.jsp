<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .login-container {
            max-width: 400px;
            margin: 100px auto;
            background-color: #202020;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 8px rgba(0,0,0,0.3);
            text-align: center;
        }

        .login-container h2 {
            color: #ff4500;
            margin-bottom: 20px;
        }

        .login-container label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            margin-top: 15px;
        }

        .login-container input {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 4px;
            background-color: #282828;
            color: #fff;
        }

        .login-container button {
            width: 100%;
            padding: 12px;
            margin-top: 20px;
            background-color: #ff4500;
            border: none;
            border-radius: 5px;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .login-container button:hover {
            background-color: #e03e00;
        }

        .error-message {
            margin-top: 15px;
            color: #ff4d4d;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Admin Login</h2>

        <form method="post" action="${pageContext.request.contextPath}/admin/login">
            <label for="username">Username:</label>
            <input type="text" name="username" required />

            <label for="password">Password:</label>
            <input type="password" name="password" required />

            <button type="submit">Login</button>
        </form>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </div>
</body>
</html>