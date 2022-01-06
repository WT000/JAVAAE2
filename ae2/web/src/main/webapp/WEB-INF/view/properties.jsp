<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <td><input type="url" name="BankURL" value="${currentURL}" required/></td>
                </tr>
                <tr>
                    <td>Bank Card</td>
                    <td><input type="text" name="BankCard" value="${currentCardNo}" placeholder="1111222233334444" pattern="[0-9]{16}" required/></td>
                </tr>
                <tr>
                    <td>Bank Username</td>
                    <td><input type="text" name="BankUsername" value="${currentUsername}" required/></td>
                </tr>
                <tr>
                    <td>Bank Password</td>
                    <td><input type="password" name="BankPassword" required/></td>
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
                    <td><input type="text" name="BankCardExpiry" value="${currentCardDate}" placeholder="01/01 or 01/2001" pattern="([0-9]{2}[/]?){2}|([0-9]{2}[/]?){3}"/></td>
                </tr>
            </tbody>
        </table>
        <button class="btn btn-success" type="submit">Submit</button>
    </form>
</main>

<jsp:include page="footer.jsp" />
