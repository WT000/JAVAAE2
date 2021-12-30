<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItem"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.ShoppingItemCategory"%>
<%
// request set in controller
//    request.setAttribute("selectedPage","contact");
%>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <h3>Catalogue</h3>
    <div style="color:green;">${message}</div>

    <h1>Search Items</h1>
    <form action="./catalogue" method="GET">
        <input type="hidden" name="action" value="search">

        <input name="toFind">
        <select name="category">
            <option value="ALL">ALL</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category}">${category}</option>
            </c:forEach>
        </select>
        <button class="btn btn-primary">Search</button>
    </form>

    <br>
    <h1>Available Items</h1>
    <div style="color:red;">${errorMessage}</div>
    <div class="row">
        <c:forEach var="item" items="${currentItems}">
            <div class="col-sm-6 col-md-4">
                <div class="thumbnail">
                    <div class="caption">
                        <h1 class="text-center">${item.name} - £${item.price}</h1>
                        <p class="text-center">${item.description}</p>
                        <div id="button-spacer">
                            <c:if test="${item.quantity > 0}">
                                <button class="btn btn-primary">Add to Cart</button>
                            </c:if>
                            <c:if test="${item.quantity == 0}">
                                <button class="btn btn-warning">Out of Stock</button>
                            </c:if>
                            <form action="./viewModifyItem" method="GET">
                                <input type="hidden" name="itemUuid" value="${item.uuid}">
                                <button class="btn btn-primary">View Info</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${action == 'search'}">
        <a href="./catalogue" class="btn btn-primary" role="button">Clear Search</a>
    </c:if>

    <c:if test="${sessionUser.userRole == 'ADMINISTRATOR'}">
        <br>
        <h1>Unavailable Items</h1>
        <div class="row">
            <c:forEach var="item" items="${hiddenItems}">
                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <div class="caption">
                            <h1 class="text-center">${item.name} - £${item.price}</h1>
                            <p class="text-center">${item.description}</p>
                            <div id="button-spacer">
                                <c:if test="${item.quantity == 0}">
                                    <button class="btn btn-warning">Out of Stock</button>
                                </c:if>
                                <form action="./viewModifyItem" method="GET">
                                    <input type="hidden" name="itemUuid" value="${item.uuid}">
                                    <button class="btn btn-primary">View Info</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</main>

<jsp:include page="footer.jsp" />
