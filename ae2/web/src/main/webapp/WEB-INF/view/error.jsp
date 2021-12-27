<%-- 
    Document   : error
    Created on : 3 Aug 2021, 20:22:57
    Author     : cgallen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<!-- Begin page content -->
<main role="main" class="container">
        <h3>ERROR</h3>
        <p>Error code ${status}: ${error}</p>
        <p>Failed URL: ${requestUrl}</p>
        <p>Exception:  ${exception.message}</p>
        <p>Stack trace:</p>
        <p>${strStackTrace}</p>
</main>

<jsp:include page="footer.jsp" />
