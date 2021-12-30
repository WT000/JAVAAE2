<%-- 
    Document   : content
    Created on : Jan 4, 2020, 11:19:47 AM
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// request set in controller
//    request.setAttribute("selectedPage","contact");
%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>Properties</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <form action='properties' method='POST'>
        <input type="hidden" name="action" value="setProperties">
        
        <h1>Required Information</h1>
        <table class="table">
            <tbody>
                <tr>
                    <td>Remote Bank URL</td>
                    <td><input type="text" name="BankURL" value="${currentURL}"/></td>
                </tr>
                <tr>
                    <td>Bank Card</td>
                    <td><input type="text" name="BankCard" value="${currentCardNo}"/></td>
                </tr>
                <tr>
                    <td>Bank Username</td>
                    <td><input type="text" name="BankUsername" value="${currentUsername}"/></td>
                </tr>
                <tr>
                    <td>Bank Password</td>
                    <td><input type="password" name="BankPassword" value=""/></td>
                </tr>
            </tbody>
        </table>
        
        <h1>Optional Information</h1>
        <table class="table">
            <tbody>
                <tr>
                    <td>Name on Card</td>
                    <td><input type="text" name="BankCardName" value="${currentCardName}"/></td>
                </tr>
                <tr>
                    <td>Expiry Date</td>
                    <td><input type="text" name="BankCardExpiry" value="${currentCardDate}"/></td>
                </tr>
            </tbody>
        </table>
        <button class="btn btn-primary" type="submit" >Submit</button>
    </form>
</main>


<jsp:include page="footer.jsp" />
