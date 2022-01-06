<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <h3>${item.name}</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${param.message}</div>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <form action="./viewModifyItem" method="POST">
            <input type="hidden" name="action" value="updateItem">
            <input type="hidden" name="uuid" value="${item.uuid}">
        </c:if>
        <table class="table">
            <tbody>
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <tr>
                        <th>Name</th>
                        <td><input name="name" value="${item.name}" required></td>
                    </tr>
                </c:if>

                <tr>
                    <th>Description</th>
                        <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                        <td>${item.description}</td>
                    </c:if>

                    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                        <td><input name="description" value="${item.description}"></td>
                        </c:if>
                </tr>

                <tr>
                    <th>Category</th>
                        <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                        <td>${item.category}</td>
                    </c:if>

                    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                        <td>
                            <select name="category" required>
                                <c:forEach var="category" items="${ShoppingItemCategory.values()}">
                                    <c:if test="${ShoppingItemCategory.valueOf(category) == ShoppingItemCategory.valueOf(item.category)}">
                                        <option value="${category}" selected>${category}</option>
                                    </c:if>
                                    <c:if test="${ShoppingItemCategory.valueOf(category) != ShoppingItemCategory.valueOf(item.category)}">
                                        <option value="${category}">${category}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </c:if>
                </tr>

                <tr>
                    <th>Price</th>
                        <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                        <td>£${item.price}</td>
                    </c:if>

                    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                        <td><input type="number" name="price" value="${item.price}" required></td>
                        </c:if>
                </tr>

                <tr>
                    <th>Quantity</th>
                        <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                        <td>${item.quantity}</td>
                    </c:if>

                    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                        <td><input type="number" name="quantity" value="${item.quantity}" required></td>
                        </c:if>
                </tr>

                <tr>
                    <th>UUID</th>
                    <td>${item.uuid}</td>
                </tr>
            </tbody>
        </table>
        <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
            <button class="btn btn-success" type="submit">Update ${item.name}</button>
        </form>
        <br>
        <form action="./viewModifyItem" method="POST">
            <input type="hidden" name="action" value="deleteItem">
            <input type="hidden" name="uuid" value="${item.uuid}">
            <button class="btn btn-danger" type="submit">Delete ${item.name}</button>
        </form>
        <br>
    </c:if>
    <c:if test="${item.quantity > 0}">
        <form action="./catalogue" method="POST">
            <input type="hidden" name="itemUuid" value="${item.uuid}">
            <button class="btn btn-success">Add to Cart</button>
        </form>
    </c:if>
    <c:if test="${item.quantity == 0}">
        <button class="btn btn-warning">Out of Stock</button><br>
    </c:if>
    <br><a href="./catalogue" class="btn btn-primary" role="button">Back to the Catalogue</a>
</main>

<jsp:include page="footer.jsp" />
