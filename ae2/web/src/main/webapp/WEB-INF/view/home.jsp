<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <h3>Home</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <h1>Shopping Cart Application</h1>
    <p>Welcome to the site! The current pages are as follows:</p>
    
    <ul>
        <li>Catalogue - searching for items and adding items to your cart (admins can add, edit and remove items from this page)</li>
        <li>Cart - the items which are currently in the cart and where they're purchased</li>
        <li>Orders - the orders made by the user (admins can see all orders and search by username)</li>
        <li>Login and Register - where a user can login or make an account</li>
        <li>Manage Users - where an admin can edit users</li>
        <li>Edit Properties - where an admin can set bank properties</li>
    </ul>
</main>

<jsp:include page="footer.jsp" />
