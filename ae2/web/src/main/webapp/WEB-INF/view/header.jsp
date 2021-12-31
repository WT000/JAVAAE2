<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <title>AE2 Shopping Cart</title>

        <!-- Bootstrap core CSS -->
        <link href="./resources/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="./resources/css/navbar.css" rel="stylesheet">
        <link href="./resources/css/styles.css" rel="stylesheet">
    </head>

    <body>

        <div class="container">

            <!-- Static navbar -->
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="./home">AE2 Shopping Cart</a>
                    </div>
                    
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li <% if ("home".equals(request.getAttribute("selectedPage"))) {%> class="active"  <% } %> ><a href="./home">Home</a></li> 
                            <li <% if ("catalogue".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% } %> ><a href="./catalogue">
                                    Catalogue<c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}"> (admin mode)</c:if>
                                </a></li> 
                            <li <% if ("contact".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% }%> ><a href="./contact">Cart</a></li>
                            <c:if test="${sessionUser.userRole != 'ANONYMOUS'}">
                                <li <% if ("orders".equals(request.getAttribute("selectedPage"))) {%>  class="active"  <% }%> ><a href="./contact">My Orders</a></li>
                            </c:if>
                            
                            <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                                <li class="dropdown" >
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> Admin <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="./users">Manage Users</a></li>
                                        <li><a href="./properties">Edit Properties</a></li>
                                    </ul>
                                </li>
                            </c:if>
                        </ul>
                                
                        <ul class="nav navbar-nav navbar-right">
                            <!-- user role:  ${sessionUser.userRole}-->
                            <c:if test="${sessionUser.userRole == 'ANONYMOUS'}">
                                <li <% if ("login".equals(request.getAttribute("selectedPage")) || "register".equals(request.getAttribute("selectedPage"))) {%> class="active"  <% } %> ><a href="./login">Login or create a new Account</a></li>
                                </c:if>
                                <c:if test="${sessionUser.userRole != 'ANONYMOUS'}">
                                <li><form id="logoutForm" method="POST" action="./logout"></form></li>
                                
                                <li><form id="profile" method="GET" action="./viewModifyUser">
                                    <input type="hidden" name="username" value="${sessionUser.username}"/>
                                </form></li>
                                
                                <li id="account-showing"><p class="text-muted"> Welcome, 
                                    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}"> Admin</c:if>                                   
                                    ${sessionUser.username}&nbsp;&nbsp;
                                    <br><a id="url-spacer" onclick="document.forms['logoutForm'].submit()">Logout</a>
                                    <a onclick="document.forms['profile'].submit()">User Profile</a></p></li>
                                </c:if>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div><!--/.container-fluid -->
            </nav>