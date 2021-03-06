<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <h3>About</h3>
    <h1>What can be done in this release?</h1>
    <ul>
        <li><strong>ALPHA</strong></li>
        <li>Users exist with 4 roles (customer, admin, anon or deactivated)</li>
        <li>Properties can be set which are validated against the remote bank</li>
        <li>Catalogue items are stored in the database</li>
        <li>Items in the database can be edited</li>
        <li>Items in the database can be deleted</li>
        <li>Items can be added to the database</li>
        <li>Items can be added to a cart</li>
        <li>Cart items can be purchased</li>
        <li><strong>BETA</strong></li>
        <li>Items in the database can be searched for</li>
        <li>Cart items are converted into an order</li>
        <li>Orders can be viewed by relevant customers and admins</li>
        <li>Orders can be searched for</li>
        <li>Orders can have their status edited by an admin (including refund)</li>
    </ul>
</main>

<jsp:include page="footer.jsp" />
