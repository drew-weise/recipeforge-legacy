<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="title" value="RecipeForge" />

<jsp:include page="head.jsp" />

<body class="bg-light">

<div class="container min-vh-100 d-flex align-items-center justify-content-center">
    <div class="card shadow-sm text-center p-5" style="max-width: 500px;">

        <h1 class="mb-3">
            RecipeForge
        </h1>

        <p class="text-muted mb-4">
            Discover and create delicious recipes.
        </p>

        <c:choose>
            <c:when test="${empty email}">
                <p>
                    Log in to explore your recipes and get cooking.
                </p>

                <a class="btn btn-dark"
                   href="${pageContext.request.contextPath}/logIn">
                    Log in
                </a>
            </c:when>

            <c:otherwise>
                <c:redirect url="/user" />
            </c:otherwise>
        </c:choose>

    </div>
</div>

</body>
</html>