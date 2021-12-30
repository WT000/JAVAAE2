<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>
<%
// request set in controller
//    request.setAttribute("selectedPage","contact");
%>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <h3>Catalogue</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>
    
    <h1>Available Items</h1>
    <c:forEach var="item" items="${currentItems}">
        <p>${item.name}</p>
    </c:forEach>
        
    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <br>
        <h1>Unavailable Items</h1>
    </c:if>
</main>

<jsp:include page="footer.jsp" />
