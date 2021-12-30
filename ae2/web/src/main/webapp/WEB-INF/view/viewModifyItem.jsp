<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>${item.name}</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <table class="table">
        <tbody>
            <tr>
                <td>Description</td>
                <td>${item.description}</td>
            </tr>
            <tr>
                <td>Category</td>
                <td>${item.category}</td>
            </tr>
            <tr>
                <td>Price</td>
                <td>£${item.price}</td>
            </tr>
            <tr>
                <td>Current Quantity</td>
                <td>${item.quantity}</td>
            </tr>
            <tr>
                <td>UUID</td>
                <td>${item.uuid}</td>
            </tr>
        </tbody>
    </table>
    <a href="./catalogue" class="btn btn-primary" role="button">Back to the Catalogue</a>
</main>

<jsp:include page="footer.jsp" />
