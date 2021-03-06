<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp" />

<!-- Begin page content -->
<main role="main" class="container">
    <div>
        <h3>Checkout</h3>
        <!-- print error message if there is one -->
        <div style="color:red;">${errorMessage}</div>
        <div style="color:red;">${param.errorMessage}</div>
        <div style="color:green;">${message}</div>

        <h1>General Information</h1>
        <form action="./checkout" method="POST">
            <table class="table">
                <tbody>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" value="${checkoutUser.firstName}" readonly/></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="secondName" value="${checkoutUser.secondName}" readonly/></td>
                    </tr>
                    <tr>
                        <td>Address Line 1</td>
                        <td><input type="text" name="addressLine1" value="${checkoutUser.address.addressLine1}" readonly/></td>
                    </tr>
                    <tr>
                        <td>Address Line 2</td>
                        <td><input type="text" name="addressLine2" value="${checkoutUser.address.addressLine2}" readonly/></td>
                    </tr>
                    <tr>
                        <td>City</td>
                        <td><input type="text" name="city" value="${checkoutUser.address.city}" readonly/></td>
                    </tr>
                    <tr>
                        <td>County</td>
                        <td><input type="text" name="county" value="${checkoutUser.address.county}" readonly/></td>
                    </tr>
                    <tr>
                        <td>Postcode</td>
                        <td><input type="text" name="postcode" value="${checkoutUser.address.postcode}" readonly/></td>
                    </tr>
                    <tr>
                        <td>Mobile number</td>
                        <td><input type="text" name="mobile" value="${checkoutUser.address.mobile}" readonly/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><a href="./viewModifyUser?username=${checkoutUser.username}">Edit your details</a></td>
                    </tr>
                </tbody>
            </table>

            <h1>Payment Information</h1>
            <table class="table">
                <tbody>
                    <tr>
                        <td>Card Number</td>
                        <td><input type="text" name="cardno" value="${sessionUser.card.cardnumber}" placeholder="1111222233334444" pattern="[0-9]{16}" required></td>
                    </tr>
                    <tr>
                        <td>Name on Card</td>
                        <td><input type="text" name="cardname" value="${sessionUser.card.name}" placeholder="John Doe" required></td>
                    </tr>
                    <tr>
                        <td>Expiry Date</td>
                        <td><input type="text" name="carddate" value="${sessionUser.card.endDate}" placeholder="01/01 or 01/2001" pattern="([0-9]{2}[/]?){2}|([0-9]{2}[/]?){3}" required></td>
                    </tr>
                    <tr>
                        <td>Cvv</td>
                        <td><input type="text" name="cardcvv" placeholder="123" pattern="[0-9]{3}" required></td>
                    </tr>
                </tbody>
            </table>
            <a href="./cart" class="btn btn-primary" role="button">Back to your cart</a>    
            <button class="btn btn-success" type="submit" >Submit</button>
        </form>
    </div>
</main>

<jsp:include page="footer.jsp" />
