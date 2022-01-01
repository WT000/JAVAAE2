<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>Cart</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <h1>Contents</h1>
    <table class="table">

        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>

        <c:forEach var="item" items="${shoppingCartItems}">
            <tr>
                <td><a href="./viewModifyItem?itemUuid=${item.uuid}">${item.name}</a></td>
                <td>£${item.price}</td>
                <td>${item.quantity}</td>
                <td>
                    <!-- post avoids url encoded parameters -->
                    <form action="./cart" method="POST">
                        <input type="hidden" name="uuid" value="${item.uuid}">
                        <button class="btn btn-danger">Remove item</button>
                    </form> 
                </td>
            </tr>
        </c:forEach>
        
        <tr>
            <td><strong>TOTAL</strong></td>
            <td>£${shoppingCartTotal}</td>
        </tr>
    </table>
    
    <c:if test="${sessionUser.userRole != 'ANONYMOUS' && shoppingCartItems.size() > 0}">
        <a href="./checkout" class="btn btn-success" role="button">Proceed to Checkout</a>
    </c:if>
    <c:if test="${sessionUser.userRole == 'ANONYMOUS' && shoppingCartItems.size() > 0}">
        <a href="./login" class="btn btn-primary" role="button">Login to Checkout</a>
    </c:if>

</main>
<jsp:include page="footer.jsp" />
