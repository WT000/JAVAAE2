<%-- 
    Document   : errornew
    Created on : 15 Nov 2021, 17:08:19
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Online Sale Service</title>
        <link rel="stylesheet" href="public/styles/style.css">
    </head>
    <body>
        <h1>ERROR</h1>
        <p>The Sale Service has encountered an error, <a href="./saleservice">click here to return to the app</a>.</p>
        <p>${status} - ${error}</p>
        <p>Exception:  ${exception.message}</p>
        <p>Stack trace:</p>
        <p>${strStackTrace}</p>
        <p>Failed URL: ${requestUrl}</p>
    </body>
</html>
