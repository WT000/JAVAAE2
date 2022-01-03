<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>Orders</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
       <h1>All Orders</h1>
       <p>search form</p>
    </c:if>
       
    <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
       <h1>Your Orders</h1>
    </c:if>
       
    <table class="table">
        <tr>
            <th>Order ID (brief)</th>
            <th>Order Date</th>
            <th>Amount Paid</th>
            <th>Status</th>
            <th>Action (hide this, view button goes under here)</th>
        </tr>

        <c:forEach var="item" items="${shoppingCartItems}">
            <tr>
                <td>£${item.price}</td>
                <td>${item.quantity}</td>
                <td>
                    <!-- post avoids url encoded parameters -->
                    <form action="./cart" method="POST">
                        <input type="hidden" name="uuid" value="${item.uuid}">
                        <button class="btn btn-danger">View</button>
                    </form> 
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <a href="./cart" class="btn btn-primary" role="button">Back to your cart</a>

</main>
<jsp:include page="footer.jsp" />
