<%-- 
    Document   : legacysaleservice
    Created on : 27 Oct 2021, 20:46:37
    Author     : Will
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="headersaleservice.jsp"/>
    <div id="resultContainer" class="bg-dark">
        <div id="result">
            ${result}
        </div>
    </div>
    <div id="inputContainer" class="bg-dark">
        <div id="currentInput">
            <p id="inputResult"></p>
        </div>
    </div>
    
    <!-- Hidden form which will be used to submit the card details -->
    <form id="transactionForm" method="post" autocomplete="off" style="display: none;">
        <input type="hidden" name="action" value="doTransaction">
        <input type="hidden" name="cardNumber">
        <input type="hidden" name="cardName">
        <input type="hidden" name="cardDate">
        <input type="hidden" name="cardCvv">
        <input type="hidden" name="amount">
    </form>
    
    <div id="formPlacer">
        <div id="formContainerKeypad" class="container-md">
            <div class="keypadRow">
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">1</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">2</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">3</button></div>
                <div class="keypadCol"><button class="btn btn-danger numpadButton" onclick="attemptBack()">X</button></div>
            </div>
            <div class="keypadRow">
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">4</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">5</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">6</button></div>
                <div class="keypadCol"><button class="btn btn-warning numpadButton" onclick="removeText()"><</button></div>
            </div>
            <div class="keypadRow">
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">7</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">8</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">9</button></div>
                <div class="keypadCol"><button class="btn btn-success numpadButton" onclick="attemptSubmit()">O</button></div>
            </div>
            <div class="keypadRow">
                <div class="keypadColFiller"></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">0</button></div>
                <div class="keypadCol" id="amountDecimal"><button class="btn btn-secondary numpadButton" onclick="addText(this)">.</button></div>
                <div class="keypadColFiller"></div>
            </div>
        </div>
        <div id="formContainerKeyboard" class="container-md">
            <div class="keypadRow">
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">1</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">2</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">3</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">4</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">5</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">6</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">7</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">8</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">9</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">0</button></div>
                <div class="keypadColFiller"></div>
            </div>
            <div class="keypadRow">
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">Q</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">W</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">E</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">R</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">T</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">Y</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">U</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">I</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">O</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">P</button></div>
                <div class="keypadCol"><button class="btn btn-danger numpadButton" onclick="attemptBack()">X</button></div>
            </div>
            <div class="keypadRow">
                <div class="keypadColFiller"></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">A</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">S</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">D</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">F</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">G</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">H</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">J</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">K</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">L</button></div>
                <div class="keypadCol"><button class="btn btn-warning numpadButton" onclick="removeText()"><</button></div>
            </div>
            <div class="keypadRow">
                <div class="keypadColFiller"></div>
                <div class="keypadColFiller"></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">Z</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">X</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">C</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">V</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">B</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">N</button></div>
                <div class="keypadCol"><button class="btn btn-secondary numpadButton" onclick="addText(this)">M</button></div>
                <div class="keypadColFiller"></div>
                <div class="keypadCol"><button class="btn btn-success numpadButton" onclick="attemptSubmit()">O</button></div>
            </div>
            <div class="keypadRow">
                <div class="keypadCol"><button id="spaceBar" class="btn btn-secondary numpadButton"></button></div>
            </div>
        </div>
    </div>
<jsp:include page="footersaleservice.jsp"/>