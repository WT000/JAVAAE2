<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>Create a New Account</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>


    <p>Username must be unique and password must be at least 8 characters.</p>
    <form action="./register" method="POST">
        <input type="hidden" name="action" value="createNewAccount">
        <p>Username <input type="text" name="username" ></p><BR>
        <p>Password <input type="password" name="password" ></p>
        <p>Re Enter Password <input type="password" name="password2" ></p>
        <p><button type="submit" >Create New Account</button></p>
    </form>

</main>


<jsp:include page="footer.jsp" />
