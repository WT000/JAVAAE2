<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
    <h3>Login</h3>
    <div style="color:red;">${errorMessage}</div>
    <div style="color:green;">${message}</div>

    <form action="./login" method="post">
        <input type="hidden" name="action" value="login">
        <p>Username <input type="text" name="username" ></p><br>
        <p>Password <input type="password" name="password" ></p>
        <p><button type="submit" >Log In</button></p>
    </form> 
    
    <a href="./register">Create a new account</a>
</main>


<jsp:include page="footer.jsp" />
