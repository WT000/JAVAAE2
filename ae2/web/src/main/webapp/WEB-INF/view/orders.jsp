<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>Orders</h3>
    <div style="color:green;">${message}</div>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
       <h1>Search by Username</h1>
       <form action="./orders" method="GET">
           <input name="toFind">
           <button class="btn btn-primary">Search</button>
       </form>
       
       <h1>Orders (${orders.size()})</h1>
    </c:if>
       
    <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
       <h1>Your Orders (${orders.size()})</h1>
    </c:if>
    
    <table class="table">
        <tr>
            <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                <th>Username</th>
            </c:if>
            <th>Order ID</th>
            <th>Order Date and Time</th>
            <th>Amount Paid</th>
            <th>Status</th>
        </tr>
        
        <c:forEach var="invoice" items="${orders}">
            <tr>
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <td>${invoice.purchaser.username}</td>
                </c:if>
                <td>${invoice.invoiceNumber}</td>
                <td>${invoice.dateOfPurchase}</td>
                <td>£${invoice.amountDue}</td>
                <td>${invoice.currentStatus}</td>
                <td>
                    <a href="./viewModifyOrder?invoiceNumber=${invoice.invoiceNumber}" class="btn btn-sm btn-primary" role="button">View</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div style="color:red;">${errorMessage}</div>
    <c:if test="${didSearch == true}">
        <a href="./orders" class="btn btn-primary" role="button">Clear Search</a>
    </c:if>
</main>
    
<jsp:include page="footer.jsp" />
