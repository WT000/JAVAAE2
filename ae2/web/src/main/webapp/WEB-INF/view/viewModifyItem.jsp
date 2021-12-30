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
    <div style="color:green;">${message}</div>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <form action="./viewModifyItem" method="POST">
        <input type="hidden" name="uuid" value="${item.uuid}">
    </c:if>
    <table class="table">
        <tbody>
            <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                <tr>
                    <td>Name</td>
                    <td><input name="name" value="${item.name}"></td>
                </tr>
            </c:if>
            
            <tr>
                <td>Description</td>
                <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                    <td>${item.description}</td>
                </c:if>
                
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <td><input name="description" value="${item.description}"></td>
                </c:if>
            </tr>
            <tr>
                <td>Category</td>
                <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                    <td>${item.category}</td>
                </c:if>
                
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <td>
                        <select name="category">
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
                <td>Price</td>
                <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                    <td>£${item.price}</td>
                </c:if>
                
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <td><input name="price" value="${item.price}"></td>
                </c:if>
            </tr>
            <tr>
                <td>Quantity</td>
                <c:if test="${sessionUser.userRole != 'ADMINISTRATOR'}">
                    <td>${item.quantity}</td>
                </c:if>
                
                <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
                    <td><input name="quantity" value="${item.quantity}"></td>
                </c:if>
            </tr>
            <tr>
                <td>UUID</td>
                <td>${item.uuid}</td>
            </tr>
        </tbody>
    </table>
    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <button class="btn btn-primary" type="submit" >Update ${item.name}</button>
        </form>
        <br>
    </c:if>
    <a href="./catalogue" class="btn btn-primary" role="button">Back to the Catalogue</a>
</main>

<jsp:include page="footer.jsp" />
