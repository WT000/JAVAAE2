<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// request set in controller
//    request.setAttribute("selectedPage","about");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>About</h3>
    <h1>What can be done in this release?</h1>
    <ul>
        <li>Users exist with 4 roles (customer, admin, anon or deactivated)</li>
        <li>Properties can be set which are validated against the remote bank</li>
        <li>Catalogue items are stored in the database</li>
        <li>Items in the database can be searched for</li>
        <li>Items in the database can be edited</li>
    </ul>
</main>




<jsp:include page="footer.jsp" />
