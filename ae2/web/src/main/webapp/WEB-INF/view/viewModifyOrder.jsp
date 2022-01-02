<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.Address"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.InvoiceStatus"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>AE2 Shopping Cart Invoice - ${invoice.dateOfPurchase}</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:red;">${param.errorMessage}</div>
    <div style="color:green;">${param.message}</div>

    <table class="table">
        <h1>General Details<h1>
        <tbody>
            <tr>
                <th>Invoice Number</th>
                <td>${invoice.invoiceNumber}</td>
            </tr>
            <tr>
                <th>Purchaser</th>
                <td>${invoice.purchaser.firstName} ${invoice.purchaser.secondName} (${invoice.purchaser.username})</td>
            </tr>
            <tr>
                <th>Payment Card</th>
                <td>${invoice.paymentCardNumber}</td>
            </tr>
            <tr>
                <th>Current Status</th>
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <c:if test="${invoice.currentStatus != InvoiceStatus.REFUNDED}">
                        <td>
                            <form action="./viewModifyOrder" method="POST">
                                <input type="hidden" name="action" value="updateStatus">
                                <input type="hidden" name="invoiceNumber" value="${invoice.invoiceNumber}">
                                    <select name="status">
                                        <c:forEach var="status" items="${InvoiceStatus.values()}">
                                            <c:if test="${InvoiceStatus.valueOf(status) == InvoiceStatus.valueOf(invoice.currentStatus)}">
                                                <option value="${status}" selected>${status}</option>
                                            </c:if>
                                            <c:if test="${InvoiceStatus.valueOf(status) != InvoiceStatus.valueOf(invoice.currentStatus) && InvoiceStatus.valueOf(status) != InvoiceStatus.REFUNDED}">
                                                <option value="${status}">${status}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                <button class="btn btn-sm btn-success">Update</button>                    
                            </form>
                        </td>
                    </c:if>
                </c:if>
                <c:if test="${sessionUser.userRole != 'ADMINISTRATOR' || invoice.currentStatus == InvoiceStatus.REFUNDED}">
                    <td>${invoice.currentStatus}</td>
                </c:if>
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
        
    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <form action="./viewModifyOrder" method="POST">
            <input type="hidden" name="action" value="refund">
            <input type="hidden" name="invoiceNumber" value="${invoice.invoiceNumber}">
            <button class="btn btn-success">Refund Order</button><br><br>
        </form>
    </c:if>
            
    <a href="./orders" class="btn btn-primary" role="button">Back to orders</a>
</main>

<jsp:include page="footer.jsp" />
