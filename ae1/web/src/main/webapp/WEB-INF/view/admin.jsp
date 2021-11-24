<%-- 
    Document   : admin
    Created on : 13 Nov 2021, 12:23:40
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="headeradmin.jsp"/>
<div id="resultContainer">
    <div id="result">
        ${result}
    </div>
</div>

<c:choose>        
<c:when test="${not loggedIn}">
    <div id="formPlacer">    
        <div id="formContainerSmall" class="container-md">
            <form id="loginForm" method="post" autocomplete="off">
                <input type="hidden" name="action" value="adminLogin">
                
                <div class="row">
                    <div class="col">
                        <label for="loginUsername">Properties Username</label>
                        <input type="text" class="form-control" id="loginUsername" name="propertiesUsername" required>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <label for="loginPassword">Properties Password</label>
                        <input type="password" class="form-control" id="loginPassword" name="propertiesPassword" required>
                    </div>
                </div>
                
                <div class="row mt-2">
                    <div class="col text-center">
                        <button class="btn btn-dark submitButton">Login</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</c:when>
<c:when test="${loggedIn}">
    <div id="formPlacer">
        <div id="formContainer">
            <form id="propertiesForm" class="innerForm" method="post" autocomplete="off">
                <input type="hidden" name="action" value="setProperties">
                
                <div class="row">
                    <div class="col">
                        <label for="propertyURL">Bank URL</label>
                        <input type="url" class="form-control" id="propertyURL" name="propertiesURL" placeholder="Bank URL" value="${bankUrl}" required>
                    </div>
                </div>
                    
                    <div class="row mt-3 mb-3">
                        <div class="col-md-4">
                            <label for="propertyUser">Username</label>
                            <input type="text" class="form-control" id="propertyUser" name="propertiesUsername" placeholder="Bank Username" value="${bankUsername}" required>
                        </div>
                        <div class="col-md-4">
                            <label for="propertyPass">Password</label>
                            <input type="password" class="form-control" id="propertyPass" name="propertiesPassword" placeholder="Bank Password" required>
                        </div>
                        <div class="col-md-4">
                            <label for="propertyCard">Card Number</label>
                            <input type="text" class="form-control" id="propertyCard" name="propertiesCard" placeholder="Bank Card" pattern="[0-9]{16}" value="${bankCardNo}" required>
                        </div>
                    </div>
                
                <div class="row">
                    <div class="col text-center">
                        <button class="btn btn-dark submitButton">Submit</button>
                    </div>
                </div>
            </form>
            
            <form id="refundForm" class="innerForm" method="post" autocomplete="off">
                <input type="hidden" name="action" value="doRefund">
                
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label for="refundCard">Card Number</label>
                        <input type="text" class="form-control" id="refundCard" name="cardNumber" placeholder="1111222233334444" pattern="[0-9]{16}" required>
                    </div>
                    <div class="col-md-6">
                        <label for="refundAmount">Refund amount</label>
                        <input type="text" class="form-control" id="refundAmount" name="amount" placeholder="£0.00" pattern="[0-9]*\.?[0-9]*" required>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col text-center">
                        <button class="btn btn-dark submitButton">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    
        <div id="functionContainer">
            <div class="functionButton" id="buttonProperties">
                <p>Properties Controller</p>
            </div>

            <div class="functionButton" id="buttonRefund">
                <p>Refund to Card</p>
            </div>
        </div>
</c:when>
</c:choose>
    
<c:choose>        
<c:when test="${not loggedIn}">
    <jsp:include page="footeradminmin.jsp"/>
</c:when>
<c:when test="${loggedIn}">
    <jsp:include page="footeradmin.jsp"/>
</c:when>
</c:choose>
