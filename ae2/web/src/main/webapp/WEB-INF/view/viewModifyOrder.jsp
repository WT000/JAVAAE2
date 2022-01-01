<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>AE2 Shopping Cart Invoice - ${invoice.dateOfPurchase}</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <form action="./viewModifyOrder" method="POST">
        <input type="hidden" name="uuid" value="${invoice.invoiceNumber}">
    </c:if>
    <table class="table">
        <tbody>
            <tr>
                <td>Invoice Number</td>
                <td>${invoice.invoiceNumber}</td>
            </tr>
        </tbody>
    </table>
    <a href="./orders" class="btn btn-primary" role="button">Back to your orders</a>
</main>

<jsp:include page="footer.jsp" />
