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
                <td>${item.name}</td>
                <td>${item.price}</td>
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
            <td>TOTAL</td>
            <td>${shoppingCartTotal}</td>
        </tr>
    </table>

</main>
<jsp:include page="footer.jsp" />
