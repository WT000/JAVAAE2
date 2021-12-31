<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="org.solent.com504.oodd.cart.model.dto.User"%>
<%@page import="org.solent.com504.oodd.cart.model.dto.UserRole"%>
<c:set var = "selectedPage" value = "users" scope="request"/>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">

    <div>
        <h3>${modifyUser.username}'s profile</h3>
        <!-- print error message if there is one -->
        <div style="color:red;">${errorMessage}</div>
        <div style="color:green;">${message}</div>

        <h1>General Information</h1>
        <form action="./viewModifyUser" method="POST">
            <table class="table">
                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" value="${modifyUser.firstName}" /></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="secondName" value="${modifyUser.secondName}" /></td>
                    </tr>
                    <tr>
                        <td>Address Line 1</td>
                        <td><input type="text" name="addressLine1" value="${modifyUser.address.addressLine1}" /></td>
                    </tr>
                    <tr>
                        <td>Address Line 2</td>
                        <td><input type="text" name="addressLine2" value="${modifyUser.address.addressLine2}" /></td>
                    </tr>
                    <tr>
                        <td>City</td>
                        <td><input type="text" name="city" value="${modifyUser.address.city}" /></td>
                    </tr>
                    <tr>
                        <td>County</td>
                        <td><input type="text" name="county" value="${modifyUser.address.county}" /></td>
                    </tr>
                    <tr>
                        <td>Postcode</td>
                        <td><input type="text" name="postcode" value="${modifyUser.address.postcode}" /></td>
                    </tr>
                    <tr>
                        <td>Telephone number</td>
                        <td><input type="text" name="telephone" value="${modifyUser.address.telephone}" /></td>
                    </tr>
                    <tr>
                        <td>Mobile number</td>
                        <td><input type="text" name="mobile" value="${modifyUser.address.mobile}" /></td>
                    </tr>
                </tbody>
            </table>
            
            <c:if test="${sessionUser.id == modifyUser.id}">
                <h1>Session Payment Information (optional)</h1>
                <table class="table">
                    <tbody>
                        <tr>
                            <td>Card Number</td>
                            <td><input type="text" name="cardno" value="${sessionUser.card.cardNumber}"></td>
                        </tr>
                        <tr>
                            <td>Name on Card</td>
                            <td><input type="text" name="cardname" value="${sessionUser.card.name}"></td>
                        </tr>
                        <tr>
                            <td>Expiry Date</td>
                            <td><input type="text" name="carddate" value="${sessionUser.card.endDate}" placeholder="01/01 or 01/2001"></td>
                        </tr>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${sessionUser.userRole !='ADMINISTRATOR'}">
                <h1>User Details</h1>
                <table class="table">
                    <thead>
                    </thead>
                    <tbody>
                        <tr>
                            <td>User ID</td>
                            <td>${modifyUser.id}</td>
                        </tr>
                        <tr>
                            <td>Username</td>
                            <td>${modifyUser.username}</td>
                        </tr>
                        <tr>
                            <td>Role</td>
                            <td>${modifyUser.userRole}</td>
                        </tr>
                        <tr>
                            <td>Enabled</td>
                            <td><c:if test="${modifyUser.enabled}">ENABLED</c:if><c:if test="${!modifyUser.enabled}">DISABLED</c:if></td>
                            </tr>
                        </tbody>
                    </table>
            </c:if>

            <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
                <h1>Manage User Status and Role</h1>
                <table class="table">
                    <thead>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Role</td>
                            <td>
                                <select class="form-control" name="userRole" >
                                    <c:forEach var="value" items="${UserRole.values()}">
                                        <option value="${value}" <c:if test="${modifyUser.userRole == value}"> selected  </c:if>>${value}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Enabled</td>
                            <td>
                                <select class="form-control" name="userEnabled" >
                                    <option value="true" <c:if test="${modifyUser.enabled}"> selected  </c:if> >ENABLED</option>
                                    <option value="false" <c:if test="${!modifyUser.enabled}"> selected  </c:if> >DISABLED</option>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
            </c:if>

            <input type="hidden" name="username" value="${modifyUser.username}"/>
            <button class="btn btn-primary" type="submit" >Update User ${modifyUser.username}</button>
        </form>
        
        <br>
        <h1>Update Password</h1>
        <form action="./viewModifyUser" method="post">
            <input type="hidden" name="username" value="${modifyUser.username}"/>
            <input type="hidden" name="action" value="updatePassword"/>
            <p>Password <input type="password" name="password" ></p>
            <p>Re-enter Password <input type="password" name="password2" ></p>
            <button class="btn btn-primary" type="submit" >Update ${modifyUser.username} Password</button>
        </form>
        <c:if test="${sessionUser.userRole =='ADMINISTRATOR'}">
            <BR>
            <form action="./users">
                <button class="btn btn-primary" type="submit" >Return To Users</button>
            </form> 
        </c:if> 

    </div>

</main>

<jsp:include page="footer.jsp" />
