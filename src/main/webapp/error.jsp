<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="Error" />

<jsp:include page="head.jsp" />

<body class="bg-light">

<div class="container min-vh-100 d-flex align-items-center justify-content-center">
    <div class="card shadow-sm p-4 text-center"
         style="max-width: 500px;">

        <h1 class="h3 text-danger">
            Something went wrong
        </h1>

        <p class="text-muted">
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    ${errorMessage}
                </c:when>

                <c:otherwise>
                    We were unable to complete your request.
                </c:otherwise>
            </c:choose>
        </p>

        <a class="btn btn-dark"
           href="${pageContext.request.contextPath}/">
            Return home
        </a>

    </div>
</div>

</body>
</html>