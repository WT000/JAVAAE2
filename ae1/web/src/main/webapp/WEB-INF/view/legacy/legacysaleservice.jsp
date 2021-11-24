<%-- 
    Document   : legacysaleservice
    Created on : 27 Oct 2021, 20:46:37
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="legacyheadersaleservice.jsp"/>
    <div id="resultContainer" class="bg-dark">
        <div id="result">
            ${result}
        </div>
    </div>
    
    <div id="formPlacer">
        <div id="formContainer" class="container-md">
            <form id="transactionForm" method="post" autocomplete="off">
                <input type="hidden" name="action" value="doTransaction">
                
                <div class="row">
                    <div class="col-md-6">
                        <label for="customerCardNo">Card Number</label>
                        <input type="text" class="form-control" id="customerCardNo" name="cardNumber" placeholder="1111222233334444" pattern="[0-9]{16}" max="16" onclick="show_easy_numpad(this, 'number');" required>
                    </div>
                    <div class="col-md-6">
                        <label for="customerNameOnCard">Name on Card</label>
                        <input type="text" class="form-control" id="customerNameOnCard" name="cardName" placeholder="John Doe" max="30" onclick="show_easy_numpad(this, 'letter');" required>
                    </div>
                </div>
                
                <div class="row mt-3 mb-3">
                    <div class="col">
                        <label for="customerExpireDate">Expiry Date</label>
                        <input type="text" class="form-control" id="customerExpireDate" name="cardDate" placeholder="MM/YY or MM/YYYY" pattern="([0-9]{2}[/]?){2}|([0-9]{2}[/]?){3}" max="7" onclick="show_easy_numpad(this, 'date');" required>
                    </div>
                    <div class="col">
                        <label for="customerCvv">Cvv</label>
                        <input type="text" class="form-control" id="customerCvv" name="cardCvv" placeholder="123" pattern="[0-9]{3}" max="3" onclick="show_easy_numpad(this, 'number');" required>
                    </div>
                    <div class="col">
                        <label for="customerAmount">Amount</label>
                        <input type="text" class="form-control" id="customerAmount" name="amount" placeholder="£0" pattern="[0-9]*\.?[0-9]*" max="8" onclick="show_easy_numpad(this, 'decimal');" required>
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
<jsp:include page="legacyfootersaleservice.jsp"/>