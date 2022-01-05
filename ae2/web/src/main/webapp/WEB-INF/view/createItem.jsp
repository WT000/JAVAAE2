<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>${item.name}</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <form action="./createItem" method="POST">
    </c:if>
    <table class="table">
        <tbody>
            <tr>
                <td>Name</td>
                <td><input name="name" value="${item.name}"></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><input name="description" value="${item.description}"></td>
            </tr>
            <tr>
                <td>Category</td>
                <td>
                    <select name="category">
                        <c:forEach var="category" items="${ShoppingItemCategory.values()}">
                            <option value="${category}" selected>${category}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Price</td>
                <td><input name="price" value="${item.price}"></td>
            </tr>
            <tr>
                <td>Quantity</td>
                <td><input name="quantity" value="${item.quantity}"></td>
            </tr>
        </tbody>
    </table>
    
    <button class="btn btn-primary" type="submit">Create Item</button>
    </form>
            
    <br>
    <a href="./catalogue" class="btn btn-primary" role="button">Back to the Catalogue</a>
</main>

<jsp:include page="footer.jsp" />
