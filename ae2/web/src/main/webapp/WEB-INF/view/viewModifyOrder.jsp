<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.Address"%>
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
            <h1>General Details<h1>
                    <tbody>
                        <tr>
                            <td>Invoice Number</td>
                            <td>${invoice.invoiceNumber}</td>
                        </tr>
                        <tr>
                            <td>Purchaser</td>
                            <td>${invoice.purchaser.firstName} ${invoice.purchaser.secondName} (${invoice.purchaser.username})</td>
                        </tr>
                        <tr>
                            <td>Current Status</td>
                            <td>${invoice.currentStatus}</td>
                        </tr>
                    </tbody>
                    </table>

                    <h1>Order Items</h1>
                    <table class="table">
                        <tbody>
                            <tr>
                                <th>Name</th>
                                <th>Quantity</th>
                                <th>Cost</th>
                                <th>Total Cost</th>
                            </tr>
                            <c:forEach var="item" items="${savedPurchasedItems}">
                                <tr>
                                    <td><a href="./viewModifyItem?itemUuid=${item.dbItem.uuid}">${item.name}</a></td>
                                    <td>${item.quantity}</td>
                                    <td>£${item.price}</td>
                                    <td>£${item.price * item.quantity}</td>
                                </tr>
                            </c:forEach>
                                <tr>
                                    <th>TOTAL</th>
                                    <td colspan="2"></td>
                                    <td>£${invoice.amountDue}</td>
                                    
                                </tr>
                        </tbody>
                    </table>
                    <a href="./orders" class="btn btn-primary" role="button">Back to your orders</a>
                    </main>

                    <jsp:include page="footer.jsp" />
