<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en-US" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>Welcome</title>
        <meta charset="UTF-8"></meta>
    </head>
     <body>
        <table>
            <tr>
            <h1>Welcome to another quiz game</h1>
        </tr>
        <tr>
        <h2>Please login or register</h2>
        </tr>
        <tr>
        <form method="GET" action="/L3ST/quiz">
            Username <input type="text" name="username"><br>
            Password <input type="text" name="password"><br>
            <input type="hidden" name="action" value="login">
            <input type="submit" value="Login">  
        </form>
        
        <form method="GET" action="/L3ST/reg">
            <input type="hidden" name="action" value="register">
            <input type="submit" value="Register">
        </form>
        </tr>
        
    </body>
</html>
